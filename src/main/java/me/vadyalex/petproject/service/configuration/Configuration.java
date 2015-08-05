package me.vadyalex.petproject.service.configuration;


import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.gson.Gson;
import me.vadyalex.petproject.service.resource.Deployment;
import me.vadyalex.petproject.service.resource.Healthcheck;
import me.vadyalex.petproject.service.resource.Metrics;
import me.vadyalex.petproject.service.resource.MyResource;
import me.vadyalex.petproject.service.service.Repository;
import me.vadyalex.petproject.service.service.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.servlet.SparkApplication;

public class Configuration implements SparkApplication, AutoCloseable {

    public static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    @Override
    public void init() {

        final String PETPROJECT_ENV = System.getProperty("petproject.env");

        switch (PETPROJECT_ENV) {
            case "dev": {
                LOGGER.info("LOCAL DEVELOPMENT ENVIRONMENT");
                break;
            }
            case "mock": {
                LOGGER.info("MOCKED INTEGRATION TEST");
                break;
            }
            default: {
                LOGGER.info("PRODUCTION ENVIRONMENT");
            }
        }

        final Repository repository = new StorageRepository();

        final HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

        final MetricRegistry metricRegistry = new MetricRegistry();

        Spark.get(
                "/myresource",
                new MyResource(healthCheckRegistry, metricRegistry, repository),
                o -> new Gson().toJson(o)
        );

        Spark.get(
                "/@deployment",
                new Deployment()
        );


        Spark.get(
                "/@healthcheck",
                new Healthcheck(healthCheckRegistry)
        );

        Spark.get(
                "/@metrics",
                new Metrics(metricRegistry),
                o -> new Gson().toJson(o)
        );

    }

    @Override
    public void close() throws Exception {
        LOGGER.info("RELEASING RESOURCES..");
    }
}
