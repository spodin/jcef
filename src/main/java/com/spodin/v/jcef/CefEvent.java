package com.spodin.v.jcef;

import java.io.Serializable;

/**
 * CEF event parameters.
 *
 * @author spodin
 */
public class CefEvent implements Serializable {

    private static final String FORMAT_IDENTIFIER_PREFIX = "CEF";

    private final int version;
    private final Device device;
    private final EventId eventId;
    private final String severity;
    private final Extension extension;

    private CefEvent(Builder builder) {
        Assert.validState((builder.version >= 0), "Version must be >= 0");
        this.version = builder.version;

        Assert.notNull(builder.device, "Device is required");
        this.device = builder.device;

        Assert.notNull(builder.eventId, "EventId is required");
        this.eventId = builder.eventId;

        Assert.notNullOrBlank(builder.severity, "Severity is required");
        this.severity = builder.severity;

        this.extension = (builder.extension == null ? Extension.empty() : builder.extension);
    }

    public String getFormatIdentifier() {
        return String.format("%s:%d", FORMAT_IDENTIFIER_PREFIX, version);
    }

    public int getVersion() {
        return version;
    }

    public Device getDevice() {
        return device;
    }

    public String getId() {
        return eventId.getId();
    }

    public String getName() {
        return eventId.getName();
    }

    public String getSeverity() {
        return severity;
    }

    public Extension getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "CefEvent{" +
            "formatIdentifier='" + getFormatIdentifier() + '\'' +
            ", version=" + version +
            ", device=" + device +
            ", eventId=" + eventId +
            ", severity='" + severity + '\'' +
            ", extension=" + extension +
            '}';
    }

    /**
     * Creates CEF event builder.
     *
     * @return event builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private static final int DEFAULT_FORMAT_VERSION = 0;

        private int version = DEFAULT_FORMAT_VERSION;
        private Device device;
        private EventId eventId;
        private String severity;
        private Extension extension;

        private Builder() {
        }

        /**
         * Sets version of the CEF format. Event consumers use this information to determine
         * what the following fields represent.
         *
         * <p>Optional, default value is {@value #DEFAULT_FORMAT_VERSION}.</p>
         *
         * @param version CEF version, must be greater than or equal to 0
         * @return this builder for further customizations
         */
        public Builder version(int version) {
            this.version = version;
            return this;
        }

        /**
         * Device uniquely identify the type of sending device. No two products may use the same
         * device-vendor and device-product pair. There is no central authority managing these
         * pairs. Event producers must ensure that they assign unique name pairs.
         *
         * <p>Mandatory.</p>
         *
         * @param device device
         * @return this builder for further customizations
         */
        public Builder device(Device device) {
            this.device = device;
            return this;
        }

        /**
         * Sets event identifier.
         *
         * <p>Mandatory.</p>
         *
         * @param eventId event identifier
         * @return this builder for further customizations
         */
        public Builder eventId(EventId eventId) {
            this.eventId = eventId;
            return this;
        }

        /**
         * Shorthand method for {@link #severity(String)}.
         *
         * @param severity event severity
         * @return this builder for further customizations
         */
        public Builder severity(int severity) {
            return severity(String.valueOf(severity));
        }

        /**
         * Sets a string or integer and reflects the importance of the event. The valid string
         * values are Unknown, Low, Medium, High, and Very-High. The valid integer values are
         * 0-3=Low, 4-6=Medium, 7- 8=High, and 9-10=Very-High.
         *
         * <p>Mandatory.</p>
         *
         * @param severity event severity
         * @return this builder for further customizations
         */
        public Builder severity(String severity) {
            this.severity = severity;
            return this;
        }

        /**
         * Sets extension field contains a collection of key-value pairs. The keys are part of a
         * predefined set. The extension portion of the message is a placeholder for additional
         * fields, but is not mandatory.
         *
         * <p>Optional.</p>
         *
         * @param extension event extension
         * @return this builder for further customizations
         */
        public Builder extension(Extension extension) {
            this.extension = extension;
            return this;
        }

        /**
         * Creates CEF event with submitted parameters.
         *
         * @return CEF event
         * @throws IllegalArgumentException on missing or illegal parameters
         */
        public CefEvent build() {
            return new CefEvent(this);
        }
    }
}
