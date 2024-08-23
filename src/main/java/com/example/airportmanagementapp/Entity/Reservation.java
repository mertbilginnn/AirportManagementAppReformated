package com.example.airportmanagementapp.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reservation {

    @Id // Primary key alanını belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak arttırılan bir ID
    private Long reservation_id;

    @ManyToOne
    @JoinColumn(name="flights_id")
    private Flights flights;


    @ManyToOne
    @JoinColumn(name="passenger_id")
    private Passenger passenger;

    private String pnr_code;




}
