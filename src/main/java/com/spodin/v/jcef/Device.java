package com.spodin.v.jcef;

import java.io.Serializable;
import java.util.Objects;

/**
 * CEF event device.
 *
 * @author spodin
 */
public class Device implements Serializable {

    private final String vendor;
    private final String product;
    private final String version;

    private Device(Builder builder) {
        Assertions.notNullOrBlank(builder.vendor, "Vendor is required");
        this.vendor = builder.vendor;

        Assertions.notNullOrBlank(builder.product, "Product is required");
        this.product = builder.product;

        Assertions.notNullOrBlank(builder.version, "Version is required");
        this.version = builder.version;
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
     * Creates CEF event device builder.
     *
     * @return event device builder
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

        /**
         * Sets event device vendor.
         *
         * <p>Mandatory.</p>
         *
         * @param vendor vendor
         * @return this builder for further customizations
         */
        public Builder vendor(String vendor) {
            this.vendor = vendor;
            return this;
        }

        /**
         * Sets event device product.
         *
         * <p>Mandatory.</p>
         *
         * @param product product
         * @return this builder for further customizations
         */
        public Builder product(String product) {
            this.product = product;
            return this;
        }

        /**
         * Sets event device version.
         *
         * <p>Mandatory.</p>
         *
         * @param version version
         * @return this builder for further customizations
         */
        public Builder version(String version) {
            this.version = version;
            return this;
        }

        /**
         * Creates CEF event device with submitted parameters.
         *
         * @return CEF event device
         * @throws IllegalArgumentException on missing or illegal parameters
         */
        public Device build() {
            return new Device(this);
        }
    }
}
