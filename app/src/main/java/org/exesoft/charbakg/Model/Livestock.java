package org.exesoft.charbakg.Model;


import java.util.Date;
import java.util.UUID;

public class Livestock {
    private String uid = UUID.randomUUID().toString();
    private String serial;
    private String sex;
    private int age;
    private long added;
    private boolean child = false;
    private int childAmount;
    private String ageUnit;
    private int ageMonth;
    private int ageYear;
    private boolean slaughter = false;

    public Livestock(){
        added = new Date().getTime();
    }

    public void setSerial(String serial){ this.serial = serial;}
    public String getSerial(){return serial;}

    public void setSex(String sex){this.sex = sex;}
    public String getSex(){return sex;}

    public void setAge(int age){this.age = age;}
    public int getAge(){return  age;}

    public void setAgeUnit(String ageUnit){
        this.ageUnit = ageUnit;
    }

    public String getAgeUnit(){
        return ageUnit;
    }

    public String getUid(){
        return  uid;
    }

    public long getAdded(){return added;}

    public int getAgeMonth(){return  ageMonth;}
    public void setAgeMonth(int ageMonth){this.ageMonth = ageMonth;}

    public int getAgeYear(){ return  ageYear;}
    public void setAgeYear(int ageYear){this.ageYear = ageYear;}
}
