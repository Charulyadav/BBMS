package com.BloodBank.dto;

import java.sql.Date;

public class UserSignUpDTO {
    private String username;
    private String password;
    private String name;
    private Long phone;
    private Date dob;
    private String address;
    private String bloodGroup;
    private String petName;

    private String hobby;
    private String dish;
    private String profileUrl;

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getHobby() {
        return hobby;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
            this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
            this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
