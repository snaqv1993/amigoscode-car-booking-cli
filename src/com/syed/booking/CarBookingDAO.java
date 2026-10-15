package com.syed.booking;

import java.util.UUID;

public interface CarBookingDAO {
    CarBooking[] getBookings();
    CarBooking findBookingById(UUID bookingId);
    void saveBooking(CarBooking booking);
    void deleteBooking(UUID bookingId);
}
