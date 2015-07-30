package me.vadyalex.petproject.service.configuration;


import com.google.gson.Gson;
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

        Spark.get(
                "/myresource",
                new MyResource(repository),
                o -> new Gson().toJson(o)
        );

    }

    @Override
    public void close() throws Exception {
        LOGGER.info("RELEASING RESOURCES..");
    }
}
