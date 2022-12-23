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

    public void addToParking(String type, String idd) {
        // if idd is not a number
        if (!idd.matches("[0-9]+")) {
            System.out.println("ID must be a number, please try again");
            JOptionPane.showMessageDialog(null, "ID must be a number, please try again");
            return;
        }
        int id = Integer.parseInt(idd);
        Vehicle vehicle = vehicleByType(type, id);
        if (vehicle == null) {
            System.out.println("Invalid vehicle type");
            JOptionPane.showMessageDialog(null, "Invalid vehicle type");
            return;
        }
        if (searchInParking(id) != null) {
            System.out.println("Vehicle with this id is already in parking");
            JOptionPane.showMessageDialog(null, "Vehicle with this id is already in parking");
            return;
        }
        vehiclesInParking.add(vehicle);
        int startIndex = findParkingSpace(vehicle.getLength());
        int endIndex = startIndex + vehicle.getLength();
        if (startIndex == -1) {
            System.out.println("No space available");
            JOptionPane.showMessageDialog(null, "No space available");
        } else {
            for (int i = startIndex; i < endIndex; i++) {
                parkingArea[i] = vehicle.getId();
            }
            System.out.println("Vehicle added successfully");
            JOptionPane.showMessageDialog(null, "Vehicle added successfully");
        }
    }

    private boolean validHours(int hours) {
        if (hours < 0) {
            System.out.println("Hours must be positive number");
            JOptionPane.showMessageDialog(null, "Hours must be positive number");
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
        if(!validNumber(idd) || !validNumber(hourss)) {
            System.out.println("ID and hours must be numbers");
            JOptionPane.showMessageDialog(null, "ID and hours must be numbers");
            return;
        }
        if(!positiveNumber(idd) || !positiveNumber(hourss)) {
            System.out.println("ID and hours must be positive numbers");
            JOptionPane.showMessageDialog(null, "ID and hours must be positive numbers");
            return;
        }
        int id = Integer.parseInt(idd);
        int hours = Integer.parseInt(hourss);
        Vehicle vehicle = searchInParking(id);

        if (vehicle == null) {
            System.out.println("Vehicle not found, please try again");
            JOptionPane.showMessageDialog(null, "Vehicle not found, please try again");
            return;
        }

        for (int i = 0; i < parkingLength; i++)
            if (parkingArea[i] == id) parkingArea[i] = 0;


        int money = costOfParking(vehicle, hours);
        totalMoney += money;
        System.out.println("Vehicle released successfully");
        System.out.println("Money to be payed: " + money);
        JOptionPane.showMessageDialog(null, "Vehicle released successfully\nMoney to be payed: " + money);
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
        JOptionPane.showMessageDialog(null, ms);
    }

    int getTotalMoney() {
        return totalMoney;
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
        ParkingSerivce parkingSerivce = new ParkingSerivce();
        JFrame frame = new JFrame("Parking");
        JButton releaseVehicle = new JButton("Release Vehicle");
        JButton showParkingStatus = new JButton("Show Parking Status");
        JButton showTotalMoney = new JButton("Show Total Money");
        JButton addVehicle = new JButton("Add Vehicle");
        // set buttons color to dark gray, and text color to white
        // remove the border of the button
        releaseVehicle.setBackground(Color.DARK_GRAY);
        releaseVehicle.setForeground(Color.WHITE);
        showParkingStatus.setBackground(Color.DARK_GRAY);
        showParkingStatus.setForeground(Color.WHITE);
        showTotalMoney.setBackground(Color.DARK_GRAY);
        showTotalMoney.setForeground(Color.WHITE);
        addVehicle.setBackground(Color.DARK_GRAY);
        addVehicle.setForeground(Color.WHITE);
        releaseVehicle.setBorder(BorderFactory.createEmptyBorder());
        showParkingStatus.setBorder(BorderFactory.createEmptyBorder());
        showTotalMoney.setBorder(BorderFactory.createEmptyBorder());
        addVehicle.setBorder(BorderFactory.createEmptyBorder());
        // set buttons font
        releaseVehicle.setFont(new Font("Cascadia Code", Font.BOLD, 15));
        showParkingStatus.setFont(new Font("Cascadia Code", Font.BOLD, 15));
        showTotalMoney.setFont(new Font("Cascadia Code", Font.BOLD, 15));
        addVehicle.setFont(new Font("Cascadia Code", Font.BOLD, 15));
        // set buttons size

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setResizable(false);
        addVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] vehicleTypes = {"Car", "Truck", "Bus", "Motorcycle"};
                JComboBox type = new JComboBox(vehicleTypes);
                JTextField id = new JTextField();
                Object[] message = {
                        "Type:", type,
                        "ID:", id
                };
                int option = JOptionPane.showConfirmDialog(null, message, "Add Vehicle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    parkingSerivce.addToParking((String) type.getSelectedItem(), id.getText());
                }
            }
        });
        releaseVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField id = new JTextField();
                JTextField hours = new JTextField();
                Object[] message = {
                        "Vehicle ID:", id,
                        "Hours:", hours,
                };
                int option = JOptionPane.showConfirmDialog(frame, message, "Release Vehicle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    parkingSerivce.releaseFromParking(id.getText(),hours.getText());
                }
            }
        });
        showParkingStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parkingSerivce.showParkingStatus();
            }
        });
        showTotalMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Total Money: " + parkingSerivce.getTotalMoney());

            }
        });
        addVehicle.setBounds(50, 50, 200, 50);
        releaseVehicle.setBounds(50, 150, 200, 50);
        showParkingStatus.setBounds(50, 250, 200, 50);
        showTotalMoney.setBounds(50, 350, 200, 50);
        frame.add(addVehicle);
        frame.add(releaseVehicle);
        frame.add(showParkingStatus);
        frame.add(showTotalMoney);
//        frame.setLayout(new FlowLayout());
    }
}
