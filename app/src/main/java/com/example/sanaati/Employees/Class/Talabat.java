package com.example.sanaati.Employees.Class;

public class Talabat {
    public  String talabId="",clientId="", clientName = "", empId = "", empName = "",empService = "",clientaddress = "",clientlocation = "", requestDate ="", requestTime = "", empArrivedDateTime = "", empLeavedDateTime = "", clientLocation = "",
            empLocation = "", totalAmount = "", companyComission = "", customerEmpRate = "", status = "";

    public Talabat(String talabId,String clientId, String clientName,String empService, String empId, String empName,String clientaddress,String clientlocation, String requestDate, String requestTime, String empArrivedDateTime, String empLeavedDateTime, String clientLocation, String empLocation, String totalAmount, String companyComission, String customerEmpRate, String status) {
        this.talabId = talabId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.empId = empId;
        this.empName = empName;
        this.empService = empService;
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
        this.clientaddress = clientaddress;
        this.clientlocation = clientlocation;
    }
}
