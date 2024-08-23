package com.example.airportmanagementapp.Service;


import com.example.airportmanagementapp.Entity.Reservation;
import com.example.airportmanagementapp.Repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Tüm rezervasyonları listele
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    // Rezervasyon ID ile rezervasyon bul
    public Optional<Reservation> findById(Long reservation_id) {
        return reservationRepository.findById(reservation_id);
    }

    // Yeni rezervasyon oluştur
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Rezervasyonu güncelle
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Rezervasyonu sil
    public void deleteReservation(Long reservation_id) {
        reservationRepository.deleteById(reservation_id);
    }

    public String createPnrNumber(){
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<Reservation> findReservationBypnr(String pnr) {
        return reservationRepository.findAll().stream()
                .filter(reservation -> Objects.equals(reservation.getPnr_code(), pnr))
                .toList();
    }



}
