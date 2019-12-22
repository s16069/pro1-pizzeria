package pro.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import pro.backend.config.security.JwtAuthenticationFilter;
import pro.backend.config.security.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("pro.backend.config.security")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors();

        http.csrf().disable();

        http.requestCache().disable();

        http.authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/menu/**").permitAll()
                .antMatchers(HttpMethod.GET, "/pizzas").permitAll()
                .antMatchers(HttpMethod.GET, "/pizzas/*").permitAll()
                .antMatchers(HttpMethod.GET, "/sizes").permitAll()
                .antMatchers(HttpMethod.GET, "/sizes/*").permitAll()
                .antMatchers(HttpMethod.GET, "/doughs").permitAll()
                .antMatchers(HttpMethod.GET, "/doughs/*").permitAll()
                .antMatchers(HttpMethod.GET, "/ingredients").permitAll()
                .antMatchers(HttpMethod.GET, "/ingredients/*").permitAll()
                .antMatchers(HttpMethod.GET, "/promotions").permitAll()
                .antMatchers(HttpMethod.GET, "/promotions/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()));

        http.requestCache().requestCache(new NullRequestCache());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

