package org.example;  // Declare the package for the ATMTechnician class

import java.io.*;  // Import necessary classes for file handling and input/output


public class ATMTechnician {  // Define the ATMTechnician class
    private double cash;  // Variable to hold the amount of cash in the ATM
    private double ink;  // Variable to hold the ink level in the ATM
    private double paper;  // Variable to hold the paper level in the ATM
    private String password;  // Password for the technician to log in

    // Constructor to initialize the ATMTechnician with password, cash, ink, and paper
    public ATMTechnician(String password, double cash, double ink, double paper) {
        this.password = password;  // Set the technician's password
        this.cash = cash;  // Set the initial cash amount
        this.ink = ink;  // Set the initial ink level
        this.paper = paper;  // Set the initial paper level
    }

    // Getter method for password
    public String getPassword() {
        return password;  // Return the technician's password
    }

    // Method to validate if the input password matches the technician's password
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);  // Return true if passwords match, false otherwise
    }

    // Method to add cash to the ATM
    public void addCash(double amount) {
        this.cash += amount;  // Increase the cash by the given amount
        saveTechnician();  // Save the updated technician status after adding cash
    }

    public double getCash() {
        return cash;
    }

    // Method to add ink to the ATM
    public void addInk(double amount) {
        this.ink += amount;  // Increase the ink level by the given amount
        saveTechnician();  // Save the updated technician status after adding ink
    }

    // Method to add paper to the ATM
    public void addPaper(double amount) {
        this.paper += amount;  // Increase the paper level by the given amount
        saveTechnician();  // Save the updated technician status after adding paper
    }

    // Getter method for paper level
    public double getPaper() {
        return paper;  // Return the current level of paper in the ATM
    }

    // Getter method for ink level
    public double getInk() {
        return ink;  // Return the current level of ink in the ATM
    }

    // Method to display the current status of the ATM
    public void serviceATM() {
        System.out.println("ATM Status:");  // Print ATM status label
        System.out.println("Cash: " + cash);  // Print the current cash level in the ATM
        System.out.println("Ink: " + ink);  // Print the current ink level in the ATM
        System.out.println("Paper: " + paper);  // Print the current paper level in the ATM
    }

    // Static method to load the technician's information from the file
    public static ATMTechnician loadTechnician() {
        try {
            File file = new File("atm_status.txt");  // Create a file object to read the technician status from "atm_status.txt"
            if (!file.exists()) {  // If the file does not exist
                // Create a default technician file if it does not exist
                createDefaultTechnicianFile(file);  // Call the method to create the default technician file
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));  // Open the file for reading
            String line = reader.readLine();  // Read the first line of the file
            if (line != null) {  // If the line is not null (i.e., the file has content)
                String[] data = line.split(",");  // Split the line by commas to extract the technician's details
                reader.close();  // Close the reader after processing
                // Return a new ATMTechnician object created from the data read from the file
                return new ATMTechnician(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
            }
            reader.close();  // Close the reader if no data was found
        } catch (IOException e) {
            e.printStackTrace();  // Print the stack trace if there is an IOException
        }
        return null;  // Return null if the technician could not be loaded
    }

    // Private method to create a default technician file with initial values
    private static void createDefaultTechnicianFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));  // Open the file for writing
        writer.write("password123,1000.0,50.0,500.0");  // Write the default technician data to the file
        writer.close();  // Close the writer after writing
    }

    // Method to save the current technician's status (cash, ink, paper) to the file
    public void saveTechnician() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("atm_status.txt"));  // Open the file for writing
            // Write the current technician status to the file (password, cash, ink, paper)
            writer.write(this.password + "," + this.cash + "," + this.ink + "," + this.paper);
            writer.close();  // Close the writer after writing the data
        } catch (IOException e) {
            e.printStackTrace();  // Print the stack trace if there is an IOException
        }
    }
}
