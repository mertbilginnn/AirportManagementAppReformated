package com.example.airportmanagementapp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/showMyLoginPage","/register").permitAll() // Herkesin erişebileceği sayfalar
                                .requestMatchers("/admin/manageUsers").hasAuthority("ADMIN") // Sadece ADMIN rolündeki kullanıcılar için
                                .anyRequest().authenticated() // Diğer tüm sayfalar için kimlik doğrulaması gereksin
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .successHandler(successHandler())  // Başarılı giriş işlemi
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/");
    }
}
