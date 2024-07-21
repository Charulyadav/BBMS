package com.BloodBank.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.time.LocalDateTime;

@Document(collection="UserModel")
public class UserModel {
    @Id
    private String id; // Primary key

    private String username;
    private String password;
    private String name;
    private Long phone;
    private Date dob;
    private LocalDateTime created_date_time;
    private  String createdBy;
    private String modifyBy;
    private  String role;
    private String address;
    private LocalDateTime updated_date_time;
    private String bloodGroup;
    private boolean blockedStatus=false;
    private boolean firstTimeLogin=true;
    private int coins;
    private Long commission;

    private int wrongAttemptCount;

    private String petName;

    private String hobby;
    private String dish;
    private String profileUrl;

    private int coinRequestCount;

    public int getCoinRequestCount() {
        return coinRequestCount;
    }

    public void setCoinRequestCount(int coinRequestCount) {
        this.coinRequestCount = coinRequestCount;
    }

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

    public int getWrongAttemptCount() {
        return wrongAttemptCount;
    }

    public void setWrongAttemptCount(int wrongAttemptCount) {
        this.wrongAttemptCount = wrongAttemptCount;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        if (bloodGroup != null && !bloodGroup.trim().isEmpty()) {
            this.bloodGroup = bloodGroup;
        } else {
            throw new IllegalArgumentException("Blood group cannot be null or empty");
        }
    }

    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
    }

    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    public void setFirstTimeLogin(boolean firstTimeLogin) {
        this.firstTimeLogin = firstTimeLogin;
    }

    public boolean getBlockedStatus() {
        return blockedStatus;
    }

    public void setBlockedStatus(boolean blockedStatus) {
        this.blockedStatus = blockedStatus;
    }

    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        if (password != null && !password.trim().isEmpty()) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    public String getName() {
        return name;
    }

    public void setPhone(Long phone) {
        if (phone != null && phone > 0) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Phone number cannot be null or negative");
        }
    }

    public Long getPhone() {
        return phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        if (dob != null) {
            this.dob = dob;
        } else {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
    }

    public LocalDateTime getCreated_date_time() {
        return created_date_time;
    }

    public void setCreated_date_time(LocalDateTime created_date_time) {
        this.created_date_time = created_date_time;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        if (createdBy != null && !createdBy.trim().isEmpty()) {
            this.createdBy = createdBy;
        } else {
            throw new IllegalArgumentException("Created by cannot be null or empty");
        }
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        if (modifyBy != null && !modifyBy.trim().isEmpty()) {
            this.modifyBy = modifyBy;
        } else {
            throw new IllegalArgumentException("Modify by cannot be null or empty");
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role != null && !role.trim().isEmpty()) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
    }

    public LocalDateTime getUpdated_date_time() {
        return updated_date_time;
    }

    public void setUpdated_date_time(LocalDateTime updated_date_time) {
        this.updated_date_time = updated_date_time;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
