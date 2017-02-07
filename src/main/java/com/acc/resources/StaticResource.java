package com.acc.resources;

import com.acc.models.Group;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("static")
public class StaticResource {

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
                "{\n" +
                "  \"id\": \"1\",\n" +
                "  \"problem\": \"Dat boi\",\n" +
                "  \"title\": \"Yes, dat boi\",\n" +
                "  \"author\": \"1\"\n" +
                "},\n" +
                "{\n" +
                "  \"id\": \"2\",\n" +
                "  \"problem\": \"Dette er også en oppgave\",\n" +
                "  \"title\": \"Det var en gang\",\n" +
                "  \"author\": \"1\"\n" +
                "},\n" +
                "{\n" +
                "  \"id\": \"3\",\n" +
                "  \"problem\": \"Bon bo hue pho bo bahn mi dap mei du ma pho ga\",\n" +
                "  \"title\": \"Vietnamesisk\",\n" +
                "  \"author\": \"1\"\n" +
                "}\n" +
                "]";
        return Response.ok(body).build();
    }

    @Path("supervisors")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisors() {
        System.out.println("GET - supervisors");
        String body = "[\n" +
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
                "]";

        return Response.ok(body).build();
    }

    @Path("groups")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroups() {
        System.out.println("GET - groups");
        String body = "[\n" +
                " {\n" +
                "   \"name\": \"Adrian\",\n" +
                "   \"school\": \"Melsom\",\n" +
                "   \"assignment\": \"1\",\n" +
                "   \"studentList\": [\n" +
                "     {\n" +
                "       \"firstname\": \"Adrian\",\n" +
                "       \"lastname\": \"Smørvik\",\n" +
                "       \"email\": \"hakon.smoervik@live.no\",\n" +
                "       \"enterpriseid\": \"1231231\"\n" +
                "     },\n" +
                "     {\n" +
                "       \"firstname\": \"Håkon\",\n" +
                "       \"lastname\": \"Melsom\",\n" +
                "       \"email\": \"melsom\",\n" +
                "       \"enterpriseid\": \"1312523\"\n" +
                "     }\n" +
                "   ],\n" +
                "   \"supervisorList\": [\n" +
                "     {\n" +
                "       \"id\": \"2\"\n" +
                "     }\n" +
                "   ]\n" +
                " },\n" +
                " {\n" +
                "   \"name\": \"Bjørn og Binna\",\n" +
                "   \"school\": \"Skogen\",\n" +
                "   \"assignment\": \"1\",\n" +
                "   \"studentList\": [\n" +
                "     {\n" +
                "       \"firstname\": \"Binna\",\n" +
                "       \"lastname\": \"Bjørnehi\",\n" +
                "       \"email\": \"binna@hiet.bj\",\n" +
                "       \"enterpriseid\": \"1231231\"\n" +
                "     },\n" +
                "     {\n" +
                "       \"firstname\": \"Bjørn\",\n" +
                "       \"lastname\": \"Bjørnehi\",\n" +
                "       \"email\": \"bjørn@hiet.bj\",\n" +
                "       \"enterpriseid\": \"12312412\"\n" +
                "     },\n" +
                "     {\n" +
                "       \"firstname\": \"Valpen\",\n" +
                "       \"lastname\": \"Bjørnehi\",\n" +
                "       \"email\": \"valpen@hiet.bj\",\n" +
                "       \"enterpriseid\": \"1243436\"\n" +
                "     }\n" +
                "   ],\n" +
                "   \"supervisorList\": [\n" +
                "     {\n" +
                "       \"id\": \"1\"\n" +
                "     },\n" +
                "     {\n" +
                "       \"id\": \"4\"\n" +
                "     }\n" +
                "   ]\n" +
                " }\n" +
                "]";

        return Response.ok(body).build();
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


    @Path("stdoutform")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response write(Group group) {
        System.out.println("POST - write");
        System.out.println(group.toString());
        return Response.ok("Accepted").build();
    }

    @Path("stdout")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response writeToCmd(JsonObject o) {
        System.out.println("POST - write");
        System.out.println(o);
        return Response.ok("Accepted").build();
    }
}
