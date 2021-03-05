package pet.authSecurityPosgreSQL.service;

import org.springframework.validation.BindingResult;
import pet.authSecurityPosgreSQL.dto.UserDTO;
import pet.authSecurityPosgreSQL.model.AccountStatus;
import pet.authSecurityPosgreSQL.model.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    List<User> getAllUsers();

    User saveUser(UserDTO userDTO, BindingResult result);

    AccountStatus changeStatus(User user, String status);
}
