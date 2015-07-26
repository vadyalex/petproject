package me.vadyalex.petproject.service.configuration;


import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Created by vadyalex.
 */
@WebListener
public class Configuration extends GuiceServletContextListener {

    public static final String JERSEY_RESOURCES_PACKAGE = "me.vadyalex.petproject";

    public static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    @Override
    protected final Injector getInjector() {
        LOGGER.trace("Initialize injector...");
        try {
            return Guice.createInjector(
                    // Services wiring..
                    new EnvConfiguration(),

                    // Jersey resources
                    new ServletModule() {
                        protected void configureServlets() {
                            LOGGER.info("Jersey resources wiring..");
                            serve("/*")
                                    .with(
                                            GuiceContainer.class,
                                            ImmutableMap.of(
                                                    PackagesResourceConfig.PROPERTY_PACKAGES, JERSEY_RESOURCES_PACKAGE
                                            )
                                    );
                        }
                    }
            );
        } catch (final Exception e) {
            LOGGER.error("ERROR", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        final Injector injector = (Injector) servletContextEvent
                .getServletContext()
                .getAttribute(Injector.class.getName());

        injector
                .getInstance(EnvConfiguration.class)
                .close();

        super.contextDestroyed(servletContextEvent);
    }
}
