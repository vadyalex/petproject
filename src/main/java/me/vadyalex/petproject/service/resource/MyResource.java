package me.vadyalex.petproject.service.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("myresource")
@Singleton
public class MyResource {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHtml() {

        LOGGER.info("Serving content..");

        return "{\"test\":true}";
    }

}
