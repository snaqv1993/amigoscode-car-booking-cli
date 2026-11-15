package com.syed.booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDAO {

    //private CarBooking[] carBookings = new CarBooking[10];
    private List<CarBooking> carBookings = new ArrayList<>();

    public void saveBooking(CarBooking carBooking){
//        int index = 0;
//        while(index < carBookings.size()){
//            if(carBookings.get(index) == null){
//                break;
//            }
//            index++;
//        }
//        if(index >= carBookings.length){
//            carBookings = Arrays.copyOf(carBookings, carBookings.length*2);
//        }
//        carBookings[index] = carBooking;
        carBookings.add(carBooking);
    }

    public void deleteBooking(UUID bookingId){
        for (CarBooking carBooking : carBookings) {
            if (carBooking.getUuid().equals(bookingId)) {
                carBooking.setBookingStatus(BookingStatus.CANCELLED);
                return;
            }
        }
    }

    public List<CarBooking> getBookings (){
        //CarBooking [] tempCarBookingsHolder = new CarBooking[carBookings.length];
//        List<CarBooking> tempCarBookingsHolder = new ArrayList<>();
//        int countBookings = 0;
//        for(int i = 0; i < carBookings.size(); i++){
//            if(carBookings.get(i) != null) {
//                tempCarBookingsHolder.add(carBookings.get(i));
//                //tempCarBookingsHolder[countBookings] = carBookings[i];
//                //countBookings++;
//            }
//        }
//        //return Arrays.copyOf(tempCarBookingsHolder,countBookings);
//        return tempCarBookingsHolder;
        return carBookings;
    }

    public CarBooking findBookingById(UUID bookingId){
        List<CarBooking> carBookingsTemp = getBookings();
        for (CarBooking carBooking : carBookingsTemp) {
            if (carBooking.getUuid().equals(bookingId))
                return carBooking;
        }
        return null;
    }


}
