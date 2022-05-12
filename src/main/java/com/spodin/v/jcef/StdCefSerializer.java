package com.spodin.v.jcef;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serializes CEF event to {@code String} according to ArcSight Common Event Format specification.
 *
 * @author spodin
 */
public class StdCefSerializer implements CefSerializer<String> {

    private static final String FIELDS_DELIMITER = "|";

    @Override
    public String serialize(CefEvent event) {
        return String.join(FIELDS_DELIMITER,
            event.getFormatIdentifier(),
            StringUtils.escapeField(event.getDevice().getVendor()),
            StringUtils.escapeField(event.getDevice().getProduct()),
            StringUtils.escapeField(event.getDevice().getVersion()),
            StringUtils.escapeField(event.getId()),
            StringUtils.escapeField(event.getName()),
            event.getSeverity(),
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
}
