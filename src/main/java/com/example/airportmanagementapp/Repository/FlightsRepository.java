package com.example.airportmanagementapp.Repository;

import com.example.airportmanagementapp.Entity.Flights;
import com.example.airportmanagementapp.Entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightsRepository extends JpaRepository<Flights, Long> {
    List<Flights> findAllByRoute(Route route);
}
