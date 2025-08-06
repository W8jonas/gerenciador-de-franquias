package domain.model;

import java.io.Serializable;
import java.util.UUID;

public class Customer implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String cpf;

    private String city;
    private String state;
    private String neighborhood;
    private String street;
    private String number;

    private String idUserParent; // Email do usuário responsável pelo cadastro
    private String franchiseId;

    public Customer(String name, String phone, String cpf,
                    String city, String state, String neighborhood,
                    String street, String number, String idUserParent) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.city = city;
        this.state = state;
        this.neighborhood = neighborhood;
        this.street = street;
        this.number = number;
        this.idUserParent = idUserParent;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdUserParent() {
        return idUserParent;
    }

    public void setIdUserParent(String idUserParent) {
        this.idUserParent = idUserParent;
    }

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", idUserParent='" + idUserParent + '\'' +
                '}';
    }
}
