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

@Configuration
public class PersonasWebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Value("${userdetails.user.username}")
    private String userName;
    
    @Value("${userdetails.user.password}")
    private String userPassword;
    
    @Value("${userdetails.user.role}")
    private String userRole;
    
    @Value("${userdetails.admin.username}")
    private String adminName;
    
    @Value("${userdetails.admin.password}")
    private String adminPassword;
    
    @Value("${userdetails.admin.role}")
    private String adminRole;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        
        UserDetails user = User.builder().username(userName).password(userPassword).roles(userRole).build();
        
        UserDetails userAdmin = User.builder().username(adminName).password(adminPassword).roles(adminRole).build();
        
        return new InMemoryUserDetailsManager(user,userAdmin);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
//    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}