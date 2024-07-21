package com.BloodBank.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document("BloodStockModel")
public class BloodStockModel {
    private String bloodGroup;
    private LocalDateTime lastUpdate;
    private int donateCoinsPerUnit;
    private int receiveCoinsPerUnit;
    private int availableUnits;

    public int getDonateCoinsPerUnit() {
        return donateCoinsPerUnit;
    }

    public void setDonateCoinsPerUnit(int donateCoinsPerUnit) {
        this.donateCoinsPerUnit = donateCoinsPerUnit;
    }

    public int getReceiveCoinsPerUnit() {
        return receiveCoinsPerUnit;
    }

    public void setReceiveCoinsPerUnit(int receiveCoinsPerUnit) {
        this.receiveCoinsPerUnit = receiveCoinsPerUnit;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }


    public int getAvailableUnits() {
        return availableUnits;
    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
