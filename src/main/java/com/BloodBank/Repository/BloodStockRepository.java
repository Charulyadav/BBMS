package com.BloodBank.Repository;

import com.BloodBank.Model.BloodStockModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodStockRepository extends MongoRepository<BloodStockModel,String> {

    List<BloodStockModel> findAll();

    Optional<BloodStockModel> findByBloodGroup(String bloodGroup);

    void updateStock(String bloodGroup, int quantity);

    void updateTime(String bloodGroup, LocalDateTime time);

    List<String> findAllBloodGroups();
}