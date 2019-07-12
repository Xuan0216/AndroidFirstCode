package cn.edu.hznu.databasetest;

public class Contact {
    private String name;
    private String mobile;
    private boolean isChecked;
    public Contact(String name,String mobile){
        this.name=name;
        this.mobile=mobile;
    }
    public Contact(boolean checked,String name,String mobile){
        this.name=name;
        this.mobile=mobile;
        this.isChecked=checked;
    }
    public String getName(){
        return name;
    }
    public String getMobile(){
        return mobile;
    }
    public boolean getChecked(){
        return isChecked;
    }
    public void setChecked(boolean isChecked){
        this.isChecked=isChecked;
    }
}
