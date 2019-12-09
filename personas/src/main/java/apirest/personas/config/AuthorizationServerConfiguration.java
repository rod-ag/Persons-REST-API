package apirest.personas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * The AuthorizationServerConfiguration Class
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    /** The AuthenticationManager */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    
    /** The TokenStore */
    @Autowired
    private TokenStore tokenStore;
    
    /** The clientId */
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    /** The clientSecret */
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    
    /** The client scope */
    @Value("${security.oauth2.client.scope}")
    private String clientScope;
    
    /**
     * The client details configuration
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(clientId)
            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
            .scopes(clientScope)
            .autoApprove(true)
            .secret(clientSecret);
    }
    
    /**
     * The authorization server endpoints configuration
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .authenticationManager(authenticationManager)
            .tokenStore(tokenStore);
    }
    
    /**
     * The TokenStore bean
     * 
     * @return token Store
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
}