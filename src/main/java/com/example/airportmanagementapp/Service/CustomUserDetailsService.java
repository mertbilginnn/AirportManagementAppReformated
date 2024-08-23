package com.example.airportmanagementapp.Service;

import com.example.airportmanagementapp.Entity.User;
import com.example.airportmanagementapp.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı.");
        }
        User currentUser = user.get();

        // auth_num değerine göre rol belirleme
        String role = currentUser.getAuth_num() == 0 ? "ADMIN" : "USER";

        return org.springframework.security.core.userdetails.User.withUsername(currentUser.getUsername())
                .password(currentUser.getPassword())
                .authorities(role) // Kullanıcıya dinamik olarak rol atıyoruz
                .build();
    }
}
