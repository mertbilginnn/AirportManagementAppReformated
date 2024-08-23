package com.example.airportmanagementapp.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak arttırılan bir ID
    private Long passengerId;

    private String passengerName;
    private String passengerSurname;
    private String passengertckno;
    private String passengerEmail;
    private LocalDate passengerBirthdate;
    private String passengertelno;
    private String passportno;
    private String passengergender;



}
