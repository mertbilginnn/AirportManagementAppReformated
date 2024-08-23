package com.example.airportmanagementapp.Entity.DataModels;

import lombok.Data;

import java.time.LocalDate;

@Data
public class selectRoutesDataModel {
    String fromDestination;
    String toDestination;
    String flightType;
    LocalDate departureDate;
    LocalDate returnDate;
    int adultPassengerCount;
    int studentPassengerCount;
    int disabledPassengerCount;
}
