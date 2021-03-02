package pet.authSecurityPosgreSQL.config.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider jwtTokenProvider;

    public JwtConfigurer(TokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void configure(HttpSecurity security) {

        TokenFilter tokenFilter = new TokenFilter(jwtTokenProvider);
        security.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
