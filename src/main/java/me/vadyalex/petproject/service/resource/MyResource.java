package me.vadyalex.petproject.service.resource;

import com.netflix.hystrix.*;
import me.vadyalex.petproject.service.service.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {

        new HystrixCommand<String>(
                HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("petproject"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("myresource"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("endpoint"))
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)
                        )
        ) {
            protected String run() throws Exception {
                return repository.get();
            }
        }
                .observe()
                .subscribe(
                        result -> {
                            LOGGER.info("Serving content..");
                            asyncResponse.resume(
                                    Response.ok().entity(result).build()
                            );
                        },
                        throwable -> {
                            LOGGER.warn("Operation execution failed..");
                            asyncResponse.resume(
                                    Response.status(Response.Status.SERVICE_UNAVAILABLE).build()
                            );

                        }
                );
    }

}
