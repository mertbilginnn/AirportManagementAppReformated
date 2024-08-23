package com.example.airportmanagementapp.Service;


import com.example.airportmanagementapp.Entity.Passenger;
import com.example.airportmanagementapp.Repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // Yeni bir yolcu eklemek
    public Passenger addPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // Tüm yolcuları listelemek
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // ID ile yolcu bulmak
    public Optional<Passenger> getPassengerById(Long passengerId) {
        return passengerRepository.findById(passengerId);
    }

    // Bir yolcu güncellemek
    public Passenger updatePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // Bir yolcu silmek
    public void deletePassenger(Long passengerId) {
        passengerRepository.deleteById(passengerId);
    }
}