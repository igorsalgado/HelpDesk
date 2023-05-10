package com.dev.helpdesk.service;


import com.dev.helpdesk.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface UserService {

    User findByEmail(String email);

    User createOrUpdate(User user);

    User findById(String id);

    void delete(String id);

    Page<User> findAll(int page, int count);

}
