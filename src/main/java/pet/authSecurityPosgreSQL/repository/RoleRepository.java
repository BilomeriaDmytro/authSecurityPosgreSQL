package pet.authSecurityPosgreSQL.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.authSecurityPosgreSQL.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
