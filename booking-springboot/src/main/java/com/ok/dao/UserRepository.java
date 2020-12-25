package com.ok.dao;

import com.ok.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{'firstName': {$regex : '^?0$', $options: 'i'}}")
    User findByNameIgnoreCase(String firstName);

    @Query(value = "{'lastName': {$regex : '^?0$', $options: 'i'}}")
    User findByLastNameIgnoreCase(String lastName);

    @Query("{ 'userName' : { $regex: '?0', $options: 'i'} }")
    List<User> findUserByRegexpName(String userName);

    @Query("{ 'firstName' : { $regex: '?0', $options: 'i'} }")
    List<User> findUserByRegexpFirstName(String firstName);

    @Query("{'lastName':{ $regex: '?0', $options: 'i'} }")
    List<User> findUserByRegexpLastName(String lastName);

}
