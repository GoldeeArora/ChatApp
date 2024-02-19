package com.goldee.websocket.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.goldee.websocket.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByEmailIsNot(String email);
}
