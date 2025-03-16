package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ATMTechnician technician = ATMTechnician.loadTechnician(); // Loads the ATM Technician using a static method 'loadTechnician'
        Customer customer = null; // Initializes a 'Customer' object as null (it will hold customer information after login)

        // Print the working directory to show where the program is running
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        // a while loop to keep the ATM running until the user exits
        while (true) {
            // Displays the ATM menu for the user
            System.out.println("Welcome to the ATM System!");
            System.out.println("1. Customer Login");
            System.out.println("2. Technician Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            // Customer login logic
            if (choice == 1) {
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine();

                try {
                    // pin provided and session starts if the pin is correct for the customer
                    customer = Customer.loadCustomer(pin, false);
                    if (customer != null) { // Checks if the customer object is not null (successful login)
                        System.out.println("Hello " + customer.getName());
                        ATM atm = new ATM(customer, technician);
                        atm.startCustomerSession();
                    } else {
                        System.out.println("Invalid PIN."); // If no customer is found, an invalid PIN message is shown
                    }
                } catch (Exception e) { // Catches any exceptions that might occur during the login process
                    e.printStackTrace(); // Prints the stack trace of the exception
                }
            }
            // Technician login logic
            else if (choice == 2) {
                System.out.print("Enter Technician Password: ");
                String password = scanner.nextLine();

                // Checks if the entered password is valid
                if (technician.validatePassword(password)) {
                    System.out.println("Login successful!");
                    ATM atm = new ATM(null, technician);
                    atm.startTechnicianSession();
                } else {
                    System.out.println("Invalid password.");
                }
            }
            // Exit logic
            else {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}
