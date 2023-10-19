package com.example.hp.myapplication.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String email;
    private String Uid;
    public User(){

    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public User(String name, String password, String phone, String email, String uid) {
        Name = name;
        Password = password;
        Phone = phone;
        this.email = email;
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name)
    {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password){
        Password = password;
    }
}
