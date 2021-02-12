package pet.authSecurityPosgreSQL.service;



import pet.authSecurityPosgreSQL.dto.UserDTO;
import pet.authSecurityPosgreSQL.model.AccountStatus;
import pet.authSecurityPosgreSQL.model.User;

import java.util.List;

public interface UserService {

    User getUser(String username);

    List<User> getAllUsers();

    void saveUser(UserDTO userDTO);

    void changeStatus(User user, AccountStatus status);
}
