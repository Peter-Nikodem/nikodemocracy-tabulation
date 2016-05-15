package net.nikodem.util;

public class ValidationPreconditions {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
