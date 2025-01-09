import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI {
    public static JFrame frame = new JFrame("Parking");
    public static void setButtonProperties(JButton button, String text, int x, int y, int width, int height) {
        button.setText(text);
        button.setBounds(x, y, width, height);
        button.setFocusable(false);
        button.setFont(new Font("Comic Sans", Font.PLAIN, 15));
        button.setBackground(Color.lightGray);
        button.setForeground(Color.black);
    }

    // funciton to set frame properties
    public static void setFrameProperties(int width, int height, String title) {
        GUI.frame.setTitle(title);
        GUI.frame.setLocationRelativeTo(null);
        GUI.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.frame.setResizable(false);
        GUI.frame.setSize(width, height);
        GUI.frame.setLayout(null);
        GUI.frame.setVisible(true);
        GUI.frame.getContentPane().setBackground(Color.darkGray);
    }

    public static void start() {
        ParkingSerivce parkingSerivce = new ParkingSerivce();
        setFrameProperties(300, 500, "Parking");
        JButton addVehicle = new JButton("Add Vehicle");
        JButton releaseVehicle = new JButton("Release Vehicle");
        JButton showParkingStatus = new JButton("Show Parking Status");
        JButton showTotalMoney = new JButton("Show Total Money");
        setButtonProperties(addVehicle, "Add Vehicle", 50, 50, 200, 50);
        setButtonProperties(releaseVehicle, "Release Vehicle", 50, 150, 200, 50);
        setButtonProperties(showParkingStatus, "Show Parking Status", 50, 250, 200, 50);
        setButtonProperties(showTotalMoney, "Show Total Money", 50, 350, 200, 50);
        GUI.frame.add(addVehicle, BorderLayout.CENTER);
        GUI.frame.add(releaseVehicle, BorderLayout.CENTER);
        GUI.frame.add(showParkingStatus, BorderLayout.CENTER);
        GUI.frame.add(showTotalMoney, BorderLayout.CENTER);
        JLabel names = new JLabel("Names : Omar");
        // Make names at center
        names.setHorizontalAlignment(JLabel.CENTER);
        names.setBounds(45, 10, 200, 30);
        names.setForeground(Color.white);
        // set font size
        names.setFont(new Font("Comic Sans", Font.PLAIN, 17));
        names.setForeground(Color.white);
        GUI.frame.add(names);



//       Bottons Actions
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
                int option = JOptionPane.showConfirmDialog(GUI.frame, message, "Add Vehicle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    parkingSerivce.addToParking((String) type.getSelectedItem(), id.getText());
                }
            }
        });
        releaseVehicle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField id = new JTextField();
                JTextField hours = new JTextField();
                Object[] message = {
                        "Vehicle ID:", id,
                        "Hours:", hours,
                };
                int option = JOptionPane.showConfirmDialog(GUI.frame, message, "Release Vehicle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    parkingSerivce.releaseFromParking(id.getText(), hours.getText());
                }
            }
        });
        showParkingStatus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parkingSerivce.showParkingStatus();
            }
        });
        showTotalMoney.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GUI.frame, "Total Money: " + parkingSerivce.getTotalMoney());

            }
        });
    }
}
