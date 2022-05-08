package com.spodin.v.jcef;

import java.io.Serializable;

/**
 * Represents CEF event parameters.
 *
 * @author spodin
 */
public class CefEvent implements Serializable {

    private static final String FORMAT_IDENTIFIER_PREFIX = "CEF";

    private final int version;
    private final Device device;
    private final String id;
    private final String name;
    private final int severity;
    private final Extension extension;

    private CefEvent(Builder builder) {
        validState((builder.version >= 0), "Version must be >= 0");
        this.version = builder.version;

        this.device = notNull(builder.device, "Device is required");
        this.id = notNullOrBlank(builder.id, "id is required");
        this.name = notNullOrBlank(builder.name, "name is required");

        validState(
            (builder.severity != null && builder.severity >= 0 && builder.severity <= 10),
            "Severity value from 0 to 10 is allowed");
        this.severity = builder.severity;

        this.extension = builder.extension == null ? Extension.empty() : builder.extension;
    }

    private static String notNullOrBlank(String value, String message) {
        validState((value != null && !value.isBlank()), message);
        return value;
    }

    private static <T> T notNull(T obj, String message) {
        validState(obj != null, message);
        return obj;
    }

    private static void validState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @return CEF format identifier in form of PREFIX:VERSION (e.g.: {@code CEF:0})
     */
    public String getFormatIdentifier() {
        return String.format("%s:%d", FORMAT_IDENTIFIER_PREFIX, version);
    }

    /**
     * @return CEF format version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @return event sending device type
     */
    public Device getDevice() {
        return device;
    }

    /**
     * @return unique identifier per event-type. This can be a string or an integer.
     * Signature ID identifies the type of event reported. In the intrusion detection system
     * (IDS) world, each signature or rule that detects certain activity has a unique signature
     * ID assigned
     */
    public String getId() {
        return id;
    }

    /**
     * @return representing a human-readable and understandable description of the event.
     * The event name should not contain information that is specifically mentioned in other fields
     * (for example: “Port scan from 10.0.0.1 targeting 20.1.1.1” is not a good event name. It
     * should be: “Port scan”. The other information is redundant and can be picked up from the
     * other fields)
     */
    public String getName() {
        return name;
    }

    /**
     * @return importance of this event
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * @return event extension
     */
    public Extension getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "Cef{" +
            "formatIdentifier='" + getFormatIdentifier() + '\'' +
            ", version=" + version +
            ", device=" + device +
            ", id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", severity=" + severity +
            ", extension=" + extension +
            '}';
    }

    /**
     * @return CEF event builder
     * @throws IllegalArgumentException on missing or incorrect parameters
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final int DEFAULT_FORMAT_VERSION = 0;

        private int version = DEFAULT_FORMAT_VERSION;
        private Device device;
        private String id;
        private String name;
        private Integer severity;
        private Extension extension;

        private Builder() {
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        public Builder device(Device device) {
            this.device = device;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder severity(int severity) {
            this.severity = severity;
            return this;
        }

        public Builder extension(Extension extension) {
            this.extension = extension;
            return this;
        }

        public CefEvent build() {
            return new CefEvent(this);
        }
    }
}
