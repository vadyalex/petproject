package me.vadyalex.petproject.service.resource;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class Metrics implements Route {

    public static final Logger LOGGER = LoggerFactory.getLogger(Metrics.class);

    private final MetricRegistry metricRegistry;

    public Metrics(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.type("text/plain");

        return this.metricRegistry.getMetrics();
    }
}
