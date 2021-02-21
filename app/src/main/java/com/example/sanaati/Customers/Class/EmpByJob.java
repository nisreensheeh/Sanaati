package com.example.sanaati.Customers.Class;

import com.example.sanaati.UsersAuth.Class.Users;

import java.util.ArrayList;

public class EmpByJob {
    public String job = "";
    public ArrayList<Users> info = null;

    public EmpByJob(String job) {
        this.job = job;
        info = new ArrayList<>();
    }

    public void Add(Users ch){ info.add(ch); }
}
