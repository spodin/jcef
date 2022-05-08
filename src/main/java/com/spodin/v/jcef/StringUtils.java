package com.spodin.v.jcef;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds utility methods to work with CEF related strings.
 *
 * @author spodin
 */
final class StringUtils {

    private static final Pattern INVALID_FIELD_PATTERN = Pattern.compile("[\r\n]");

    private static final Pattern ESCAPE_FIELD_PATTERN = Pattern.compile("([|\\\\])");

    private static final Pattern INVALID_EXTENSION_KEY_PATTERN = Pattern.compile("\\s");

    private static final Pattern ESCAPE_EXTENSION_KEY_PATTERN = Pattern.compile("=");

    private static final Pattern ESCAPE_EXTENSION_VALUE_PATTERN = Pattern.compile("[=\r\n]");

    private StringUtils() {
    }

    /**
     * Escapes field values.
     *
     * <p>According to CEF rules:</p>
     * <ul>
     *     <li>If a pipe (|) is used in the prefix (not extension), it has to be escaped
     *     with a backslash (\).</li>
     *     <li>If a backslash (\) is used in the prefix or the extension, it has to be escaped
     *     with another backslash (\).</li>
     * </ul>
     *
     * @param value field value
     * @return escaped value
     * @throws IllegalArgumentException if field contains invalid characters ({@code \n} or
     * {@code \r}, cause multiple lines are only allowed in the value part of the extensions)
     */
    public static String escapeField(String value) {
        if (value == null) {
            return null;
        }

        if (INVALID_FIELD_PATTERN.matcher(value).find()) {
            throw new IllegalArgumentException(
                String.format("Field value '%s' contains invalid character", value));
        }

        return ESCAPE_FIELD_PATTERN.matcher(value).replaceAll("\\\\$1");
    }

    /**
     * Escapes extension keys.
     *
     * <p>According to CEF rules: If an equal sign (=) is used in the extensions, it has to be
     * escaped with a backslash (\).</p>
     *
     * @param value extension key value
     * @return escaped value
     * @throws IllegalArgumentException if extension key contains invalid characters (white spaces)
     */
    public static String escapeExtensionKey(String value) {
        if (value == null) {
            return null;
        }

        if (INVALID_EXTENSION_KEY_PATTERN.matcher(value).find()) {
            throw new IllegalArgumentException(
                String.format("Extension key '%s' contains invalid character", value));
        }

        return ESCAPE_EXTENSION_KEY_PATTERN.matcher(value).replaceAll("\\\\=");
    }

    /**
     * Escape extension values.
     *
     * <p>According to CEF rules:</p>
     * <ul>
     *     <li>If an equal sign (=) is used in the extensions, it has to be escaped with a
     *     backslash (\).</li>
     *     <li>If a backslash (\) is used in the prefix or the extension, it has to be escaped with
     *     another backslash (\).</li>
     * </ul>
     *
     * @param value extension value
     * @return escaped value
     */
    public static String escapeExtensionValue(String value) {
        if (value == null) {
            return null;
        }

        final Matcher matcher = ESCAPE_EXTENSION_VALUE_PATTERN.matcher(value);
        final StringBuilder escaped = new StringBuilder(value.length());

        while (matcher.find()) {
            final char letter = matcher.group(0).charAt(0);
            String replacement;

            switch (letter) {
                case '\r':
                    replacement = "\\\\r";
                    break;
                case '\n':
                    replacement = "\\\\n";
                    break;
                case '\\':
                    replacement = "\\\\\\\\";
                    break;
                default:
                    replacement = "\\\\" + letter;
            }

            matcher.appendReplacement(escaped, replacement);
        }

        matcher.appendTail(escaped);
        return escaped.toString();
    }
}
