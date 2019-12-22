package pro.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pro.backend.service.UserService;

@Configuration
public class AuthConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Password grants are switched on by injecting an AuthenticationManager.
     * Here, we setup the builder so that the userDetailsService is the one we
     * coded.
     */

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserService service) throws Exception {
        builder.userDetailsService(service).passwordEncoder(passwordEncoder);
    }
}

