package za.ac.cput.domain;

import jakarta.persistence.*;

@Embeddable
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    protected Address() {}

    private Address(Builder builder) {
        this.addressId = builder.addressId;
        this.street = builder.street;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
        this.user = builder.user;
    }

    public Long getAddressId() {
        return addressId;
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
    public User getUser() {
        return user;
    }

    public static class Builder {
        private Long addressId;
        private String street;
        private String city;
        private String province;
        private String postalCode;
        private String country;
        private User user;

        public Builder setAddressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }
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
        public Builder setUser(User user) {
            this.user = user; return this;
        }

        public Builder copy(Address address){
            this.addressId = address.addressId;
            this.street = address.street;
            this.city = address.city;
            this.province = address.province;
            this.postalCode = address.postalCode;
            this.country = address.country;
            this.user = address.user;
            return this;
        }
        public Address build() {
            return new Address(this);
        }
    }
}
