package apirest.personas.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The SwaggerConfig Class
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    /** The app version */
    @Value("${info.build.version}")
    private String appVersion;
    
    /** The auth server url */
    @Value("${springfox.auth.server.url}")
    private String authServer;
    
    /** The oauth2 client id */
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    
    /** The oauth2 client secret */
    @Value("${springfox.security.configuration.client-secret}")
    private String clientSecret;
    
    /** The base package */
    @Value("${springfox.apis.basepackage}")
    private String basePackage;
    
    /** The regex path */
    @Value("${springfox.paths.regex}")
    private String pathRegex;
    
    /** The ant path */
    @Value("${springfox.paths.ant}")
    private String pathsAnt;
    
    /** The security scheme name */
    @Value("${springfox.securityscheme.name}")
    private String schemeName;
    
    /** The oauth2 client scope */
    @Value("${security.oauth2.client.scope}")
    private String clientScope;
    
    /**
     * The Docket bean
     */
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.regex(pathRegex))
            .build()
            .securitySchemes(Arrays.asList(securityScheme()))
            .securityContexts(Arrays.asList(securityContext()))
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo());
    }
    
    /**
     * The Security Configuration bean
     */
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .build();
    }
    
    /**
     * The SecurityScheme
     */
    private SecurityScheme securityScheme() {
        GrantType passwordGrant = new ResourceOwnerPasswordCredentialsGrant(authServer);
        
        SecurityScheme oauth = new OAuthBuilder().name(schemeName)
            .grantTypes(Arrays.asList(passwordGrant))
            .scopes(Arrays.asList(scopes()))
            .build();
        
        return oauth;
    }
    
    /**
     * The SecurityContext
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(Arrays.asList(new SecurityReference(schemeName, scopes())))
            .forPaths(PathSelectors.ant(pathsAnt))
            .build();
    }
    
    /**
     * The AuthorizationScope array
     */
    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = { 
            new AuthorizationScope(clientScope, "read and write"), 
        };
        
        return scopes;
    }
    
    /**
     * The Api info
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
            "API de Personas",
            "Servicios REST de Api de personas",
            appVersion,
            "urn:tos",
            new Contact("Contact Name", "http://www.none.com", "test@test.com"),
            "API License",
            "http://www.api-license-url.com",
            new ArrayList<>());
    }
}
