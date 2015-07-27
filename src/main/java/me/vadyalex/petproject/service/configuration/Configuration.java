package me.vadyalex.petproject.service.configuration;


import me.vadyalex.petproject.service.resource.MyResource;
import spark.Spark;
import spark.servlet.SparkApplication;

public class Configuration implements SparkApplication {

    @Override
    public void init() {

        Spark.get("/myresource", new MyResource());

    }
}
