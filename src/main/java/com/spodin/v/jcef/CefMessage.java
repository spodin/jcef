package com.spodin.v.jcef;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents formatted CEF message.
 *
 * @author spodin
 */
public class CefMessage implements Serializable {

    private static final String FIELDS_DELIMITER = "|";

    private final String value;

    public CefMessage(CefEvent event) {
        this.value = composeMessage(event);
    }

    private String composeMessage(CefEvent event) {
        return String.join(FIELDS_DELIMITER,
            event.getFormatIdentifier(),
            StringUtils.escapeField(event.getDevice().getVendor()),
            StringUtils.escapeField(event.getDevice().getProduct()),
            StringUtils.escapeField(event.getDevice().getVersion()),
            StringUtils.escapeField(event.getId()),
            StringUtils.escapeField(event.getName()),
            String.valueOf(event.getSeverity()),
            extensionString(event.getExtension()));
    }

    private String extensionString(Extension extension) {
        return extension.getFields()
            .entrySet().stream()
            .filter(it -> (it.getKey() != null && it.getValue() != null))
            .map(it -> Map.entry(
                StringUtils.escapeExtensionKey(it.getKey()),
                StringUtils.escapeExtensionValue(it.getValue())))
            .map(it -> String.format("%s=%s", it.getKey(), it.getValue()))
            .collect(Collectors.joining(" "));
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
