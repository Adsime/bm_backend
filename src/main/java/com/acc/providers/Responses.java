package com.acc.providers;

import javax.xml.ws.Response;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by melsom.adrian on 09.05.2017.
 */
public class Responses {

    private static final String fileName = "responses.xml";
    private static Properties properties;

    static {
        try {
            InputStream is = Response.class.getResourceAsStream(fileName);
            properties = new Properties();
            properties.loadFromXML(is);
            is.close();

        } catch (Exception e) {

        }
    }
    public static String getResponse(String key) {
        return properties.getProperty(key);
    }
}
