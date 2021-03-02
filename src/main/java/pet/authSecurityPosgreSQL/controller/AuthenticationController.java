package pet.authSecurityPosgreSQL.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import pet.authSecurityPosgreSQL.config.jwt.TokenProvider;
import pet.authSecurityPosgreSQL.dto.AuthenticationResponseDTO;
import pet.authSecurityPosgreSQL.dto.UserDTO;
import pet.authSecurityPosgreSQL.model.User;
import pet.authSecurityPosgreSQL.service.UserService;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.UserNotFoundException;
import java.util.List;

@Slf4j
@RestController
public class AuthenticationController {

    AuthenticationManager authenticationManager;
    TokenProvider tokenProvider;
    UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("api/login")
    public AuthenticationResponseDTO login(@RequestBody UserDTO userDTO) {

        try{
            String username = userDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,userDTO.getPassword()));
            User user = userService.findByUsername(username);
            if(user == null){
                throw new UserNotFoundException(username);
            }
            String token = tokenProvider.createToken(username);
            return new AuthenticationResponseDTO(username,token);

        }catch (AuthenticationException | UserNotFoundException e){
            log.warn("Wrong credentials. Username - {}", userDTO.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("api/a/get/allUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("api/a/get/userByUsername")
    public User getUserByUsername(@RequestParam("username") String username) throws UserNotFoundException {

        return userService.findByUsername(username);
    }

    @PostMapping("api/sign")
    public String addNewUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

}
