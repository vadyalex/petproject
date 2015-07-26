package me.vadyalex.petproject.service.configuration;


import com.google.inject.AbstractModule;
import me.vadyalex.petproject.service.service.Repository;
import me.vadyalex.petproject.service.service.StorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * Created by vadyalex.
 */
public class EnvConfiguration extends AbstractModule implements Closeable {

    public static final Logger LOGGER = LoggerFactory.getLogger(EnvConfiguration.class);

    @Override
    protected void configure() {

        final String PETPROJECT_ENV = System.getenv("petproject.env");

        if (PETPROJECT_ENV == null) {
            LOGGER.info("PRODUCTION ENVIRONMENT");
        } else if (PETPROJECT_ENV.equalsIgnoreCase("dev")) {
            LOGGER.info("LOCAL DEVELOPMENT ENVIRONMENT");
        } else if (PETPROJECT_ENV.equalsIgnoreCase("mock")) {
            LOGGER.info("MOCKED INTEGRATION TEST");
        }

        bind(Repository.class).to(StorageRepository.class).asEagerSingleton();
    }

    @Override
    public void close() {
        LOGGER.info("RELEASING RESOURCES..");
        LOGGER.info("DONE.");
    }
}
