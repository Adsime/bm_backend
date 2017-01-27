package com.acc.controller;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by melsom.adrian on 25.01.2017.
 */

@Path("res")
public class Controller {

    @Path("ping")
    @GET
    public String ping() {
        System.out.println("GET request: ping");
        return "Pong!";
    }

    @Path("assignments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssignments() {
        System.out.println("GET - assignments");
        String body = "[\n" +
                "     {id: 1, problem: \"YOUR MOM\", title: \"oppgave1\", author: this.displayedSupervisorList[0]},\n" +
                "     {id: 2, problem: \"YOUR DAD\", title: \"oppgave2\", author: this.displayedSupervisorList[0]},\n" +
                "     {id: 3, problem: \"YOUR HÅKON\", title: \"oppgave3\", author: this.displayedSupervisorList[0]},\n" +
                "   ]";
        return Response.accepted(body).header("Access-Control-Allow-Origin", "*").build();
    }

    @Path("supervisors")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisors() {
        System.out.println("GET - supervisors");
        String body = "{" +
                "\"data\": [\n" +
                " {\n" +
                "   \"id\": \"1\",\n" +
                "   \"firstname\": \"Jostein\",\n" +
                "   \"lastname\": \"Guldal\",\n" +
                "   \"enterpriseid\": \"jostein.guldal\",\n" +
                "   \"tags\": \"supervisor\"\n" +
                " },\n" +
                " {\n" +
                "   \"id\": \"2\",\n" +
                "   \"firstname\": \"Joakim\",\n" +
                "   \"lastname\": \"Kartveit\",\n" +
                "   \"enterpriseid\": \"joakim.kartveit\",\n" +
                "   \"tags\": \"supervisor\"\n" +
                " },\n" +
                " {\n" +
                "   \"id\": \"3\",\n" +
                "   \"firstname\": \"Simon\",\n" +
                "   \"lastname\": \"Litlehamar\",\n" +
                "   \"enterpriseid\": \"simon.litlehamar\",\n" +
                "   \"tags\": \"supervisor\"\n" +
                " },\n" +
                " {\n" +
                "   \"id\": \"4\",\n" +
                "   \"firstname\": \"Fredrik\",\n" +
                "   \"lastname\": \"Bjørnøy\",\n" +
                "   \"enterpriseid\": \"fredrik.bjornoy\",\n" +
                "   \"tags\": \"supervisor\"\n" +
                " }\n" +
                "]" +
                "}";

        return Response.accepted(body).header("Access-Control-Allow-Origin", "*").build();
    }

    @Path("post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String postExample(JsonArray array) {
        String retType = "";
        for(int i = 0; i < array.size(); i++) {
        JsonObject object = array.getJsonObject(i);
        for(String key : object.keySet()) {
            retType += "<li>"+ key + " = " + object.getString(key) +"</li>";
        }
        retType += "</ul>";
        retType += "</br></br>";
    }
        return retType;
}

}