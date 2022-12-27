/*
Write a Java program to manage a parking area. The parking area is 100 m. For each
coming vehicle, the system should allocate a suitable space for this car according to its
length and the available spaces. When a car leaves the parking, your system should mark
its space as free. If two contiguous spaces are free you should merge them.
There are 4 types of vehicles: Truck with default length 7m, bus with default length 10m,
car with default length 5m, and motorcycle with default length 2m.
Your program should provide a menu to enable the user to manage the parking. The
menu should include: adding a vehicle, leaving a vehicle, show parking status (to show
free space and occupied spaces).
When a vehicle comes the user should enter its type and id. Using car id the user can
release it from the parking area. The user should also enter number of hours spent in the
parking when release. Using calcMoney function in each different type of vehicle the
program should calculates the money to be payed when release.
 */

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

class Vehicle {
    private String type;
    private int id;
    private int length;

    public Vehicle(int id, String type, int length) {
        this.id = id;
        this.length = length;
        this.type = type;
    }

    int getId() {
        return id;
    }

    int getLength() {
        return length;
    }

    public String toString() {
        return "Vehicle Type = " + type.toUpperCase() + ", Vehicle ID = " + id + ", Vehicle Length = " + length;
    }
}

class Car extends Vehicle {

    private static final int CAR_LENGTH = 5;
    private static final String CAR_TYPE = "car";

    public Car(int id) {
        super(id, CAR_TYPE, CAR_LENGTH);
    }
}

class Truck extends Vehicle {

    private static final int TRUCK_LENGTH = 7;
    private static final String TRUCK_TYPE = "truck";

    public Truck(int id) {
        super(id, TRUCK_TYPE, TRUCK_LENGTH);
    }
}

class Bus extends Vehicle {

    private static final int BUS_LENGTH = 10;
    private static final String BUS_TYPE = "bus";

    public Bus(int id) {
        super(id, BUS_TYPE, BUS_LENGTH);
    }
}

class Motorcycle extends Vehicle {

    private static final int MOTORCYCLE_LENGTH = 2;
    private static final String MOTORCYCLE_TYPE = "motorcycle";

    public Motorcycle(int id) {
        super(id, MOTORCYCLE_TYPE, MOTORCYCLE_LENGTH);
    }
}
/*
ParkingService
it should has a method to add a vehicle to the parking area
it should has a method to remove a vehicle from the parking area
it should has a method to show the parking status
*/

class ParkingSerivce {
    private final int parkingLength = 100;
    private int totalMoney = 0;
    private int[] parkingArea = new int[parkingLength];
    private ArrayList<Vehicle> vehiclesInParking = new ArrayList<>();

    public void addToParking(String type, int id) {
        Vehicle vehicle = vehicleByType(type, id);
        if (vehicle == null) {
            System.out.println("Invalid vehicle type");
            return;
        }
        if (searchInParking(id) != null) {
            System.out.println("Vehicle with this id is already in parking");
            return;
        }
        vehiclesInParking.add(vehicle);
        int startIndex = findParkingSpace(vehicle.getLength());
        int endIndex = startIndex + vehicle.getLength();
        if (startIndex == -1) {
            System.out.println("No space available");
        } else {
            for (int i = startIndex; i < endIndex; i++) {
                parkingArea[i] = vehicle.getId();
            }
            System.out.println("Vehicle added successfully");
        }
    }

    public void releaseFromParking(int id, int hours) {
        Vehicle vehicle = searchInParking(id);
        if (vehicle == null) {
            System.out.println("Vehicle not found");
            return;
        }

        for (int i = 0; i < parkingLength; i++)
            if (parkingArea[i] == id) parkingArea[i] = 0;

        System.out.println("Vehicle released successfully");

        int money = costOfParking(vehicle, hours);
        totalMoney += money;
        System.out.println("Money to be payed: " + money);
    }

    public void showParkingStatus() {
        int space = 0;
        System.out.println("Parking Available Space: ");
        for (int i = 0; i < parkingLength; i++) {
            if (parkingArea[i] == 0) {
                space++;
            } else {
                if (space != 0) System.out.print(space + " ");
                space = 0;
            }
            if (i == parkingLength - 1 && space != 0) System.out.print(space + " ");
        }
        System.out.println();
    }

    private int costOfParking(Vehicle vehicle, int hours) {
        final int CAR_COST = 10, TRUCK_COST = 15, BUS_COST = 15, MOTORCYCLE_COST = 5;
        if (vehicle instanceof Car) {
            return hours * CAR_COST;
        } else if (vehicle instanceof Truck) {
            return hours * TRUCK_COST;
        } else if (vehicle instanceof Bus) {
            return hours * BUS_COST;
        } else if (vehicle instanceof Motorcycle) {
            return hours * MOTORCYCLE_COST;
        }
        return 0;
    }

    private int findParkingSpace(int spaceLength) {
        int countCells = 0;
        for (int i = 0; i < parkingLength; i++) {
            if (parkingArea[i] == 0) {
                countCells++;
                if (countCells == spaceLength) return i - spaceLength + 1;
            } else {
                countCells = 0;
            }
        }
        return -1;
    }

    private Vehicle searchInParking(int id) {
        for (Vehicle v : vehiclesInParking) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    private Vehicle vehicleByType(String type, int id) {
        type = type.toLowerCase();
        if (type.equals("car")) {
            return new Car(id);
        } else if (type.equals("truck")) {
            return new Truck(id);
        } else if (type.equals("bus")) {
            return new Bus(id);
        } else if (type.equals("motorcycle")) {
            return new Motorcycle(id);
        }
        return null;
    }


}

public class Main {
    public static void main(String[] args) {
        // User Manual
        // 1. Add a vehicle
        // 2. Release a vehicle
        // 3. Show parking status
        // 4. Exit
        ParkingSerivce parkingSerivce = new ParkingSerivce();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add a vehicle");
            System.out.println("2. Release a vehicle");
            System.out.println("3. Show parking status");
            System.out.println("4. Exit");
            int choice = in.nextInt();
            if (choice == 1) {
                System.out.println("Enter vehicle type");
                String type = in.next();
                System.out.println("Enter vehicle id");
                int id = in.nextInt();
                parkingSerivce.addToParking(type, id);
            } else if (choice == 2) {
                System.out.println("Enter vehicle id");
                int id = in.nextInt();
                System.out.println("Enter hours");
                int hours = in.nextInt();
                parkingSerivce.releaseFromParking(id, hours);
            } else if (choice == 3) {
                parkingSerivce.showParkingStatus();
            } else if (choice == 4) {
                break;
            }
        }
    }
}
