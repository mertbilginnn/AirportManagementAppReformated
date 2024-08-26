package com.example.airportmanagementapp.Controller;

import com.example.airportmanagementapp.Entity.*;
import com.example.airportmanagementapp.Entity.DataModels.passengerDataModel;
import com.example.airportmanagementapp.Entity.DataModels.selectRoutesDataModel;
import com.example.airportmanagementapp.Service.FlightsService;
import com.example.airportmanagementapp.Service.PassengerService;
import com.example.airportmanagementapp.Service.ReservationService;
import com.example.airportmanagementapp.Service.RouteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private RouteService routeService;
    private FlightsService flightsService;
    private PassengerService passengerService;
    private ReservationService reservationService;

    public HomeController(RouteService routeService, FlightsService flightsService, PassengerService passengerService, ReservationService reservationService) {
        this.routeService = routeService;
        this.flightsService = flightsService;
        this.passengerService = passengerService;
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String homePage() {

        return "index"; // index.html sayfasına yönlendirir
    }

    @GetMapping("/selectroutes")
    public String selectRoutes(Model model) {
        var Routes = routeService.findAll();

        List<String> FromRouteList = Routes.stream()
                .map(Route::getFromDestination)
                .distinct().toList();

        List<String> FromToList = Routes.stream()
                .map(Route::getToDestination)
                .distinct().toList();

        model.addAttribute("fromRouteList", FromRouteList);
        model.addAttribute("toRouteList", FromToList);
        model.addAttribute("selectRoutesDataModel", new selectRoutesDataModel());

        return "selectRoutes"; // selectRoutes.html sayfasına yönlendirir
    }

    @PostMapping("/selectroutes")
    public RedirectView selectRoutes(@ModelAttribute selectRoutesDataModel selectRoutesDataModel, HttpSession session) {
        //Bilet Processi
        String fromDestination = selectRoutesDataModel.getFromDestination();
        String toDestination = selectRoutesDataModel.getToDestination();



        session.setAttribute("adultPassengerCount", selectRoutesDataModel.getAdultPassengerCount());
        session.setAttribute("studentPassengerCount", selectRoutesDataModel.getStudentPassengerCount());
        session.setAttribute("disabledPassengerCount", selectRoutesDataModel.getDisabledPassengerCount());


        // Veritabanından seçilen rotaya ve tarihe göre uçuşları çek
        Route route = routeService.findFlightsByRoute(fromDestination, toDestination).getFirst();
        if (route == null) {
            //TO DO yanlış girdin
            // Hata döndür böyle bir uçuş yok
            return new RedirectView("/selectroutes");
        }

        String url = UriComponentsBuilder.fromPath("/selectflights")
                .queryParam("routedata", route.getRoute_id())
                .queryParam("flightdate",selectRoutesDataModel.getDepartureDate())
                .toUriString();

        return new RedirectView(url);
    }


    @GetMapping("/checkout")
    public String checkOutPnr(@ModelAttribute selectRoutesDataModel selectRoutesDataModel) {
        return "checkOutPnr"; // checkOutPnr.html sayfasına yönlendirir


    }


    @GetMapping("/selectflights")
    public String selectFlights(
            @RequestParam("routedata") Long routeId,
            @RequestParam("flightdate") LocalDate flightdate,
            Model model,
            HttpSession session) {
        // Kullanıcının seçtiği rota bilgilerini al

        Route _route = routeService.findById(routeId).isPresent() ? routeService.findById(routeId).get() : null;
        List<Flights> flights = flightsService.findAllByRoute(_route);
        flights = flights.stream().filter(f -> f.getLocalDepartureDate().isEqual(flightdate)).collect(Collectors.toList());

        int adultCount = (Integer) session.getAttribute("adultPassengerCount");
        int studentCount = (Integer) session.getAttribute("studentPassengerCount");
        int disabledCount = (Integer) session.getAttribute("disabledPassengerCount");
        int totalPassengerCount = adultCount + studentCount + disabledCount;


        // Uçuş bilgilerini model'e ekle
        model.addAttribute("totalPassengerCount", totalPassengerCount);
        model.addAttribute("route", _route);
        model.addAttribute("selectflights", flights);

        // Uçuş seçme sayfasına yönlendir
        return "selectFlights";
    }


    @GetMapping("/PassengerInfo")
    public String showPassengerInfoForm(
            Model model,
            HttpSession session,
            @RequestParam("flightId") Long flightId,
            @RequestParam("price") Double price) {

        int adultCount = (Integer) session.getAttribute("adultPassengerCount");
        int studentCount = (Integer) session.getAttribute("studentPassengerCount");
        int disabledCount = (Integer) session.getAttribute("disabledPassengerCount");

        session.setAttribute("flightsid",flightId);
        session.setAttribute("price",price);

        // Model'e kişi sayısını ve rota bilgisini ekle
        model.addAttribute("passenger", new Passenger());
        model.addAttribute("adultCount", adultCount);
        model.addAttribute("studentCount", studentCount);
        model.addAttribute("disabledCount", disabledCount);
        model.addAttribute("passengerDataModel", new passengerDataModel());
        return "PassengerInfo"; // HTML dosyasının adı
    }

    @PostMapping("/PassengerInfo")
    public String savePassengers(@ModelAttribute passengerDataModel passengerDataModel, HttpSession session) {
        List<Passenger> passengerList = passengerDataModel.getPassengerList();

        // Seçilen uçuş ID'sini session'dan alın
        Long flights_id = (Long) session.getAttribute("flightsid");
        Double price = (Double) session.getAttribute("price");

                // İlk yolcu bilgisi

                // Seçilen uçuş bilgisi
                Flights flight = flightsService.findById(flights_id).orElseThrow(() -> new RuntimeException("Flight not found"));

        // Kullanıcı bilgisi (örneğin oturumdaki kullanıcı)

        String commonPnrCode = reservationService.createPnrNumber();
        for (Passenger passenger : passengerList) {
            passenger = passengerService.addPassenger(passenger);
            Reservation reservation = new Reservation();
            reservation.setPassenger(passenger);
            reservation.setFlights(flight);
            reservation.setPnr_code(commonPnrCode);
            reservation.setTotalReservationPrice(price);
            this.reservationService.saveReservation(reservation);

        }


        // Rezervasyon ID'sini session'a ekleyin
        session.setAttribute("pnrCode", commonPnrCode);


        return "redirect:/payment"; // Onay sayfasına yönlendir
    }

    @GetMapping("/payment")
    public String paymentPage(Model model, HttpSession session) {
        // Session'dan rezervasyon ID'sini alın
        String pnrCode = (String) session.getAttribute("pnrCode");
        List<Reservation> reservations = reservationService.findReservationBypnr(pnrCode);
        Flights flight = reservations.getFirst().getFlights();
        Route route = flight.getRoute();

        Double price = (Double) session.getAttribute("price");

        // Model'e rezervasyon bilgisini ekleyin
        model.addAttribute("route", route);
        model.addAttribute("reservations", reservations);
        model.addAttribute("flight", flight);
        model.addAttribute("price", price);


        return "payment"; // payment.html sayfasına yönlendirir
    }

    @GetMapping("/searchPnr")
    public String searchPnr(@RequestParam(value = "pnr_code") String pnrCode, Model model) {
        // PNR koduna göre rezervasyonu ara
        List<Reservation> reservations = reservationService.findReservationBypnr(pnrCode);


        if (reservations.isEmpty()) {
            // Eğer rezervasyon bulunamazsa, hata mesajını ekleyip ana sayfaya yönlendirebiliriz
            model.addAttribute("error", "Rezervasyon bulunamadı.");
            return "index"; // Ana sayfa
        }


        // Rezervasyon ve ilgili uçuş bilgilerini modele ekle
        Flights flight = reservations.getFirst().getFlights();
        Route route = flight.getRoute();
        Passenger passenger = reservations.getFirst().getPassenger();


        model.addAttribute("reservations", reservations);
        model.addAttribute("flight", flight);
        model.addAttribute("route", route);
        model.addAttribute("passenger", passenger);
        model.addAttribute("pnrCode", pnrCode);
        model.addAttribute("price", reservations.getFirst().getTotalReservationPrice());

        return "checkOutPnr"; // checkOutPnr.html sayfasına yönlendirme
    }

    @PostMapping("/cancel-reservation")
    public String cancelReservation(@RequestParam("pnrCode") String pnrCode) {
        List<Reservation> reservations = reservationService.findReservationBypnr(pnrCode);

        if (!reservations.isEmpty()) {
            Reservation reservation = reservations.getFirst();
            reservationService.deleteReservation(reservation.getReservation_id());
        }

        return "index";
    }


}
