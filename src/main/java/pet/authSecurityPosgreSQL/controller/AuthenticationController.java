package pet.authSecurityPosgreSQL.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pet.authSecurityPosgreSQL.config.jwt.TokenProvider;
import pet.authSecurityPosgreSQL.dto.AuthenticationResponseDTO;
import pet.authSecurityPosgreSQL.dto.UserDTO;
import pet.authSecurityPosgreSQL.dto.UserLoginDTO;
import pet.authSecurityPosgreSQL.model.User;
import pet.authSecurityPosgreSQL.service.UserService;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.RegistrationFailureException;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("api/login")
    public AuthenticationResponseDTO login(@RequestBody UserLoginDTO userLoginDTO) {

        try{
            String username = userLoginDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,userLoginDTO.getPassword()));
            User user = userService.findByUsername(username);

            if(user == null){
                throw new UserNotFoundException(username);
            }

            String token = tokenProvider.createToken(username);
            return new AuthenticationResponseDTO(username,token);

        }catch (AuthenticationException | UserNotFoundException e){

            log.warn("Wrong credentials. Username - {}", userLoginDTO.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }
    }



    @PostMapping("api/sign")
    public User addNewUser(@Valid @RequestBody UserDTO userDTO, BindingResult result)
            throws RegistrationFailureException {

        String username = userDTO.getUsername();
        if(result.hasErrors()) {
            log.warn("Exception thrown then trying to register user with username - '{}', {} errors found",
                    username, result.getErrorCount());

            throwNewRegError(result);
        }

        User user = userService.saveUser(userDTO, result);
        if(user == null){
            throw new RegistrationFailureException("User with username '" + username + "' already exist");
        }
        return user;
    }

    private void throwNewRegError(BindingResult result) throws RegistrationFailureException {

        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ObjectError> globalErrors = result.getGlobalErrors();

        List<pet.authSecurityPosgreSQL.service.exceptionHandler.FieldError> myFieldErrors;

        myFieldErrors = fieldErrors.stream()
                .map(pet.authSecurityPosgreSQL.service.exceptionHandler.FieldError::new)
                .collect(Collectors.toList());

        throw new RegistrationFailureException("Errors appears then validating form", myFieldErrors, globalErrors);
    }

}
