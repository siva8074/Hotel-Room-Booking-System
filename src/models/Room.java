package models;

public class Room {
    private int roomId;
    private String type;
    private double price;
    private boolean available;

    public Room(int roomId, String type, double price, boolean available) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public int getRoomId() { return roomId; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Room ID: " + roomId + " | Type: " + type + " | Price: ₹" + price + " | Available: " + available;
    }
}