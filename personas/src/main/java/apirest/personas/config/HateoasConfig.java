package apirest.personas.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.client.JsonPathLinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.core.DelegatingLinkRelationProvider;
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.plugin.core.support.PluginRegistryFactoryBean;

/**
 * The HateoasConfig Class
 */
@Configuration
public class HateoasConfig {
    
    /**
     * The LinkDiscoverers bean
     */
    @Bean
    public LinkDiscoverers discoverers() {
        List<JsonPathLinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new HalLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

    /**
     * The LinkRelationProvider bean 
     */
    @Bean
    public LinkRelationProvider provider() {
        return new EvoInflectorLinkRelationProvider();
    }
    
    /**
     * The PluginRegistryFactoryBean
     */
    @Bean
    @Primary
    public PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext> myPluginRegistryProvider() {
        PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext> factory = new PluginRegistryFactoryBean<>();
        
        factory.setType(LinkRelationProvider.class);
        Class<?> classes[] = new Class<?>[1]; 
        classes[0] = DelegatingLinkRelationProvider.class;
        factory.setExclusions(classes);
        
        return factory;
    }
    
}
