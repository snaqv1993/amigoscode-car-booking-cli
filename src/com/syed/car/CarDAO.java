package com.syed.car;

import com.syed.user.User;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDAO {

    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "XYZ-TSLA", new BigDecimal("100"), Brand.TESLA, true),
                new Car(UUID.fromString("0638d056-dc0b-427b-9a5f-2a249dfd57c6"), "AUD-ABC", new BigDecimal("150"), Brand.AUDI, false),
                new Car(UUID.fromString("8f95d8f7-84e4-4d09-bc5f-e35ae82e65c1"), "MER-AMG", new BigDecimal("160"), Brand.MERCEDES, false),
                new Car(UUID.fromString("8ba92353-61f3-4549-8b5a-a1e619bacf16"), "TOY-OTA", new BigDecimal("150"), Brand.TOYOTA, true)
        };
    }

    public Car[] getAllCars(){
        return cars;
    }
}
