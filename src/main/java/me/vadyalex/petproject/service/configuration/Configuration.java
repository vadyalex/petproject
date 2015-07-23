package me.vadyalex.petproject.service.configuration;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by vadyalex.
 */
@ApplicationPath("/")
public class Configuration extends ResourceConfig {

    public Configuration() {
        packages("me.vadyalex.petproject");
    }

}
