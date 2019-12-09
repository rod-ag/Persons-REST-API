package apirest.personas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * The PersonasWebSecurityConfig Class
 */
@Configuration
public class PersonasWebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    /** The user userName */
    @Value("${userdetails.user.username}")
    private String userName;
    
    /** The user password */
    @Value("${userdetails.user.password}")
    private String userPassword;
    
    /** The user role */
    @Value("${userdetails.user.role}")
    private String userRole;
    
    /** The admin userName */
    @Value("${userdetails.admin.username}")
    private String adminName;
    
    /** The admin user password */
    @Value("${userdetails.admin.password}")
    private String adminPassword;
    
    /** The admin user role */
    @Value("${userdetails.admin.role}")
    private String adminRole;
    
    
    /**
     * The AuthenticationManager bean
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    /**
     * The UserDetailsService bean
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        
        UserDetails user = User.builder().username(userName).password(userPassword).roles(userRole).build();
        
        UserDetails userAdmin = User.builder().username(adminName).password(adminPassword).roles(adminRole).build();
        
        return new InMemoryUserDetailsManager(user,userAdmin);
    }
}