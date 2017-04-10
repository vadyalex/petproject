package me.vadyalex.petproject.service.configuration;


import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

/**
 * Created by vadyalex.
 */
@ApplicationPath("/*")
public class Configuration extends ResourceConfig {

    public static final String JERSEY_RESOURCES_PACKAGE = "me.vadyalex.petproject";

    @Inject
    public Configuration(ServiceLocator serviceLocator) {
        packages(true, JERSEY_RESOURCES_PACKAGE);
        register(new EnvConfiguration());
    }
}
