package com.spodin.v.jcef;

/**
 * @author spodin
 */
final class Assertions {

    private Assertions() {
    }

    public static String notNullOrBlank(String value, String message) {
        validState((value != null && !value.isBlank()), message);
        return value;
    }

    public static <T> T notNull(T obj, String message) {
        validState(obj != null, message);
        return obj;
    }

    public static void validState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
