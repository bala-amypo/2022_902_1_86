package com.example.demo.service;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
    User findById(Long id);
    // Added to fix UserController errors
    User create(User user);
    User getById(Long id);
    List<User> getAll();
    User update(Long id, User user);
    void delete(Long id);
}