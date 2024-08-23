package com.example.airportmanagementapp.Service;

import com.example.airportmanagementapp.Entity.Route;
import com.example.airportmanagementapp.Repository.FlightsRepository;
import com.example.airportmanagementapp.Entity.Flights;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightsService {

    private final FlightsRepository FlightsRepository;

    public FlightsService(FlightsRepository flightsRepository) {
        this.FlightsRepository = flightsRepository;
    }

    // Tüm uçuşları listele
    public List<Flights> findAll() {
        return FlightsRepository.findAll();
    }

    // Uçuş ID'si ile uçuş bul
    public Optional<Flights> findById(Long flights_id) {
        return FlightsRepository.findById(flights_id);
    }

    // Yeni uçuş ekle
    public Flights saveFlights(Flights flights) {
        return FlightsRepository.save(flights);
    }

    // Uçuşu güncelle
    public Flights updateFlights(Flights flights) {
        return FlightsRepository.save(flights);
    }

    // Uçuşu sil
    public void deleteFlights(Long flights_id) {
        FlightsRepository.deleteById(flights_id);
    }

    public List<Flights> findAllByRoute(Route route) {
        return FlightsRepository.findAllByRoute(route);
    }



}
