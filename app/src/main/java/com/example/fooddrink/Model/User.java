package com.example.fooddrink.Model;

public class User {
    private String Name;
    private String Password;
    private String address;

    public User() {
    }

    public User(String name, String password, String address) {
        this.Name = name;
        this.Password = password;
        this.address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
