package pet.authSecurityPosgreSQL.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.authSecurityPosgreSQL.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);


}
