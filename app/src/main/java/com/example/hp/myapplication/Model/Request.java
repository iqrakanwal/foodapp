package com.example.hp.myapplication.Model;

import java.util.List;

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private List<Order> foods;
    //list
    private StatusType order_status;

    public Request() {

    }


    public StatusType getOrder_status() {
        return order_status;
    }

    public void setOrder_status(StatusType order_status) {
        this.order_status = order_status;
    }

    public Request(String phone, String name, String address, String total, List<Order> foods, StatusType order_status) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.order_status = order_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
