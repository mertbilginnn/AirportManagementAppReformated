package com.example.airportmanagementapp.Repository;

import com.example.airportmanagementapp.Entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByFromDestinationAndToDestination(String fromDestination, String toDestination);
}
