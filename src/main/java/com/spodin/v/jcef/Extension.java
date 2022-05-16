package com.spodin.v.jcef;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CEF event extension.
 *
 * @author spodin
 */
public class Extension implements Serializable {

    private static final Extension EMPTY = new Builder(Collections.emptyMap()).build();

    private final Map<String, String> fields;

    private Extension(Builder builder) {
        this.fields = Collections.unmodifiableMap(builder.fields);
    }

    Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Extension{fields=" + fields + '}';
    }

    /**
     * Creates empty extension.
     *
     * @return empty extension
     */
    public static Extension empty() {
        return EMPTY;
    }

    /**
     * Creates CEF event extension builder.
     *
     * @return event extension builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<String, String> fields;

        private Builder() {
            this(new LinkedHashMap<>());
        }

        private Builder(Map<String, String> fields) {
            this.fields = fields;
        }

        /**
         * Adds field (key-value pair) to the extension.
         *
         * @param key field key
         * @param value field value
         * @return this builder for further additions
         */
        public Builder add(String key, String value) {
            this.fields.put(key, value);
            return this;
        }

        /**
         * Creates CEF event extension with submitted parameters.
         *
         * @return CEF event extension
         */
        public Extension build() {
            return new Extension(this);
        }
    }
}
