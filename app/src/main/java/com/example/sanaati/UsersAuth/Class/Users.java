package com.example.sanaati.UsersAuth.Class;

import java.io.Serializable;

public class Users implements Serializable {

    public String UserId="",Name="", Email="", Addressd="", Phone="", Job="", Password="", Location="", Type = "", rate="", token = "", image = "", aid = "", comission ="";

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddressd() {
        return Addressd;
    }

    public String getPhone() {
        return Phone;
    }

    public String getJob() {
        return Job;
    }

    public String getPassword() {
        return Password;
    }

    public String getLocation() {
        return Location;
    }

    public String getType() { return Type; }

    public String getUserId() { return UserId; }

    public String getRate() {
        return rate;
    }

    public String getToken() {
        return token;
    }

    public String getImage() {
        return image;
    }

    public String getAid() {
        return aid;
    }

    public String getComission() {
        return comission;
    }

    public Users(String UserId, String name, String email, String addressd, String phone, String job, String password, String location, String Type, String rate, String token, String image, String aid, String comission) {
        this.UserId = UserId;
        Name = name;
        Email = email;
        Addressd = addressd;
        Phone = phone;
        Job = job;
        Password = password;
        Location = location;
        this.Type = Type;
        this.rate = rate;
        this.token = token;
        this.image = image;
        this.aid = aid;
        this.comission = comission;
    }
}
