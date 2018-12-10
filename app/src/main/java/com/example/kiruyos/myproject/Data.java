package com.example.kiruyos.myproject;

import java.util.Date;

public class Data {

    private String Name;
    //private String Birthday;
    private int Photo;
    private Date Birthday;
    private String Phone;
    private String PhotoPath;


    public Data() {

    }

    public Data(String name, Date birthday, int photo, String phone) {
        Name = name;
        Birthday = birthday;
        Photo = photo;
        Phone = phone;
    }

    public Data(String name, Date birthday, int photo) {
        Name = name;
        Birthday = birthday;
        Photo = photo;
    }

    public Data(String name, Date birthday, String photoPath, String phone) {
        Name = name;
        Birthday = birthday;
        PhotoPath = photoPath;
        Phone = phone;
    }

    //Getter

    public String getName() {
        return Name;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public int getPhoto() {
        return Photo;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPhotoPath(){ return PhotoPath;}

    //Setter

    public void setName(String name) {
        Name = name;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPhotoPath(String photoPath) { PhotoPath = photoPath;}
}
