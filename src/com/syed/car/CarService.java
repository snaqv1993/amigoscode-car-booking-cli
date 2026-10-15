package com.syed.car;

import java.util.UUID;

public class CarService {

    private final CarDAO carDAO;

    public CarService(CarDAO carDAO){
        this.carDAO = carDAO;
    }

    public Car[] getAllCars(){
        return carDAO.getAllCars();
    }

    public Car getCarById(UUID carId){
        return carDAO.findCarById(carId);
    }
}
