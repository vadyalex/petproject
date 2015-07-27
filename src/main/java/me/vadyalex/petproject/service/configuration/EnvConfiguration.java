package me.vadyalex.petproject.service.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by vadyalex.
 */
public class EnvConfiguration implements ServletContextListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(EnvConfiguration.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final String PETPROJECT_ENV = System.getProperty("petproject.env");

        if (PETPROJECT_ENV == null) {
            LOGGER.info("PRODUCTION ENVIRONMENT");
        } else if (PETPROJECT_ENV.equalsIgnoreCase("dev")) {
            LOGGER.info("LOCAL DEVELOPMENT ENVIRONMENT");
        } else if (PETPROJECT_ENV.equalsIgnoreCase("mock")) {
            LOGGER.info("MOCKED INTEGRATION TEST");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("RELEASING RESOURCES..");
        LOGGER.info("DONE.");
    }
}
