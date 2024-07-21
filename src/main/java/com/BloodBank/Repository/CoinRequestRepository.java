package com.BloodBank.Repository;

import com.BloodBank.Model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRequestRepository extends MongoRepository<UserModel,String> {


}
