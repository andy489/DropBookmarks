package org.example.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.YamlConfigReaderConfiguration;

@Path("/{parameter: yaml|yml}")
public class ExampleResource {
    private final YamlConfigReaderConfiguration config;
    public ExampleResource(YamlConfigReaderConfiguration configuration) {
        this.config = configuration;
    }
    @GET
    @Path("/my-arg")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMyArg() {
        return config.getMyMetrics().getMyArg();
    }

    @GET
    @Path("/my-list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyList() {
        return Response.ok(config.getMyMetrics().getMyList()).build();
    }

    @GET
    @Path("/my-list-of-lists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyListOfLists() {
        return Response.ok(config.getMyMetrics().getMyListOfLists()).build();
    }

    @GET
    @Path("/my-map")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyMap() {
        return Response.ok(config.getMyMetrics().getMyMap()).build();
    }

    @GET
    @Path("/my-map-of-lists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyMapOfLists() {
        System.out.println();
        
        return Response.ok(config.getMyMetrics().getMyMapOfLists()).build();
    }

}
