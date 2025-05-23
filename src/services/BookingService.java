package services;

import models.*;
import exceptions.*;
import java.util.*;
import java.time.LocalDate;

public class BookingService {
    private List<Room> rooms = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();
    private Map<String, RoomBooking> bookings = new HashMap<>();

    public BookingService() {
        rooms.add(new Room(101, "Deluxe", 3000, true));
        rooms.add(new Room(102, "Standard", 2000, true));
        rooms.add(new Room(103, "Suite", 5000, true));
    }

    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    public List<Room> searchAvailableRooms(String type, double maxPrice) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getType().equalsIgnoreCase(type) && room.getPrice() <= maxPrice) {
                available.add(room);
            }
        }
        return available;
    }

    public void bookRoom(String userId, int roomId, LocalDate checkIn, LocalDate checkOut)
            throws RoomUnavailableException, InvalidRoomSelectionException {

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomId() == roomId) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) throw new InvalidRoomSelectionException("Room ID not found.");
        if (!selectedRoom.isAvailable()) throw new RoomUnavailableException("Room is not available.");

        selectedRoom.setAvailable(false);
        RoomBooking booking = new RoomBooking(users.get(userId), selectedRoom, checkIn, checkOut);
        bookings.put(booking.getBookingId(), booking);

        System.out.println("\n" + booking);
        System.out.println("Confirmation email sent to " + users.get(userId).getEmail());
    }

    public double cancelBooking(String bookingId, LocalDate cancelDate) throws InvalidRoomSelectionException {
        RoomBooking booking = bookings.get(bookingId);
        if (booking == null || booking.isCancelled()) {
            throw new InvalidRoomSelectionException("Invalid or already cancelled booking ID.");
        }
        double refund = booking.cancelBooking(cancelDate);
        return refund;
    }
}
