package com.ok.entities;

import java.util.HashMap;

public class Reservation {

    private String day;
    private HashMap<String, Long> reservations = new HashMap<>();

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public HashMap<String, Long> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<String, Long> reservations) {
        this.reservations = reservations;
    }
}
