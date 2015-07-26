package me.vadyalex.petproject.service.resource;

import com.google.inject.Inject;
import me.vadyalex.petproject.service.service.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

@Path("myresource")
@Singleton
public class MyResource {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);

    private final Repository repository;

    @Inject
    public MyResource(Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHtml() {

        LOGGER.info("Serving content..");

        return repository.get();
    }

}
