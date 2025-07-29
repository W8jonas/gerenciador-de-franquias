package domain.model;

import java.util.Objects;

public class Address {
    private String id;
    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public Address() {
    }

    public Address(
            String id,
            String street,
            String number,
            String district,
            String city,
            String state,
            String zipCode,
            String country
    ) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "Endereço completo: Rua " + street +
                ", número=" + number +
                ", bairro=" + district +
                ", cidade=" + city +
                ", Estado=" + state +
                ", CEP=" + zipCode +
                ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
