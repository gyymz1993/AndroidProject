package com.databing.bean;

public class User {
    public String name;
    public int age;
    public String pic;
    public boolean isChecked;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return this.name;
    }


}