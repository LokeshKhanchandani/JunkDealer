package com.example.junkdealer;

import java.io.Serializable;

public class JunkDealer implements Serializable {

    public String username,phone,address,email,city;
    public boolean picked;
    JunkDealer(){}

    JunkDealer(String username, String phone, String address, String email, String city, Boolean picked){
        this.username=username;
        this.phone=phone;
        this.address=address;
        this.email=email;
        this.picked=picked;
        this.city=city;
        if(city==null)
            this.city="Lucknow";
    }
}
