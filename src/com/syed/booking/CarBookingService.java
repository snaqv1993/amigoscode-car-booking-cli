package com.syed.booking;

import com.syed.car.Car;
import com.syed.car.CarService;
import com.syed.user.User;
import com.syed.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingService {

    private CarBookingDAO carBookingDAO = new CarBookingDAO();
    private CarService carService = new CarService();

    public CarBooking bookCar(UUID userId,
                        UUID carId,
                        LocalDate startDate,
                        LocalDate endDate){
        //1. Look up the user by userId
        UserService userService = new UserService();
        User user = userService.getUserById(userId);
        if(user == null){
            throw new IllegalArgumentException("User not found - Invalid ID");
        }

        //2. Look up the car by carId
        CarService carService = new CarService();
        Car car = carService.getCarById(carId);
        if(car == null){
            throw new IllegalArgumentException("Car not found - Invalid ID");
        }

        //3. Validate the dates:
        if(startDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if(endDate.isBefore(startDate)){
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        //4. Get all current bookings
        CarBooking[] bookings = carBookingDAO.getAllCarBookings();

        //5. Check whether an active booking already holds this car
        for(CarBooking booking : bookings){
            if(booking != null && booking.getCar().equals(car) && booking.getBookingStatus().equals(BookingStatus.ACTIVE)){
                throw new IllegalArgumentException("Reject: the car is not available");
            }
        }

        //6. Count the days with ChronoUnit.DAYS.between(startDate, endDate)
        long numberOfDaysBooked = ChronoUnit.DAYS.between(startDate, endDate);

        //7. Calculate the price: car.getRentalPricePerDay() x numberOfDays
        BigDecimal price = car.getRentalPricePerDay().multiply(new BigDecimal(numberOfDaysBooked));

        //8. Build a CarBooking with a UUID, user, car, dates, price, BookingStatus.ACTIVE and bookedAt = LocalDateTime.now()
        CarBooking newCarBooking = new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                startDate,
                endDate,
                price,
                BookingStatus.ACTIVE,
                LocalDateTime.now()
        );

        //9. Save the booking through the DAO
        carBookingDAO.saveBooking(newCarBooking);

        //10. Return the saved booking
        return newCarBooking;
    }

    public CarBooking[] getAllCarBookings(){
        return carBookingDAO.getAllCarBookings();
    }

    public CarBooking[] getBookingsByUser(UUID userId){
        CarBooking[] allBookings = getAllCarBookings();
        CarBooking[] tempCarBookingHolder = new CarBooking[allBookings.length];
        int countTempCarBookingHolder = 0;
        for(CarBooking carBooking : allBookings){
            if(carBooking.getUser().getUuid().equals(userId)){
                tempCarBookingHolder[countTempCarBookingHolder] = carBooking;
                countTempCarBookingHolder++;
            }
        }

        return Arrays.copyOf(tempCarBookingHolder,countTempCarBookingHolder);
    }

    public Car[] getAvailableCars(){
        Car[] allCars = carService.getAllCars();
        CarBooking[] allBookings = carBookingDAO.getAllCarBookings();
        Car[] tempCarsAvailableHolder = new Car[allCars.length];
        int count = 0;
        for(int i = 0; i < allCars.length; i++){
            Car car = allCars[i];
            boolean isBooked = false;
            for(int j =0; j < allBookings.length; j++){
                CarBooking carBooking = allBookings[j];
                //System.out.println(carBooking);
                if(carBooking.getCar().equals(car) &&
                   carBooking.getBookingStatus().equals(BookingStatus.ACTIVE)){
                    isBooked = true;
                    break;
                }
            }
            if(!isBooked){
                tempCarsAvailableHolder[count] = car;
                count++;
            }
        }

        return Arrays.copyOf(tempCarsAvailableHolder, count);
    }

    public Car[] getAvailableElectricCars(){
        Car[] allCars = carService.getAllCars();
        CarBooking[] allBookings = carBookingDAO.getAllCarBookings();
        Car[] tempCarsAvailableHolder = new Car[allCars.length];
        int count = 0;
        for(int i = 0; i < allCars.length; i++){
            Car car = allCars[i];
            boolean toExcludeFromFinalArray = false;
            if(!car.isElectric())continue;
            for(int j =0; j < allBookings.length; j++){
                CarBooking carBooking = allBookings[j];
                //System.out.println(carBooking);
                if(carBooking.getCar().equals(car) &&
                        carBooking.getBookingStatus().equals(BookingStatus.ACTIVE)
                ){
                    toExcludeFromFinalArray = true;
                    break;
                }
            }
            if(!toExcludeFromFinalArray){
                tempCarsAvailableHolder[count] = car;
                count++;
            }
        }

        return Arrays.copyOf(tempCarsAvailableHolder, count);
    }

    public void deleteBooking(UUID bookingID){
        CarBooking[] allBookings = carBookingDAO.getAllCarBookings();
        for(int i = 0; i < allBookings.length; i++){
            if(allBookings[i].getUuid().equals(bookingID)){
                allBookings[i].setBookingStatus(BookingStatus.CANCELLED);
            }
        }
    }
}
