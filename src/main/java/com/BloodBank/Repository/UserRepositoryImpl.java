package com.BloodBank.Repository;

import com.BloodBank.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserModel findByUsername(String username) {
        Assert.notNull(username, "Username must not be null");

        // Perform the query using MongoTemplate
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, UserModel.class);
    }

    @Override
    public void updateUserBlockedStatusByUsername(boolean blockedStatus, String username) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("blockedStatus", blockedStatus);
        mongoTemplate.updateFirst(query, update, UserModel.class);
    }

    @Override
    public void updateWrongAttemptCount(String username, int newAttemptCount) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("wrongAttemptCount", newAttemptCount);
        mongoTemplate.updateFirst(query, update, UserModel.class);
    }

    @Override
    public void updateCoinRequestCount(String username, int newCoinRequestCount) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("coinRequestCount", newCoinRequestCount);
        mongoTemplate.updateFirst(query, update, UserModel.class);
    }

    @Override
    public void updateSecurityQuestionAndAnswer(String username, String petName, String hobby, String dish,String profileUrl) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update()
                .set("petName",petName )
                .set("hobby", hobby)
                .set("dish", dish)
                .set("profileUrl",profileUrl);
        mongoTemplate.updateMulti(query, update, UserModel.class);
    }

    @Override
    public void updatePasswordByUsername(String password, String username) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("password", password);
        mongoTemplate.updateFirst(query, update, UserModel.class);
    }

    @Override
    public void updateFirstTimeLoginByUsername(boolean firstTimeLogin, String username) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("firstTimeLogin", firstTimeLogin);
        mongoTemplate.updateFirst(query, update, UserModel.class);
    }

    @Override
    public void updateCoin(int coin, String username) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("coin", coin);
        mongoTemplate.updateFirst(query, update, UserModel.class);

    }

    @Override
    public <S extends UserModel> S save(S entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public <S extends UserModel> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<UserModel> findById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        UserModel user = mongoTemplate.findOne(query, UserModel.class);
        return Optional.ofNullable(user);
    }


    @Override
    public List<UserModel> findAll() {
        return mongoTemplate.findAll(UserModel.class);
    }

    @Override
    public Iterable<UserModel> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, UserModel.class);
    }

    @Override
    public boolean existsById(String id) {
        // Check if a document with the provided ID exists in the MongoDB collection
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.exists(query, UserModel.class);
    }

    @Override
    public void delete(UserModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserModel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<UserModel> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserModel> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends UserModel> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends UserModel> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserModel> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends UserModel> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends UserModel> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserModel> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserModel> boolean exists(Example<S> example) {
        return false;
    }
}
