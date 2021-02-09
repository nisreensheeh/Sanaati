package com.example.sanaati.UsersAuth.Class;

import java.io.Serializable;

public class Users implements Serializable {

    public String userid="",name="", email="", addressd="", phone="", job="", password="", location="", type = "",
            rate="", token = "", image = "", aid = "", comission ="";

    public Users(String userid, String name, String email, String addressd, String phone, String job, String password, String location, String type, String rate, String token, String image, String aid, String comission) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.addressd = addressd;
        this.phone = phone;
        this.job = job;
        this.password = password;
        this.location = location;
        this.type = type;
        this.rate = rate;
        this.token = token;
        this.image = image;
        this.aid = aid;
        this.comission = comission;
    }
}
