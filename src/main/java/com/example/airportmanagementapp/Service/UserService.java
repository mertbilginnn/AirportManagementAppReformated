package com.example.airportmanagementapp.Service;


import com.example.airportmanagementapp.Entity.User;
import com.example.airportmanagementapp.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Tüm kullanıcıları listele
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // ID ile kullanıcı bul
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Yeni kullanıcı ekle
    public void saveUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }

    // Kullanıcıyı güncelle
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Kullanıcıyı sil
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }



}
