package com.ok.dao.controllers.methods;

import com.ok.FindBookingCompanyNameAndDay;
import com.ok.dao.BookingRepository;
import com.ok.dao.CompanyRepository;
import com.ok.dao.UserRepository;
import com.ok.dao.requests.booking.CancelBookingHourRequest;
import com.ok.dao.requests.booking.CreateBookingRequest;
import com.ok.dao.requests.booking.CreateReservationRequest;
import com.ok.dao.requests.booking.DeleteBookingRequest;
import com.ok.dao.responses.booking.CancelBookingHourResponse;
import com.ok.dao.responses.booking.CreateBookingResponse;
import com.ok.dao.responses.booking.CreateReservationResponse;
import com.ok.dao.responses.booking.DeleteBookingResponse;
import com.ok.entities.Booking;
import com.ok.entities.BookingId;
import com.ok.entities.Company;
import com.ok.entities.User;

import java.util.List;

public class BookingMethods {

    public static void cancelHourUserBooking(UserRepository userRepo, CancelBookingHourRequest request, CancelBookingHourResponse response) {
        List<User> users = userRepo.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getBookings().isEmpty()) {
                int index = FindBookingCompanyNameAndDay.findIndex(users.get(i).getBookings(), request.getCompanyNameBranch().toUpperCase(),
                        request.getDay().toUpperCase(), request.getHour());
                if (index != -1) {
                    users.get(i).getBookings().remove(index);
                    userRepo.save(users.get(i));
                }

            }
        }
    }

    public static void createReservationAction(Booking booking, Company company, BookingRepository bookRepo, CompanyRepository compRepo,
                                               CreateReservationRequest request, CreateReservationResponse response) {
        booking.getReservations().put(request.getHour(), request.getCapacity());
        bookRepo.save(booking);
        compRepo.save(company);
        response.setSuccess(true);
        response.setMessage("Successful!");
    }

    public static void createBookingAction(BookingId bookingId, Booking booking, BookingRepository bookRepo, CreateBookingRequest request, CreateBookingResponse response) {
        bookingId.setCompanyNameBranchUpperCase(request.getCompanyNameBranch().toUpperCase());
        bookingId.setDayUpperCase(request.getDay().toUpperCase());
        booking.setBookingId(bookingId);
        bookRepo.save(booking);
        response.setSuccess(true);
        response.setMessage("Successful!");
    }

    public static void cancelUserBooking(UserRepository userRepo, DeleteBookingRequest request, DeleteBookingResponse response) {
        List<User> users = userRepo.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getBookings().isEmpty()) {
                int index = FindBookingCompanyNameAndDay.findIndexWithoutHour(users.get(i).getBookings(), request.getCompanyNameBranch().toUpperCase(),
                        request.getDay().toUpperCase());
                users.get(i).getBookings().remove(index);
                userRepo.save(users.get(i));
            }
        }
    }
}
