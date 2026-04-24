package com.mycompany.cscrswrk;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class ClientCourseWork {

    public static final String BASE_URI = "http://localhost:8080/api/v1/";

    public static void main(String[] args) throws IOException {
        ResourceConfig config = new ResourceConfig()
                .packages("resource", "exception", "filter");

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                config
        );

        System.out.println("Smart Campus API running at " + BASE_URI);
        System.out.println("Press ENTER to stop server.");
        System.in.read();

        server.shutdownNow();
    }
}