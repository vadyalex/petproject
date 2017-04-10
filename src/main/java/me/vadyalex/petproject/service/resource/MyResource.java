package me.vadyalex.petproject.service.resource;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Path("myresource")
@Singleton
public class MyResource {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);

    private final Repository repository;
    private final ExecutorService executorService;

    @Inject
    public MyResource(Repository repository, ExecutorService executorService) {
        this.repository = Objects.requireNonNull(repository);
        this.executorService = Objects.requireNonNull(executorService);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {

        asyncResponse.setTimeoutHandler(
                response -> {
                    LOGGER.warn("Operation execution timed out..");
                    asyncResponse.resume(
                            Response.status(Response.Status.SERVICE_UNAVAILABLE).build()
                    );
                }
        );
        asyncResponse.setTimeout(
                5, TimeUnit.SECONDS
        );

        try {
            executorService
                    .submit(
                            () -> {
                                LOGGER.info("Serving content..");
                                asyncResponse.resume(
                                        Response
                                                .ok()
                                                .entity(
                                                        repository.get()
                                                )
                                                .build()
                                );
                            }
                    )
                    .get(
                            6, TimeUnit.SECONDS
                    );
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.warn("Execution exception!", e);
        }

    }

}
