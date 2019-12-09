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

@Configuration
public class HateoasConfig {
    
    @Bean
    public LinkDiscoverers discoverers() {
        List<JsonPathLinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new HalLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

    @Bean
    public LinkRelationProvider provider() {
        return new EvoInflectorLinkRelationProvider();
    }
    
    @Bean
    @Primary
    public PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext>
            myPluginRegistryProvider(){
        
        PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext> factory 
            = new PluginRegistryFactoryBean<>();
        
        factory.setType(LinkRelationProvider.class);
        Class<?> classes[] = new Class<?>[1]; 
        classes[0] = DelegatingLinkRelationProvider.class;
        factory.setExclusions(classes);
        
        return factory;
    }
    
}
