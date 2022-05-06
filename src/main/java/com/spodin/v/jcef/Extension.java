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

    private final Map<String, String> fields = new LinkedHashMap<>();

    public Extension add(String key, String value) {
        fields.put(key, value);
        return this;
    }

    Map<String, String> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    @Override
    public String toString() {
        return "Extension{fields=" + fields + '}';
    }
}
