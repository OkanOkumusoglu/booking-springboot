package com.ok.dao.controllers.methods;

import com.ok.FindBookingCompanyNameAndDay;
import com.ok.dao.BookingRepository;
import com.ok.dao.CompanyRepository;
import com.ok.dao.UserRepository;
import com.ok.dao.requests.user.CreateUserRequest;
import com.ok.dao.requests.user.UserBookingRequest;
import com.ok.dao.responses.user.CreateUserResponse;
import com.ok.dao.responses.user.UserBookingResponse;
import com.ok.entities.Booking;
import com.ok.entities.BookingId;
import com.ok.entities.Company;
import com.ok.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

public class UserMethods {


    public static void userBookingSave(Booking booking, Booking userBooking, Company company, User user, CompanyRepository compRepo, UserRepository userRepo,
                                       BookingRepository bookRepo, Long capacityAmount, UserBookingRequest request, UserBookingResponse response) {

        int companyBookingIndex = FindBookingCompanyNameAndDay.findIndex((ArrayList<Booking>) company.getBookings(), request.getCompanyNameBranch().toUpperCase(),
                request.getDay().toUpperCase(), request.getHour());
        if (!company.getBookings().isEmpty()) {
            company.getBookings().get(companyBookingIndex).getReservations().replace(request.getHour(), capacityAmount - request.getCapacity());
        }
        compRepo.save(company);
        BookingId userBookingId = new BookingId(request.getCompanyNameBranch().toUpperCase(), request.getDay().toUpperCase());
        booking.getReservations().replace(request.getHour(), capacityAmount - request.getCapacity());
        bookRepo.save(booking);
        userBooking.getReservations().put(request.getHour(), request.getCapacity());
        userBooking.setBookingId(userBookingId);
        user.getBookings().add(userBooking);
        userRepo.save(user);
        response.setSuccess(true);
        response.setMessage("Successful!");
    }

    public static void userCreateMethod(User user, UserRepository userRepo, CreateUserRequest request, CreateUserResponse response) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12

        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        String encodedPassword = encoder.encode(request.getPassword());
        user.setUserPassword(encodedPassword);

        user.setUserMail(request.getMail());
        user.setUserPhone(request.getPhoneNumber());
        userRepo.save(user);
        response.setSuccess(true);
        response.setMessage("Successful!");
    }

    public static boolean canUserBook(User user, UserBookingRequest request) {
        if (!user.getBookings().isEmpty()) {
            Booking checkUserBooking = FindBookingCompanyNameAndDay.findWithHour(user.getBookings(), request.getCompanyNameBranch().toUpperCase(),
                    request.getDay().toUpperCase(), request.getHour());
            if ((checkUserBooking != null)) {
                return false;
            }

        }
        return true;

    }
}
