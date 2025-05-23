package main;

import models.*;
import services.BookingService;
import exceptions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HotelBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingService service = new BookingService();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        String userId = "U001";
        User user = new User(userId, name, email);
        service.registerUser(user);

        System.out.print("Enter room type (Deluxe/Standard/Suite): ");
        String roomType = scanner.nextLine();
        System.out.print("Enter max price: ");
        double maxPrice = scanner.nextDouble();

        List<Room> availableRooms = service.searchAvailableRooms(roomType, maxPrice);
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for your search.");
            return;
        }

        System.out.println("Available Rooms:");
        for (Room room : availableRooms) {
            System.out.println(room);
        }

        System.out.print("Enter Room ID to book: ");
        int roomId = scanner.nextInt();

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String inDate = scanner.next();
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String outDate = scanner.next();

        LocalDate checkIn = LocalDate.parse(inDate);
        LocalDate checkOut = LocalDate.parse(outDate);

        try {
            service.bookRoom(userId, roomId, checkIn, checkOut);
        } catch (RoomUnavailableException | InvalidRoomSelectionException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        scanner.nextLine();
        System.out.print("\nDo you want to cancel a booking? (yes/no): ");
        String cancel = scanner.nextLine();

        if (cancel.equalsIgnoreCase("yes")) {
            System.out.print("Enter Booking ID: ");
            String bookingId = scanner.nextLine();

            System.out.print("Enter today's date (YYYY-MM-DD): ");
            String today = scanner.nextLine();
            LocalDate cancelDate = LocalDate.parse(today);

            try {
                double refund = service.cancelBooking(bookingId, cancelDate);
                System.out.println("Booking cancelled successfully.");
                System.out.println("Refund Amount: ₹" + refund);
            } catch (InvalidRoomSelectionException e) {
                System.out.println("Cancel failed: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
