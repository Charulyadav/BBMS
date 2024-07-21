package com.BloodBank.Service;

import com.BloodBank.Model.BloodStockModel;
import com.BloodBank.Repository.BloodStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import java.util.NoSuchElementException;

@Service
public class BloodStockService {
    @Autowired
    public BloodStockRepository bloodStockRepository;

    public List<BloodStockModel> getBloodStock() {
        return bloodStockRepository.findAll();
    }

    public void updateStock(String bloodGroup, int quantity) {
//        List<BloodStockModel> list=bloodStockRepository.findAll();
//        for(BloodStockModel entity:list)
//        {
//            System.out.println("====="+entity);
//            if(entity.getBloodGroup().equals(bloodGroup))
//            {
//                System.out.println("====="+quantity);
//                quantity=quantity+entity.getAvailableUnits();
//                System.out.println("====="+quantity);
//            }
//        }
        System.out.println(quantity);
        bloodStockRepository.updateTime(bloodGroup, LocalDateTime.now());
        bloodStockRepository.updateStock(bloodGroup, quantity);
    }

    public int getCoinPerUnitByBloodGroup(String bloodGroup) {
        BloodStockModel stock = bloodStockRepository.findByBloodGroup(bloodGroup)
                .orElseThrow(() -> new NoSuchElementException("Blood group not found"));
        return stock.getDonateCoinsPerUnit();
    }
    public int getUnitsByBloodGroup(String bloodGroup) {
        BloodStockModel stock = bloodStockRepository.findByBloodGroup(bloodGroup)
                .orElseThrow(() -> new NoSuchElementException("Blood group not found"));
        return stock.getAvailableUnits();
    }
    public List<String> getBloodGroups() {
        return bloodStockRepository.findAllBloodGroups();
    }
}


