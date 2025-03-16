package org.example;

import java.io.*;

public class ATMTechnician {
    private double cash;
    private double ink;
    private double paper;
    private String password;

    public ATMTechnician(String password, double cash, double ink, double paper) {
        this.password = password;
        this.cash = cash;
        this.ink = ink;
        this.paper = paper;
    }

    public String getPassword() {
        return password;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void addCash(double amount) {
        this.cash += amount;
        saveTechnician();
    }

    public double getCash() {
        return cash;
    }

    public void addInk(double amount) {
        this.ink += amount;
        saveTechnician();
    }

    public void addPaper(double amount) {
        this.paper += amount;
        saveTechnician();
    }

    public double getPaper() {
        return paper;
    }

    public double getInk() {
        return ink;
    }

    public void serviceATM() {
        System.out.println("ATM Status:");
        System.out.println("Cash: " + cash);
        System.out.println("Ink: " + ink);
        System.out.println("Paper: " + paper);
    }

    public static ATMTechnician loadTechnician() {
        try {
            File file = new File("atm_status.txt");
            if (!file.exists()) {
                createDefaultTechnicianFile(file);
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();

            // Check if the file contains valid data
            if (line != null && !line.trim().isEmpty()) {
                String[] data = line.split(",");
                if (data.length == 4) { // Ensure the file has all required fields
                    return new ATMTechnician(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                }
            }
            // If the file is empty or invalid, create a default file
            createDefaultTechnicianFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if the technician could not be loaded
    }

    private static void createDefaultTechnicianFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("password123,1000.0,50.0,500.0");
        writer.close();
    }

    public void saveTechnician() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("atm_status.txt"));
            writer.write(this.password + "," + this.cash + "," + this.ink + "," + this.paper);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}