package com.example.fooddrink.Model;

public class Booking {

    private String Datetime;
    private String MenuId;
    private String address;
    private String amount;
    private String employee;


    private String isFinish;
    private String numberPhone;
    private String price;

    public Booking(String datetime, String menuId, String address, String amount, String employee, String isFinish, String numberPhone, String price, String userName) {
        this.Datetime = datetime;
        this.MenuId = menuId;
        this.address = address;
        this.amount = amount;
        this.employee = employee;
        this.isFinish = isFinish;
        this.numberPhone = numberPhone;
        this.price = price;
        this.userName = userName;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

}
