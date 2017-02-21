package com.acc.models;

import javax.json.*;

/**
 * Created by melsom.adrian on 15.02.2017.
 */
public class User implements IBusinessModel {

    public JsonObject toJson() {
        return Json.createObjectBuilder().add("Hello", "hello").build();
    }
}
