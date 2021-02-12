package pet.authSecurityPosgreSQL.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pet.authSecurityPosgreSQL.model.AccountStatus;
import pet.authSecurityPosgreSQL.model.Role;
import pet.authSecurityPosgreSQL.model.User;


public class MyUserPrincipal implements UserDetails {

	private String rolePrefix = "ROLE_";  //Prefix to create authority
    private User user;
   
 
    public MyUserPrincipal(User user) {
        this.user = user;
    }

	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	public Long getId() {
		return user.getId();
	}
	
	public String getFirstName() {
		return user.getFirstName();
	}
	
	public String getLastName() {
		return user.getLastName();
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> userRoles = user.getRoles();
		return userRoles.stream()
			.map(role -> new SimpleGrantedAuthority(rolePrefix + role.getName())).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getStatus().equals(AccountStatus.ACTIVE);

	}
}