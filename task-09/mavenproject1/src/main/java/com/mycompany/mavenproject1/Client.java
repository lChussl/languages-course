package com.mycompany.mavenproject1;

public class Client {
    private int id;
    private String firstName;
    private String address;

    public Client(int id, String firstName, String address) {
        this.id = id;
        this.firstName = firstName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", firstName=" + firstName + ", address=" + address + '}';
    }
}
