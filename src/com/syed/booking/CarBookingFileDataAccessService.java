package com.syed.booking;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDAO{

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath){
        this.filePath = filePath;
    }

    public void saveBooking(CarBooking carBooking){
        //CarBooking[] arr = getBookings();
        List<CarBooking> arr = getBookings();
//        arr = Arrays.copyOf(arr, arr.length +1);
//        arr[arr.length - 1] = carBooking;
        arr.add(carBooking);
        writeToFile(arr);
    }

    public void deleteBooking(UUID bookingId){
        //CarBooking[] carBookings = getBookings();
        List<CarBooking> carBookings = getBookings();
        for (CarBooking carBooking : carBookings) {
            if (carBooking.getUuid().equals(bookingId)) {
                carBooking.setBookingStatus(BookingStatus.CANCELLED);
                break;
            }
        }
        writeToFile(carBookings);
    }

    public List<CarBooking> getBookings (){
        try{
            return loadFromFile();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        //return new CarBooking[0];
        return Collections.emptyList();
    }

    public CarBooking findBookingById(UUID bookingId){
        //CarBooking[] carBookings = getBookings();
        List<CarBooking> carBookings = getBookings();
        for (CarBooking carBooking : carBookings) {
            if (carBooking.getUuid().equals(bookingId))
                return carBooking;
        }
        return null;
    }

    private List<CarBooking> loadFromFile() throws IOException {
        Path path = Path.of(filePath);
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        if (Files.size(path) == 0) {
            return Collections.emptyList();
        }
        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(filePath))) {
            return (List<CarBooking>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(
                    "Couldn't load bookings from file", e);
        }
    }

    private void writeToFile(List<CarBooking> carBookings){
        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(filePath))) {

            output.writeObject(carBookings);

        } catch (IOException  e) {
            throw new RuntimeException(
                    "Could not save booking to file", e);
        }
    }
}
