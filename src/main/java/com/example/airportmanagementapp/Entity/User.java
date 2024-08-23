package com.example.airportmanagementapp.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    @Id // Primary key alanını belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak arttırılan bir ID
    private Long id;
    private String password;
    private String username; // Kullanıcı adı
    private int auth_num;

}
