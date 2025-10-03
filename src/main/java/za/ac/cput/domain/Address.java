package za.ac.cput.domain;

import jakarta.persistence.*;

@Embeddable
public class Address {
    private String city;
    private String country;
    private String postalCode;
    private String province;
    private String street;



    protected Address() {}

    private Address(Builder builder) {
        this.street = builder.street;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
    }

    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getProvince() {
        return province;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getCountry() {
        return country;
    }


    public static class Builder {
        private String street;
        private String city;
        private String province;
        private String postalCode;
        private String country;

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }
        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }
        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }
        public Builder setCountry(String country) {
            this.country = country; return this;
        }


        public Builder copy(Address address){
            this.street = address.street;
            this.city = address.city;
            this.province = address.province;
            this.postalCode = address.postalCode;
            this.country = address.country;
            return this;
        }
        public Address build() {
            return new Address(this);
        }
    }
}
