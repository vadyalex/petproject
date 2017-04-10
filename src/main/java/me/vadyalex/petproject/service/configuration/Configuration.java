package me.vadyalex.petproject.service.configuration;


import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

/**
 * Created by vadyalex.
 */
@ApplicationPath("/*")
public class Configuration extends ResourceConfig {

    public static final String JERSEY_RESOURCES_PACKAGE = "me.vadyalex.petproject";

    @Inject
    public Configuration(@Context ServletContext servletContext, ServiceLocator serviceLocator) {
        packages(true, JERSEY_RESOURCES_PACKAGE);


        register(new EnvConfiguration());
    }
}
