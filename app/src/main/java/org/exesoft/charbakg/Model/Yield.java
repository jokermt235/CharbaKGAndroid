package org.exesoft.charbakg.Model;

import java.util.Date;
import java.util.UUID;

public class Yield {
    private String uid = UUID.randomUUID().toString();
    private String unit = "литр";
    private double amount;
    private Date added = new Date();
    private String owner;

    public String getUid(){
        return  uid;
    }

    public String getUnit(){
        return  unit;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount(){
        return  amount;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public  String getOwner(){
        return owner;
    }

    public Date getAdded(){
        return  added;
    }
}
