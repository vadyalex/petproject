package me.vadyalex.petproject.service.configuration;


import com.google.gson.Gson;
import me.vadyalex.petproject.service.resource.MyResource;
import me.vadyalex.petproject.service.service.Repository;
import me.vadyalex.petproject.service.service.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.servlet.SparkApplication;

import java.io.InputStream;
import java.util.Properties;

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


        Spark.get(
                "/@deployment",
                (request, response) -> {

                    final InputStream stream = Configuration.class.getClassLoader().getResourceAsStream("deployment.properties");

                    if (stream != null) {
                        final Properties properties = new Properties();
                        properties.load(stream);

                        return properties
                                .entrySet()
                                .stream()
                                .map(
                                        entry -> entry.getKey() + ":" + entry.getValue() + '\n'
                                )
                                .reduce(
                                        new StringBuilder("\n@deployment\n"),
                                        StringBuilder::append,
                                        StringBuilder::append
                                )
                                .toString();

                    }

                    return "\n@deployment\n";
                }
        );

    }

    @Override
    public void close() throws Exception {
        LOGGER.info("RELEASING RESOURCES..");
    }
}
