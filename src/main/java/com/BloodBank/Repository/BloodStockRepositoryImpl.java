package com.BloodBank.Repository;

import com.BloodBank.Model.BloodStockModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BloodStockRepositoryImpl implements BloodStockRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<BloodStockModel> findByBloodGroup(String bloodGroup) {
        Query query = new Query(Criteria.where("bloodGroup").is(bloodGroup));
        BloodStockModel bloodStockModel = mongoTemplate.findOne(query, BloodStockModel.class);
        return Optional.ofNullable(bloodStockModel);
    }

    @Override
    public void updateStock(String bloodGroup, int quantity) {
        BloodStockModel existingStock = mongoTemplate.findOne(Query.query(Criteria.where("bloodGroup").is(bloodGroup)), BloodStockModel.class);
        if (existingStock != null) {
            System.out.println(existingStock.getAvailableUnits());
            int newQuantity = existingStock.getAvailableUnits() + quantity;
            System.out.println(newQuantity);
            mongoTemplate.updateFirst(Query.query(Criteria.where("bloodGroup").is(bloodGroup)),
                    Update.update("availableUnits", newQuantity), BloodStockModel.class);
        }
    }

    @Override
    public void updateTime(String bloodGroup, LocalDateTime time) {
        Query query = new Query(Criteria.where("bloodGroup").is(bloodGroup));
        Update update = new Update().set("time", time);
        mongoTemplate.updateFirst(query, update, BloodStockModel.class);
    }

    @Override
    public List<String> findAllBloodGroups() {
        return mongoTemplate.query(BloodStockModel.class)
                .distinct("bloodGroup")
                .as(String.class)
                .all();
    }

    @Override
    public <S extends BloodStockModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends BloodStockModel> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<BloodStockModel> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<BloodStockModel> findAll() {
        return mongoTemplate.findAll(BloodStockModel.class);
    }

    @Override
    public Page<BloodStockModel> findAll(Pageable pageable) {
        Query query = new Query().with(pageable);
        // Execute the query and return a page of BloodStockModel
        return PageableExecutionUtils.getPage(mongoTemplate.find(query, BloodStockModel.class),
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), BloodStockModel.class));
    }

    @Override
    public Iterable<BloodStockModel> findAllById(Iterable<String> strings) {
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
    public void delete(BloodStockModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends BloodStockModel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BloodStockModel> findAll(Sort sort) {
        return List.of();
    }


    @Override
    public <S extends BloodStockModel> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends BloodStockModel> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends BloodStockModel> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends BloodStockModel> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends BloodStockModel> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends BloodStockModel> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BloodStockModel> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends BloodStockModel> boolean exists(Example<S> example) {
        return false;
    }
}
