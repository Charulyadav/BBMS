package com.BloodBank.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.time.LocalDateTime;

@Document("BloodRequestModel")
public class BloodRequestModel {
    @Id
    private String requestId; // Primary key
    
    private UserModel userId;

    private String bloodGroup;
    private  String type;
    private int quantity;
    private String agent;
    private Date dob;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private String status;
    private String username;
    private String remark;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public UserModel getUserId() {
        return userId;
    }

    public void setUserId(UserModel userId) {
        this.userId = userId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null && !status.trim().isEmpty()) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        if (agent != null && !agent.trim().isEmpty()) {
            this.agent = agent;
        } else {
            throw new IllegalArgumentException("Agent cannot be null or empty");
        }
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}