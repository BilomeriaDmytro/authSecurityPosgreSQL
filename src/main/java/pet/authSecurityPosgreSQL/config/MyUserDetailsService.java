package pet.authSecurityPosgreSQL.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;
import pet.authSecurityPosgreSQL.model.User;
import pet.authSecurityPosgreSQL.repository.UserRepository;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    
	@Override
	public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
    	log.info("User with username - {} not found", username);
        throw new UsernameNotFoundException(username);
    }
    
    log.info("user loaded: {} roles: {}", user.toString(),user.getRoles());
    
    
    return new MyUserPrincipal(user);
	}
}
