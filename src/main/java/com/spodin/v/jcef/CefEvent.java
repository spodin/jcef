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
    private final String id;
    private final String name;
    private final String severity;
    private final Extension extension;

    private CefEvent(Builder builder) {
        Assert.validState((builder.version >= 0), "Version must be >= 0");
        this.version = builder.version;

        Assert.notNull(builder.device, "Device is required");
        this.device = builder.device;

        Assert.notNullOrBlank(builder.id, "Id is required");
        this.id = builder.id;

        Assert.notNullOrBlank(builder.name, "Name is required");
        this.name = builder.name;

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
        return id;
    }

    public String getName() {
        return name;
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
            ", id='" + id + '\'' +
            ", name='" + name + '\'' +
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
        private String id;
        private String name;
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
         * Sets event id (Device Event Class ID) is a unique identifier per event-type. This can be
         * a string or an integer. Device Event ClassID identifies the type of event reported. In
         * the intrusion detection system (IDS) world, each signature or rule that detects certain
         * activity has a unique Device Event Class ID assigned. This is a requirement for other
         * types of devices as well, and helps correlation engines process the events.
         * Also known as Signature ID.
         *
         * <p>Mandatory.</p>
         *
         * @param id event id
         * @return this builder for further customizations
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets a string representing a human-readable and understandable description of the event.
         *
         * <p>The event name should not contain information that is specifically mentioned in other
         * fields. For example: "Port scan from 10.0.0.1targeting 20.1.1.1" is not a good event name.
         * It should be: "Port scan". The other information is redundant and can be picked up from
         * the other fields.</p>
         *
         * <p>Mandatory.</p>
         *
         * @param name event name
         * @return this builder for further customizations
         */
        public Builder name(String name) {
            this.name = name;
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
