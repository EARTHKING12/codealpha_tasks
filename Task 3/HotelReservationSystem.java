import java.util.*;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean isAvailable;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + category + "] - $" + price + " - " + (isAvailable ? "Available" : "Booked");
    }
}

class Booking {
    static int counter = 1000;
    int bookingId;
    String customerName;
    Room room;
    boolean isPaid;

    Booking(String customerName, Room room) {
        this.bookingId = counter++;
        this.customerName = customerName;
        this.room = room;
        this.isPaid = false;
    }

    void makePayment() {
        isPaid = true;
        System.out.println("Payment successful for booking ID: " + bookingId);
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + "\nCustomer: " + customerName +
                "\nRoom: " + room.roomNumber + " (" + room.category + ")\nPrice: $" + room.price +
                "\nPayment: " + (isPaid ? "Completed" : "Pending");
    }
}

class Hotel {
    List<Room> rooms = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();

    Hotel() {
        rooms.add(new Room(101, "Single", 100));
        rooms.add(new Room(102, "Single", 100));
        rooms.add(new Room(201, "Double", 150));
        rooms.add(new Room(202, "Double", 150));
        rooms.add(new Room(301, "Suite", 250));
    }

    List<Room> getAvailableRooms(String category) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.category.equalsIgnoreCase(category) && room.isAvailable) {
                available.add(room);
            }
        }
        return available;
    }

    Booking makeReservation(String customerName, String category) {
        List<Room> available = getAvailableRooms(category);
        if (available.isEmpty()) {
            System.out.println("No available rooms in category: " + category);
            return null;
        }
        Room room = available.get(0);
        room.isAvailable = false;
        Booking booking = new Booking(customerName, room);
        bookings.add(booking);
        System.out.println("Room " + room.roomNumber + " booked successfully. Booking ID: " + booking.bookingId);
        return booking;
    }

    Booking findBookingById(int id) {
        for (Booking b : bookings) {
            if (b.bookingId == id) return b;
        }
        return null;
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Search Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. View Booking");
            System.out.println("4. Make Payment");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter room category (Single/Double/Suite): ");
                    String category = sc.nextLine();
                    List<Room> available = hotel.getAvailableRooms(category);
                    if (available.isEmpty()) {
                        System.out.println("No rooms available.");
                    } else {
                        for (Room r : available) System.out.println(r);
                    }
                    break;

                case 2:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room category to book (Single/Double/Suite): ");
                    String cat = sc.nextLine();
                    hotel.makeReservation(name, cat);
                    break;

                case 3:
                    System.out.print("Enter booking ID: ");
                    int id = sc.nextInt();
                    Booking b = hotel.findBookingById(id);
                    if (b == null) System.out.println("Booking not found.");
                    else System.out.println(b);
                    break;

                case 4:
                    System.out.print("Enter booking ID to pay: ");
                    int payId = sc.nextInt();
                    Booking booking = hotel.findBookingById(payId);
                    if (booking == null) {
                        System.out.println("Invalid booking ID.");
                    } else if (booking.isPaid) {
                        System.out.println("Payment already completed.");
                    } else {
                        booking.makePayment();
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
