package com.example.airportmanagementapp.Service;


import com.example.airportmanagementapp.Entity.Route;
import com.example.airportmanagementapp.Repository.RouteRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    // Tüm rotaları listele
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    // Route ID ile route bul
    public Optional<Route> findById(Long route_id) {
        return routeRepository.findById(route_id);
    }

    // Yeni route ekle
    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    // Route'yi güncelle
    public Route updateRoute(Route route) {
        return routeRepository.save(route);
    }

    // Route'yi sil
    public void deleteRoute(Long route_id) {
        routeRepository.deleteById(route_id);
    }

    public List<Route> findFlightsByRoute(String fromDestination, String toDestination) {
        return routeRepository.findByFromDestinationAndToDestination(fromDestination, toDestination);
    }


}
