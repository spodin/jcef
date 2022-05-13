package com.spodin.v.jcef;

/**
 * Contains utility methods to validate arguments.
 *
 * @author spodin
 */
final class Assert {

    private Assert() {
    }

    /**
     * Validate that specified argument string is either not null, blank string; otherwise throwing
     * an exception.
     *
     * @param value value to validate
     * @param message exception message if invalid
     * @return validated string
     * @throws IllegalArgumentException if specified string is not valid
     */
    public static String notNullOrBlank(String value, String message) {
        validState((value != null && !value.isBlank()), message);
        return value;
    }

    /**
     * Validate that specified argument object is not null; otherwise throwing an exception.
     *
     * @param obj object to validate
     * @param message exception message if invalid
     * @param <T> object type
     * @return validated object
     * @throws IllegalArgumentException if specified object is null
     */
    public static <T> T notNull(T obj, String message) {
        validState(obj != null, message);
        return obj;
    }

    /**
     * Validate that specified condition is {@code true}; otherwise throwing an exception.
     *
     * @param expression boolean expression to check
     * @param message exception message if invalid
     * @throws IllegalArgumentException if specified condition is {@code false}
     */
    public static void validState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
