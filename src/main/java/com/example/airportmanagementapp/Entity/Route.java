package com.example.airportmanagementapp.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak arttırılan bir ID
    private Long route_id;

    private String fromDestination;
    private String toDestination;
    private Double ecoSeatPrice;
    private Double bussSeatPrice;


}
