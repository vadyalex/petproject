package me.vadyalex.petproject.service.resource;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.common.collect.ImmutableMap;
import me.vadyalex.petproject.service.service.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class MyResource implements Route {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);

    private final Meter meter;
    private final Repository repository;

    public MyResource(final HealthCheckRegistry healthCheckRegistry, MetricRegistry metricRegistry, final Repository repository) {
        Objects
                .requireNonNull(healthCheckRegistry)
                .register(
                        MyResource.class.getName(),
                        new HealthCheck() {
                            protected Result check() throws Exception {
                                return repository.get().isEmpty()?
                                        Result.unhealthy("REPOSITORY IS NOT AVAILABLE")
                                        :
                                        Result.healthy();
                            }
                        }
                );

        this.meter = Objects
                .requireNonNull(metricRegistry)
                .meter(
                        MyResource.class.getName()
                );

        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        meter.mark();

        return ImmutableMap.of(
                "message", repository.get()
        );
    }
}
