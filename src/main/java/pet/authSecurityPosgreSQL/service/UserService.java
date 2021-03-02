package pet.authSecurityPosgreSQL.service;



import pet.authSecurityPosgreSQL.dto.UserDTO;
import pet.authSecurityPosgreSQL.model.AccountStatus;
import pet.authSecurityPosgreSQL.model.User;
import pet.authSecurityPosgreSQL.service.exceptionHandler.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    User findByUsername(String username) throws UserNotFoundException;

    List<User> getAllUsers();

    String saveUser(UserDTO userDTO);

    void changeStatus(User user, AccountStatus status);
}
