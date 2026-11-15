package com.syed.booking;

import com.syed.car.Car;
import com.syed.car.CarService;
import com.syed.user.User;
import com.syed.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CarBookingService {

    private CarBookingDAO carBookingDAO;
    private CarService carService;
    private UserService userService;

    public CarBookingService(CarBookingDAO carBookingDAO, CarService carService, UserService userService){
        this.carBookingDAO = carBookingDAO;
        this.carService = carService;
        this.userService = userService;
    }

    public CarBooking bookCar(UUID userId,
                        UUID carId,
                        LocalDate startDate,
                        LocalDate endDate){

        //1. Look up the user by userId
        User user = userService.getUserById(userId);
        if(user == null){
            throw new IllegalArgumentException("User not found - Invalid ID");
        }

        //2. Look up the car by carId
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
        //CarBooking[] bookings = carBookingDAO.getBookings();
        List<CarBooking> bookings = carBookingDAO.getBookings();

        //5. Check whether an active booking already holds this car
        for(CarBooking booking : bookings){
            if(booking != null && booking.getCar().equals(car) && booking.getBookingStatus().equals(BookingStatus.ACTIVE)){
                throw new IllegalArgumentException("Reject: the car is not available");
            }
        }

        //6. Count the days with ChronoUnit.DAYS.between(startDate, endDate)
        long numberOfDaysBooked = ChronoUnit.DAYS.between(startDate, endDate);

        // if start and end date are equal we will charge a one day booking
        if(numberOfDaysBooked == 0){
            numberOfDaysBooked = 1;
        }

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

    public List<CarBooking> getAllCarBookings(){
        return carBookingDAO.getBookings();
    }

    public List<CarBooking> getBookingsByUser(UUID userId){
        List<CarBooking> allBookings = getAllCarBookings();
        List<CarBooking> tempCarBookingHolder = new ArrayList<>();
        int countTempCarBookingHolder = 0;
        for(CarBooking carBooking : allBookings){
            if(carBooking.getUser().getUuid().equals(userId)){
                //tempCarBookingHolder[countTempCarBookingHolder] = carBooking;
                //countTempCarBookingHolder++;
                tempCarBookingHolder.add(carBooking);
            }
        }

        //return Arrays.copyOf(tempCarBookingHolder,countTempCarBookingHolder);
        return tempCarBookingHolder;
    }

    public List<Car> getAvailableCars(){
        //Car[] allCars = carService.getAllCars();
        List<Car> allCars = carService.getAllCars();
        List<CarBooking> allBookings = carBookingDAO.getBookings();
        //Car[] tempCarsAvailableHolder = new Car[allCars.size()];
        List<Car> tempCarsAvailableHolder = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < allCars.size(); i++){
            //Car car = allCars[i];
            Car car = allCars.get(i);
            boolean isBooked = false;
            if(allBookings != null){
                for(int j =0; j < allBookings.size(); j++){
                    CarBooking carBooking = allBookings.get(j);
                    //System.out.println(carBooking);
                    if(carBooking.getCar().equals(car) &&
                            carBooking.getBookingStatus().equals(BookingStatus.ACTIVE)){
                        isBooked = true;
                        break;
                    }
                }
            }

            if(!isBooked){
//                tempCarsAvailableHolder[count] = car;
//                count++;
                tempCarsAvailableHolder.add(car);
            }
        }

//        return Arrays.copyOf(tempCarsAvailableHolder, count);
        return tempCarsAvailableHolder;
    }

    public List<Car> getAvailableElectricCars(){
        //Car[] allCars = carService.getAllCars();
        List<Car> allCars = carService.getAllCars();
        List<CarBooking> allBookings = carBookingDAO.getBookings();
        //Car[] tempCarsAvailableHolder = new Car[allCars.size()];
        List<Car> tempCarsAvailableHolder = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < allCars.size(); i++){
            //Car car = allCars[i];
            Car car = allCars.get(i);
            boolean toExcludeFromFinalArray = false;
            if(!car.isElectric())continue;
            for(int j =0; j < allBookings.size(); j++){
                CarBooking carBooking = allBookings.get(j);
                //System.out.println(carBooking);
                if(carBooking.getCar().equals(car) &&
                        carBooking.getBookingStatus().equals(BookingStatus.ACTIVE)
                ){
                    toExcludeFromFinalArray = true;
                    break;
                }
            }
            if(!toExcludeFromFinalArray){
//                tempCarsAvailableHolder[count] = car;
//                count++;
                tempCarsAvailableHolder.add(car);
            }
        }

//        return Arrays.copyOf(tempCarsAvailableHolder, count);
        return tempCarsAvailableHolder;
    }

    public void deleteBooking(UUID bookingID){
        carBookingDAO.deleteBooking(bookingID);
    }

    public CarBooking findBookingById(UUID bookingId){
        return carBookingDAO.findBookingById(bookingId);
    }
}
