package com.example.airportmanagementapp.Entity.DataModels;

import com.example.airportmanagementapp.Entity.Passenger;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class passengerDataModel {
    private String passengerNameArrString;
    private String passengerSurnameArrString;
    private String passengerDateArrString;
    private String passengerGenderArrString;
    private String passengerPassportArrString;
    private String passengerNationalityArrString;
    private String passengerTelNoArrString;
    private String passengerEmailArrString;

    public List<Passenger> getPassengerList() {
        int passengerListSize = passengerNameArrString.split("&").length;

        String[] passengerNameArr = passengerNameArrString.split("&");
        String[] passengerSurnameArr = passengerSurnameArrString.split("&");
        String[] passengerDateArr = passengerDateArrString.split("&");
        String[] passengerGenderArr = passengerGenderArrString.split("&");
        String[] passengerPassportArr = passengerPassportArrString.split("&");
        String[] passengerNationalityArr = passengerNationalityArrString.split("&");
        String[] passengerTelNoArr = passengerTelNoArrString.split("&");
        String[] passengerEmailArr = passengerEmailArrString.split("&");



        List<Passenger> passengerList = new ArrayList<>();
        for (int i = 0; i < passengerListSize; i++) {
            Passenger passenger = new Passenger();
            passenger.setPassengerName(passengerNameArr[i]);
            passenger.setPassengerSurname(passengerSurnameArr[i]);
            passenger.setPassengergender(passengerGenderArr[i]);
            passenger.setPassportno(passengerPassportArr[i]);
            passenger.setPassengertckno(passengerNationalityArr[i]);
            passenger.setPassengertelno(passengerTelNoArr[i]);
            passenger.setPassengerEmail(passengerEmailArr[i]);
            passenger.setPassengerBirthdate(LocalDate.parse(passengerDateArr[i]));


            passengerList.add(passenger);
        }

        return passengerList;
    }


}
