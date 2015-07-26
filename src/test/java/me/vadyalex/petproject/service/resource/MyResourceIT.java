package me.vadyalex.petproject.service.resource;


import com.eclipsesource.restfuse.*;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.eclipsesource.restfuse.Assert.assertOk;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(HttpJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyResourceIT {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyResourceIT.class);

    public static final String SERVER = "http://localhost";
    public static final String port = System.getProperty("integration.test.port");

    @Rule
    public Destination destination = new Destination(SERVER + ":" + port);

    @Context
    private Response response; // will be injected after every request


    @HttpTest(method = Method.GET, path = "myresource", type = MediaType.APPLICATION_JSON)
    public void an_initial_count() {
        assertOk(response);

        final String body = response.getBody(String.class);

        LOGGER.debug(" -> {}", body);

        assertThat(body).isEqualToIgnoringCase("HELLO!");
    }

}
