package pet.authSecurityPosgreSQL.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pet.authSecurityPosgreSQL.model.User;
import pet.authSecurityPosgreSQL.service.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);
        MyUserDetails myUserDetails = new MyUserDetails(user);
        return myUserDetails;
    }
}
