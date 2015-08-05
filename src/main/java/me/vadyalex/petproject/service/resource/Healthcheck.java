package me.vadyalex.petproject.service.resource;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class Healthcheck implements Route {

    public static final Logger LOGGER = LoggerFactory.getLogger(Healthcheck.class);

    private final HealthCheckRegistry healthCheckRegistry;

    public Healthcheck(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.type("text/plain");

        return Joiner
                .on("\n")
                .withKeyValueSeparator(" --> ")
                .join(
                        healthCheckRegistry.runHealthChecks()
                );
    }
}
