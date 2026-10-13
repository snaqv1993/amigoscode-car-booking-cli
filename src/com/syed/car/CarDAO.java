package com.syed.car;

import java.util.UUID;

public interface CarDAO {
    Car[] getAllCars();
    Car findCarById(UUID carId);
}
