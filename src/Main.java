import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
it should has a method to add a vehicle to the parking a rea
it should has a method to remove a vehicle from the parking area
it should has a method to show the parking status
*/

class ParkingSerivce {
    private final int parkingLength = 100;
    private int totalMoney = 0;
    private final int[] parkingArea = new int[parkingLength];
    private final ArrayList<Vehicle> vehiclesInParking = new ArrayList<>();
    final int CAR_COST = 10, TRUCK_COST = 15, BUS_COST = 15, MOTORCYCLE_COST = 5;

    public void addToParking(String type, String idd) {
        // if idd is not a number
        if (!idd.matches("[0-9]+")) {
            System.out.println("ID must be a number, please try again");
            JOptionPane.showMessageDialog(GUI.frame, "ID must be a number, please try again");
            return;
        }
        int id = Integer.parseInt(idd);
        Vehicle vehicle = vehicleByType(type, id);
        if (vehicle == null) {
            System.out.println("Invalid vehicle type");
            JOptionPane.showMessageDialog(GUI.frame, "Invalid vehicle type");
            return;
        }
        if (searchInParking(id) != null) {
            System.out.println("Vehicle with this id is already in parking");
            JOptionPane.showMessageDialog(GUI.frame, "Vehicle with this id is already in parking");
            return;
        }
        vehiclesInParking.add(vehicle);
        int startIndex = findParkingSpace(vehicle.getLength());
        int endIndex = startIndex + vehicle.getLength();
        if (startIndex == -1) {
            System.out.println("No space available");
            JOptionPane.showMessageDialog(GUI.frame, "No space available");
        } else {
            for (int i = startIndex; i < endIndex; i++) {
                parkingArea[i] = vehicle.getId();
            }
            System.out.println("Vehicle added successfully");
            JOptionPane.showMessageDialog(GUI.frame, "Vehicle added successfully");
        }
        // Show info message about the added vehicle
        System.err.println("INFO : " + vehicle);
    }

    private boolean validHours(int hours) {
        if (hours < 0) {
            System.out.println("Hours must be positive number");
            JOptionPane.showMessageDialog(GUI.frame, "Hours must be positive number");
            return false;
        }
        return true;
    }

    private boolean positiveNumber(String number) {
        return number.matches("[0-9]+");
    }

    private boolean validNumber(String number) {
        return !number.matches("[a-zA-Z]+");
    }


    public void releaseFromParking(String idd, String hourss) {
        if (!validNumber(idd) || !validNumber(hourss)) {
            System.out.println("ID and hours must be numbers");
            JOptionPane.showMessageDialog(GUI.frame, "ID and hours must be numbers");
            return;
        }
        if (!positiveNumber(idd) || !positiveNumber(hourss)) {
            System.out.println("ID and hours must be positive numbers");
            JOptionPane.showMessageDialog(GUI.frame, "ID and hours must be positive numbers");
            return;
        }
        int id = Integer.parseInt(idd);
        int hours = Integer.parseInt(hourss);
        Vehicle vehicle = searchInParking(id);

        if (vehicle == null) {
            System.out.println("Vehicle not found, please try again");
            JOptionPane.showMessageDialog(GUI.frame, "Vehicle not found, please try again");
            return;
        }

        for (int i = 0; i < parkingLength; i++)
            if (parkingArea[i] == id) parkingArea[i] = 0;

        // Removing the released vehicle from the parkingList
        vehiclesInParking.remove(vehicle);


        int money = costOfParking(vehicle, hours);
        totalMoney += money;
        System.out.println("Vehicle released successfully");
        System.out.println("Money to be payed: " + money);
        JOptionPane.showMessageDialog(GUI.frame, "Vehicle released successfully\nMoney to be payed: " + money);
    }

    public void showParkingStatus() {
        int space = 0;
        String ms = "Parking Available Space: \n| ";
        for (int i = 0; i < parkingLength; i++) {
            if (parkingArea[i] == 0) {
                space++;
            } else {
                if (space != 0) {
                    System.out.print(space + " | ");
                    ms += space + " | ";
                }
                space = 0;
            }
            if (i == parkingLength - 1 && space != 0) {
                System.out.print(space + " | ");
                ms += space + " | ";
            }
        }
        JOptionPane.showMessageDialog(GUI.frame, ms);
    }

    int getTotalMoney() {
        return totalMoney;
    }

    private int costOfParking(Vehicle vehicle, int hours) {
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
    // a function to set button properties
    public static void main(String[] args) {
        GUI.start();
    }
}