package com.acc.jsonWebToken;

import java.util.Base64;

/**
 * Created by melsom.adrian on 30.03.2017.
 */
public class Coder {

    public static String encode(String s) {
        try {
            return new String(Base64.getEncoder().encode(s.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

    public static String decode(String s) {
        return new String(Base64.getDecoder().decode(s));
    }
}
