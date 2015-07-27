package me.vadyalex.petproject.service.configuration;


import me.vadyalex.petproject.service.resource.MyResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.servlet.SparkApplication;

public class Configuration implements SparkApplication, AutoCloseable {

    public static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    @Override
    public void init() {

        final String PETPROJECT_ENV = System.getProperty("petproject.env");

        if (PETPROJECT_ENV == null) {
            LOGGER.info("PRODUCTION ENVIRONMENT");
        } else if (PETPROJECT_ENV.equalsIgnoreCase("dev")) {
            LOGGER.info("LOCAL DEVELOPMENT ENVIRONMENT");
        } else if (PETPROJECT_ENV.equalsIgnoreCase("mock")) {
            LOGGER.info("MOCKED INTEGRATION TEST");
        }

        Spark.get(
                "/myresource",
                new MyResource()
        );

    }

    @Override
    public void close() throws Exception {
        LOGGER.info("RELEASING RESOURCES..");
    }
}
