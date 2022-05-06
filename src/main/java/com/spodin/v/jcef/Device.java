package com.spodin.v.jcef;

import java.io.Serializable;
import java.util.Objects;

/**
 * Device values are strings that uniquely identify the type of sending device. No two products may
 * use the same device-vendor and device-product pair. There is no central authority managing
 * these pairs. Event producers have to ensure that they assign unique name pairs.
 *
 * @author spodin
 */
public class Device implements Serializable {

    private final String vendor;
    private final String product;
    private final String version;

    private Device(Builder builder) {
        this.vendor = notNullOrBlank(builder.vendor, "Vendor is required");
        this.product = notNullOrBlank(builder.product, "Product is required");
        this.version = notNullOrBlank(builder.version, "Version is required");
    }

    private static String notNullOrBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public String getVendor() {
        return vendor;
    }

    public String getProduct() {
        return product;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        return vendor.equals(device.vendor) && product.equals(device.product) && version.equals(
            device.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendor, product, version);
    }

    @Override
    public String toString() {
        return "Device{" +
            "vendor='" + vendor + '\'' +
            ", product='" + product + '\'' +
            ", version='" + version + '\'' +
            '}';
    }

    /**
     * @return device instance builder
     * @throws IllegalArgumentException on missing or incorrect parameters
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String vendor;
        private String product;
        private String version;

        private Builder() {
        }

        public Builder vendor(String vendor) {
            this.vendor = vendor;
            return this;
        }

        public Builder product(String product) {
            this.product = product;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Device build() {
            return new Device(this);
        }
    }
}
