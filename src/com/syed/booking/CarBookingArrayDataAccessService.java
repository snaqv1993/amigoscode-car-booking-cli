package com.syed.booking;

import java.util.Arrays;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDAO {

    private CarBooking[] carBookings = new CarBooking[10];

    public void saveBooking(CarBooking carBooking){
        int index = 0;
        while(index < carBookings.length){
            if(carBookings[index] == null){
                break;
            }
            index++;
        }
        if(index >= carBookings.length){
            carBookings = Arrays.copyOf(carBookings, carBookings.length*2);
        }
        carBookings[index] = carBooking;
    }

    public void deleteBooking(UUID bookingId){
        for (CarBooking carBooking : carBookings) {
            if (carBooking.getUuid().equals(bookingId)) {
                carBooking.setBookingStatus(BookingStatus.CANCELLED);
                return;
            }
        }
    }

    public CarBooking[] getBookings (){
        CarBooking [] tempCarBookingsHolder = new CarBooking[carBookings.length];
        int countBookings = 0;
        for(int i = 0; i < carBookings.length; i++){
            if(carBookings[i] != null) {
                tempCarBookingsHolder[countBookings] = carBookings[i];
                countBookings++;
            }
        }
        return Arrays.copyOf(tempCarBookingsHolder,countBookings);
    }

    public CarBooking findBookingById(UUID bookingId){
        CarBooking[] carBookingsTemp = getBookings();
        for (CarBooking carBooking : carBookingsTemp) {
            if (carBooking.getUuid().equals(bookingId))
                return carBooking;
        }
        return null;
    }


}
