package com.spodin.v.jcef;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A collection of key-value pairs. The keys are part of a predefined set. The standard
 * allows for including additional keys. An event can contain any number of key-value pairs in
 * any order, separated by spaces (“ “). If a field contains a space, such as a file name,
 * this is valid.
 *
 * @author spodin
 */
public class Extension implements Serializable {

    private final Map<String, String> fields;

    private Extension(Builder builder) {
        this.fields = builder.fields;
    }

    Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Extension{fields=" + fields + '}';
    }

    /**
     * @return empty extension
     */
    public static Extension empty() {
        return new Builder(Collections.emptyMap()).build();
    }

    /**
     * @return extension builder
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

        public Builder add(String key, String value) {
            this.fields.put(key, value);
            return this;
        }

        public Extension build() {
            return new Extension(this);
        }
    }
}
