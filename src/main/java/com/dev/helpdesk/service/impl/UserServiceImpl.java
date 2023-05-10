package com.dev.helpdesk.service.impl;

import com.dev.helpdesk.model.User;
import com.dev.helpdesk.repository.UserRepository;
import com.dev.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User createOrUpdate(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return this.userRepository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        this.userRepository.deleteById(id);

    }

    @Override
    public Page<User> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count); //Paginação
        return this.userRepository.findAll(pages); //Retorna todos os usuarios
    }
}
