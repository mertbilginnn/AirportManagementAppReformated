package com.example.airportmanagementapp.Entity;


import jakarta.persistence.*;
import lombok.Data;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Data
@Entity
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik olarak arttırılan bir ID
    private Long flights_id;

    private int economySeatNumber;
    private int businessSeatNumber;
    private Date deparatureDate;

    @ManyToOne
    @JoinColumn(name="route_id")
    private Route route;

    public String getFormattedDepartureDate() {
        if (deparatureDate == null) {
            return "Tarih bilgisi yok";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy 'Saat:' HH:mm", new Locale("tr", "TR"));
        return sdf.format(deparatureDate);
    }

    public LocalDate getLocalDepartureDate() {
        return this.getDeparatureDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
