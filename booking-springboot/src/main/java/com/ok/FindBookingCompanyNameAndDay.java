package com.ok;

import com.ok.entities.Booking;

import java.util.ArrayList;

public class FindBookingCompanyNameAndDay {

    public static Booking findWithHour(ArrayList<Booking> bookings, String companyNameBranchUpperCase, String dayUpperCase, String hour) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().getCompanyNameBranchUpperCase().equals(companyNameBranchUpperCase) &&
                    bookings.get(i).getBookingId().getDayUpperCase().equals(dayUpperCase.toUpperCase()) && bookings.get(i).getReservations().containsKey(hour)) {
                return bookings.get(i);
            }
        }
        return null;

    }

    public static Booking findWithoutHour(ArrayList<Booking> bookings, String companyNameBranchUpperCase, String dayUpperCase) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().getCompanyNameBranchUpperCase().equals(companyNameBranchUpperCase) &&
                    bookings.get(i).getBookingId().getDayUpperCase().equals(dayUpperCase.toUpperCase())) {
                return bookings.get(i);
            }
        }
        return null;

    }

    public static int findIndex(ArrayList<Booking> bookings, String companyNameBranchUpperCase, String dayUpperCase, String hour) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().getCompanyNameBranchUpperCase().equals(companyNameBranchUpperCase) &&
                    bookings.get(i).getBookingId().getDayUpperCase().equals(dayUpperCase.toUpperCase()) && bookings.get(i).getReservations().containsKey(hour)) {
                return i;
            }
        }
        return -1;

    }

    public static int findIndexWithoutHour(ArrayList<Booking> bookings, String companyNameBranchUpperCase, String dayUpperCase) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId().getCompanyNameBranchUpperCase().equals(companyNameBranchUpperCase) &&
                    bookings.get(i).getBookingId().getDayUpperCase().equals(dayUpperCase.toUpperCase())) {
                return i;
            }
        }
        return -1;

    }


}
