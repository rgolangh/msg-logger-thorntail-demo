package com.example.demo.rest;

import java.sql.SQLException;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.example.demo.rest.service.Dal;

@ApplicationScoped

@Path("/messages")
public class MessagesEndpoint {

    @Inject
    private Dal dal;

    @GET
    @Produces("application/json")
    public Response doGet() {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            Map<String, String> all = dal.getAll();
            all.forEach((k, v) -> objectBuilder.add(k, v));
        } catch (SQLException | NamingException e) {
            return Response.serverError().entity(e).build();
        }
        return Response.ok(objectBuilder.build()).build();
    }

    @GET
    @Path("send")
    @Produces("application/json")
    public Response send(@QueryParam("user") String user, @QueryParam("message") String message) {
        try {
            dal.save(user, message);
        } catch (SQLException | NamingException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

}
