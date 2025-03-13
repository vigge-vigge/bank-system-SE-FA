package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ATMTechnician technician = ATMTechnician.loadTechnician();
        Customer customer = null;

        // Print the working directory
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        while (true) {
            System.out.println("Welcome to the ATM System!");
            System.out.println("1. Customer Login");
            System.out.println("2. Technician Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine();

                try {
                    customer = Customer.loadCustomer(pin, false); // Login by PIN
                    if (customer != null) {
                        System.out.println("Hello " + customer.getName());
                        ATM atm = new ATM(customer, technician);
                        atm.startCustomerSession();
                    } else {
                        System.out.println("Invalid PIN.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) {
                System.out.print("Enter Technician Password: ");
                String password = scanner.nextLine();
                if (technician.validatePassword(password)) {
                    System.out.println("Login successful!");
                    ATM atm = new ATM(null, technician);
                    atm.startTechnicianSession();
                } else {
                    System.out.println("Invalid password.");
                }
            } else {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}