package com.BloodBank.Repository;

import com.BloodBank.Model.BloodRequestModel;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BloodRequestRepositoryImpl implements BloodRequestRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int countByBloodGroupAndStatus(String bloodGroup, String status) {
        Assert.notNull(bloodGroup, "Blood group must not be null");
        Assert.notNull(status, "Status must not be null");

        Query query = new Query(Criteria.where("bloodGroup").is(bloodGroup).and("status").is(status));
        return (int) mongoTemplate.count(query, BloodRequestModel.class);
    }

    @Override
    public List<BloodRequestModel> findByAgentAndBloodGroup(String agent, String bloodGroup) {
        Assert.notNull(agent, "Agent must not be null");
        Assert.notNull(bloodGroup, "Blood group must not be null");

        Query query = new Query(Criteria.where("agent").is(agent).and("bloodGroup").is(bloodGroup));
        return mongoTemplate.find(query, BloodRequestModel.class);
    }

    @Override
    public List<BloodRequestModel> findByAgent(String agentUsername) {
        Assert.notNull(agentUsername, "Agent username must not be null");

        Query query = new Query(Criteria.where("agent").is(agentUsername));
        return mongoTemplate.find(query, BloodRequestModel.class);
    }

    @Override
    public Optional<BloodRequestModel> findTopByUsernameOrderByUpdatedAtDesc(String username) {
        Assert.notNull(username, "Username must not be null");

        Query query = new Query(Criteria.where("username").is(username)).with(Sort.by(Sort.Direction.DESC, "updatedAt")).limit(1);
        return Optional.ofNullable(mongoTemplate.findOne(query, BloodRequestModel.class));
    }

    @Override
    public void addRemark(String requestId, String remark) {
        System.out.println(requestId);
        System.out.println(remark);
        Assert.notNull(requestId, "Request ID must not be null");
        Assert.notNull(remark, "Remark must not be null");

        Query query = new Query(Criteria.where("_id").is(requestId));
        Update update = new Update().set("remark", remark);
        mongoTemplate.updateFirst(query, update, BloodRequestModel.class);
    }

    @Override
    public void updateStatus(String requestId, String status) {
        Assert.notNull(requestId, "Request ID must not be null");
        Assert.notNull(status, "Status must not be null");

        Query query = new Query(Criteria.where("_id").is(requestId));
        Update update = new Update().set("status", status);
        mongoTemplate.updateFirst(query, update, BloodRequestModel.class);
    }

    @Override
    public void updateTime(String requestId, LocalDateTime updatedAt) {
        Assert.notNull(requestId, "Request ID must not be null");
        Assert.notNull(updatedAt, "Updated time must not be null");

        Query query = new Query(Criteria.where("_id").is(requestId));
        Update update = new Update().set("updatedAt", updatedAt);
        mongoTemplate.updateFirst(query, update, BloodRequestModel.class);
    }

    @Override
    public List<BloodRequestModel> findByUsernameAndTypeAndStatus(String username, String type, String status) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(type, "Type must not be null");
        Assert.notNull(status, "Status must not be null");

        Query query = new Query(Criteria.where("username").is(username).and("type").is(type).and("status").is(status));
        return mongoTemplate.find(query, BloodRequestModel.class);
    }

    @Override
    public <S extends BloodRequestModel> S save(S entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public BloodRequestModel findLastRequestByUsername(String username) {
        Assert.notNull(username, "Username must not be null");

        Query query = new Query(Criteria.where("username").is(username)).with(Sort.by(Sort.Direction.DESC, "updatedAt")).limit(1);
        return mongoTemplate.findOne(query, BloodRequestModel.class);
    }


    @Override
    public <S extends BloodRequestModel> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<BloodRequestModel> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<BloodRequestModel> findAll() {
        return mongoTemplate.findAll(BloodRequestModel.class);
    }

    @Override
    public Iterable<BloodRequestModel> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(BloodRequestModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends BloodRequestModel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BloodRequestModel> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<BloodRequestModel> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BloodRequestModel> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends BloodRequestModel> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends BloodRequestModel> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends BloodRequestModel> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends BloodRequestModel> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends BloodRequestModel> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BloodRequestModel> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends BloodRequestModel> boolean exists(Example<S> example) {
        return false;
    }
}
