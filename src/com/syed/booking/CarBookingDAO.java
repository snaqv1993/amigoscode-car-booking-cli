package com.syed.booking;

import java.util.Arrays;

public class CarBookingDAO {

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

    public CarBooking[] getAllCarBookings(){
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
}
