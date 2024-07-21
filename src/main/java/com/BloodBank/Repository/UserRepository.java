package com.BloodBank.Repository;

import com.BloodBank.Model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel,String> {

    UserModel findByUsername(String username);

    void updateUserBlockedStatusByUsername(boolean blockedStatus, String username);

    void updateWrongAttemptCount(String username, int newAttemptCount);

    void updateSecurityQuestionAndAnswer(String username, String petName, String hobby, String dish,String profileUrl);

    <S extends UserModel> S save(S entity);
    void updatePasswordByUsername(String password, String username);

    void updateFirstTimeLoginByUsername(boolean firstTimeLogin, String username);

    void updateCoin(int coin, String username);

    Optional<UserModel> findById(String id);

    List<UserModel> findAll();

    void deleteById(String id);
    boolean existsById(String id);

    void updateCoinRequestCount(String username, int newCoinRequestCount);

}
