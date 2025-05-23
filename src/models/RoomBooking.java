package models;

import java.time.LocalDate;
import java.util.UUID;

public class RoomBooking {
    private String bookingId;
    private User user;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private boolean cancelled = false;

    public RoomBooking(User user, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.bookingId = UUID.randomUUID().toString().substring(0, 8);
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public double calculateTotalPrice() {
        long days = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        return days * room.getPrice();
    }

    public String getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Room getRoom() { return room; }
    public LocalDate getCheckIn() { return checkIn; }
    public boolean isCancelled() { return cancelled; }

    public double cancelBooking(LocalDate cancelDate) {
        cancelled = true;
        room.setAvailable(true);

        double total = calculateTotalPrice();
        long daysBeforeCheckIn = java.time.temporal.ChronoUnit.DAYS.between(cancelDate, checkIn);

        if (cancelDate.isAfter(checkIn)) return 0;
        else if (daysBeforeCheckIn >= 1) return total;
        else return total * 0.5;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + "\n" +
               "User: " + user.getName() + "\n" +
               "Room ID: " + room.getRoomId() + "\n" +
               "Stay Dates: " + checkIn + " - " + checkOut + "\n" +
               "Total Price: ₹" + calculateTotalPrice();
    }
}
