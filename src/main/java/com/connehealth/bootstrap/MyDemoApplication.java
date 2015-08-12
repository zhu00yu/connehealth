package com.connehealth.bootstrap;

import com.connehealth.service.PodcastRestService;
import com.connehealth.service.UserProfileRestService;
import com.connehealth.service.UserRestService;
import com.connehealth.util.CORSResponseFilter;
import com.connehealth.util.LoggingResponseFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
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
        register(JacksonFeature.class);
        register(LoggingResponseFilter.class);
        register(CORSResponseFilter.class);

        /*register(UserRestService.class);
        register(UserProfileRestService.class);
        register(PodcastRestService.class);*/
        packages("com.connehealth.service");
    }
}