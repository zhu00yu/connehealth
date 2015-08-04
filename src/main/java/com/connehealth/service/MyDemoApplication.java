package com.connehealth.service;

import com.connehealth.util.CORSResponseFilter;
import com.connehealth.util.LoggingResponseFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Registers the components to be used by the JAX-RS application
 *
 * @author ama
 *
 */
public class MyDemoApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public MyDemoApplication(){
        register(RequestContextFilter.class);
        register(UserRestService.class);
        register(PodcastRestService.class);
        register(JacksonFeature.class);
        register(LoggingResponseFilter.class);
        register(CORSResponseFilter.class);
    }
}