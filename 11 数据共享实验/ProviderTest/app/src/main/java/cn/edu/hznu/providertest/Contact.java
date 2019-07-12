package cn.edu.hznu.providertest;

import java.io.Serializable;

public class Contact implements Serializable{
    private String name;
    private String mobile;
    private boolean isChecked;
    public Contact(String name,String mobile){
        this.name=name;
        this.mobile=mobile;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
         this.name=name;
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile){
        this.mobile=mobile;
    }
}