package pet.authSecurityPosgreSQL.controller;

import javassist.tools.web.BadHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pet.authSecurityPosgreSQL.model.AccountStatus;
import pet.authSecurityPosgreSQL.model.User;
import pet.authSecurityPosgreSQL.service.UserService;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.BadParamException;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/a/users")
public class AdminPanelController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(params = {"username"})
    public User getUserByUsername(@RequestParam("username") String username)
            throws UserNotFoundException {

        User user = userService.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }
        return user;
    }

    @PostMapping(params = {"username","status"})
    public void changeUserStatus(@RequestParam("username") String username,
                                 @RequestParam("status") String status) throws UserNotFoundException, BadParamException {

        User user = userService.findByUsername(username);
        if(user == null){
            throw new UserNotFoundException(username);
        }
        AccountStatus accountStatus = userService.changeStatus(user, status);

        if(accountStatus == null) {
            throw new BadParamException(status);
        }
    }
}
