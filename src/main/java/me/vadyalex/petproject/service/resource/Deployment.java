package me.vadyalex.petproject.service.resource;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class Deployment implements Route {

    public static final Logger LOGGER = LoggerFactory.getLogger(Deployment.class);

    private final Map properties;

    public Deployment() {

        try {

            this.properties = new Gson().fromJson(
                    Resources.toString(
                            Deployment.class.getClassLoader().getResource("deployment.json"),
                            Charsets.UTF_8
                    ),
                    Map.class
            );

        } catch (Exception e) {
            LOGGER.error("Error loading 'deployment.json' {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.type("text/plain");

        return Joiner
                .on("\n")
                .withKeyValueSeparator(" --> ")
                .join(
                        properties
                );
    }
}
