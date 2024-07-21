package com.BloodBank.Repository;

import com.BloodBank.Model.BloodRequestModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodRequestRepository extends MongoRepository<BloodRequestModel,String> {

    int countByBloodGroupAndStatus(String bloodGroup, String status);

    List<BloodRequestModel> findByAgentAndBloodGroup(String agent, String bloodGroup);

    List<BloodRequestModel> findByAgent(String agentUsername);

    Optional<BloodRequestModel> findTopByUsernameOrderByUpdatedAtDesc(String username);

    void addRemark(String requestId, String remark);

    void updateStatus(String requestId, String status);

    void updateTime(String requestId, LocalDateTime updatedAt);

    List<BloodRequestModel> findByUsernameAndTypeAndStatus(String username, String type, String status);

    List<BloodRequestModel> findAll();

    BloodRequestModel findLastRequestByUsername(String username);
}

