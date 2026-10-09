package com.syed.car;

import java.util.UUID;

public class CarService {

    private CarDAO carDAO = new CarDAO();

    public Car[] getAllCars(){
        return carDAO.getAllCars();
    }

    public Car getCarById(UUID carId){
        for (Car car : carDAO.getAllCars()){
            if(car.getUuid().equals(carId))
                return car;
        }
        return null;
    }
}
