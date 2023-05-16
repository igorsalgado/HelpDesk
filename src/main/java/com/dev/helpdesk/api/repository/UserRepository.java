package com.dev.helpdesk.api.repository;


import com.dev.helpdesk.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    Optional<User> findById (String id);
}
