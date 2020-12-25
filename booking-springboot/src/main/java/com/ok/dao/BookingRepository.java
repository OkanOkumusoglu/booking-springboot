package com.ok.dao;

import com.ok.entities.Booking;
import com.ok.entities.BookingId;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface BookingRepository extends MongoRepository<Booking, BookingId> {


}
