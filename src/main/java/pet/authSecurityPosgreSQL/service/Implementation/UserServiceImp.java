package pet.authSecurityPosgreSQL.service.Implementation;

import javassist.tools.web.BadHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.HttpClientErrorException;
import pet.authSecurityPosgreSQL.dto.UserDTO;
import pet.authSecurityPosgreSQL.model.AccountStatus;
import pet.authSecurityPosgreSQL.model.Role;
import pet.authSecurityPosgreSQL.model.User;
import pet.authSecurityPosgreSQL.repository.RoleRepository;
import pet.authSecurityPosgreSQL.repository.UserRepository;
import pet.authSecurityPosgreSQL.service.UserService;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.BadParamException;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.RegistrationFailureException;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.UserNotFoundException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Override
    public User findByUsername(String username) {
        log.info("Trying to get user with username - {}...", username);

        User user = userRepository.findByUsername(username);
        if(user == null){
            log.info("User with username - {} not found.", username);
            return null;
        }
        log.info("User with username - {} successfully found.", username);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Trying to get all users...");

        List <User> users = userRepository.findAll();

        log.info("Returning all users - {} found", users.size());
        return users;
    }

    @Override
    public User saveUser(UserDTO userDTO, BindingResult result) {

        String username = userDTO.getUsername();

        log.info("Trying to register new user with username - {}...", username);

        if(userRepository.findByUsername(username) != null){
            log.warn("User with username - {} already exist.", username);
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setFirstName(userDTO.getFirstName());
        user.setEmail(userDTO.getEmail());
        user.setLastName(userDTO.getLastName());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        user.setStatus(AccountStatus.ACTIVE);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);

        userRepository.save(user);
        log.info("User with username - {} successfully registered. Account status - {}.", username, user.getStatus());
        return user;
    }

    @Override
    public AccountStatus changeStatus(User user, String status) {

        String username = user.getUsername();
        log.info("Trying to change status for user with username - {}...", username);

        AccountStatus accountStatus = getEnumValue(status);
        if(accountStatus == null){
            log.warn("Wrong status value.");
            return null;
        }
        user.setStatus(accountStatus);
        log.info("{}'s status is changed to {}.", username, status);
        return  accountStatus;
    }

    private AccountStatus getEnumValue(String status){

        for(AccountStatus accountStatus : AccountStatus.values()) {
            if (accountStatus.name().equals(status)) {
                return accountStatus;
            }
        }
        return null;
    }
}
