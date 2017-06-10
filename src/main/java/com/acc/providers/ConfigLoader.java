package com.acc.providers;

import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Adrian PC on 10.06.2017.
 */
public class ConfigLoader {

    private static Properties properties;
    private static Logger LOGGER = Logger.getLogger("application");
    private static String configFile = "apiConfig";

    static {
        try {
            ClassLoader cl = ConfigLoader.class.getClass().getClassLoader();
            FileReader reader = new FileReader(cl.getResource(configFile).getFile());
            properties = new Properties();
            properties.load(reader);
        } catch (FileNotFoundException fnfe) {
            LOGGER.error("Unable to load file '" + configFile + "' in class ConfigLoader");
        } catch(IOException ioe) {
            LOGGER.error("Unable to read file '" + configFile + "' in class ConfigLoader");
        }
    }

    public static String load(String key) throws Exception {
        try {
            return properties.getProperty(key);
        } catch (Exception e) {
            LOGGER.warn("Unable to load property '" + key + "' from " + configFile + " in class ConfigLoader");
            throw new Exception(e);
        }
    }
}
