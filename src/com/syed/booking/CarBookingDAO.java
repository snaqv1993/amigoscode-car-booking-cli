package com.syed.booking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDAO {
    List<CarBooking> getBookings();
    CarBooking findBookingById(UUID bookingId);
    void saveBooking(CarBooking booking);
    void deleteBooking(UUID bookingId);
}
