package com.example.sanaati.Employees.Class;

public class Talabat {
    public  String clientId="", clientName = "", empId = "", empName = "", requestDate ="", requestTime = "", empArrivedDateTime = "", empLeavedDateTime = "", clientLocation = "",
            empLocation = "", totalAmount = "", companyComission = "", customerEmpRate = "", status = "";

    public Talabat(String clientId, String clientName, String empId, String empName, String requestDate, String requestTime, String empArrivedDateTime, String empLeavedDateTime, String clientLocation, String empLocation, String totalAmount, String companyComission, String customerEmpRate, String status) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.empId = empId;
        this.empName = empName;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
        this.empArrivedDateTime = empArrivedDateTime;
        this.empLeavedDateTime = empLeavedDateTime;
        this.clientLocation = clientLocation;
        this.empLocation = empLocation;
        this.totalAmount = totalAmount;
        this.companyComission = companyComission;
        this.customerEmpRate = customerEmpRate;
        this.status = status;
    }
}
