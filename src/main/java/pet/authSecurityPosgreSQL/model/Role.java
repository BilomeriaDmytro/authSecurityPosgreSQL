package pet.authSecurityPosgreSQL.model;

import lombok.Data;


import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    @ManyToMany(mappedBy = "roles", fetch =  FetchType.LAZY)
    private List<User> users;

    public String toString() {
        return this.getName();
    }
}
