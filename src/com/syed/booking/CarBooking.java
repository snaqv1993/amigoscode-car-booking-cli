package com.syed.booking;

import com.syed.car.Car;
import com.syed.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CarBooking {

    private UUID uuid;
    private User User;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private BookingStatus bookingStatus;
    private LocalDateTime bookedAt;

    public CarBooking(UUID uuid, User user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price, BookingStatus bookingStatus, LocalDateTime bookedAt) {
        this.uuid = uuid;
        User = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.bookingStatus = bookingStatus;
        this.bookedAt = bookedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CarBooking that)) return false;
        return Objects.equals(uuid, that.uuid) && Objects.equals(User, that.User) && Objects.equals(car, that.car) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(price, that.price) && bookingStatus == that.bookingStatus && Objects.equals(bookedAt, that.bookedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, User, car, startDate, endDate, price, bookingStatus, bookedAt);
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "uuid=" + uuid +
                ", User=" + User +
                ", car=" + car +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", bookingStatus=" + bookingStatus +
                ", bookedAt=" + bookedAt +
                '}';
    }
}
