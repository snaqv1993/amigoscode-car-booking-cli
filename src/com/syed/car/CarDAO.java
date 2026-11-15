package com.syed.car;

import java.util.List;
import java.util.UUID;

public interface CarDAO {
    List<Car> getAllCars();
    Car findCarById(UUID carId);
}
