package com.example.demo.service;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) throw new BadRequestException("Email already exists");
        if (user.getRole() == null) user.setRole("USER");
        if (user.getPassword() != null) user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override public User findByEmail(String email) { return userRepository.findByEmail(email).orElse(null); }
    @Override public User findById(Long id) { return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User missing")); }
    @Override public User create(User user) { return register(user); }
    @Override public User getById(Long id) { return findById(id); }
    @Override public List<User> getAll() { return userRepository.findAll(); }
    @Override public User update(Long id, User user) { user.setId(id); return userRepository.save(user); }
    @Override public void delete(Long id) { userRepository.deleteById(id); }
}