package org.example;  // Declare the package

import org.example.ATM;  // Import the ATM class
import org.example.ATMTechnician;  // Import the ATMTechnician class
import org.example.Customer;  // Import the Customer class

import java.util.Scanner;  // Import Scanner class to handle user input

public class Main {  // Define the Main class that runs the ATM system
    public static void main(String[] args) throws Exception {  // Main method that runs the program
        Scanner scanner = new Scanner(System.in);  // Create a scanner to read user input
        ATMTechnician technician = ATMTechnician.loadTechnician();  // Load the technician data from storage
        Customer customer = null;  // Declare a variable to hold the customer (initialized as null)

        while (true) {  // Infinite loop to keep the menu running until the user exits
            System.out.println("Welcome to the ATM System!");  // Display welcome message
            System.out.println("1. Customer Login");  // Option for customer login
            System.out.println("2. Technician Login");  // Option for technician login
            System.out.println("3. Exit");  // Option to exit the program
            System.out.print("Choose an option: ");  // Prompt the user to choose an option

            int choice = scanner.nextInt();  // Read the user's choice
            scanner.nextLine();  // Consume the leftover newline character after nextInt()

            if (choice == 1) {  // If the user selects Customer Login
                System.out.print("Enter Account Number: ");  // Prompt for the customer’s account number
                String accountNumber = scanner.nextLine();  // Read the account number
                System.out.print("Enter PIN: ");  // Prompt for the customer's PIN
                String pin = scanner.nextLine();  // Read the PIN

                try {
                    customer = Customer.loadCustomer(accountNumber, pin);  // Load customer data based on account and PIN
                    if (customer != null) {  // If the customer is found
                        System.out.println("Hello " + customer.getName());  // Display a welcome message with the customer’s name
                        ATM atm = new ATM(customer, technician);  // Create a new ATM instance with the customer and technician
                        atm.startCustomerSession();  // Start the customer session
                    } else {  // If the customer is not found
                        System.out.println("Invalid account number or PIN.");  // Notify the user of invalid credentials
                    }
                } catch (Exception e) {  // Catch any exceptions that might occur during customer login
                    e.printStackTrace();  // Print the stack trace for debugging
                }
            } else if (choice == 2) {  // If the user selects Technician Login
                System.out.print("Enter Technician Password: ");  // Prompt for the technician’s password
                String password = scanner.nextLine();  // Read the password
                if (technician.validatePassword(password)) {  // Check if the password is correct
                    System.out.println("Login successful!");  // Notify the technician of successful login
                    ATM atm = new ATM(customer, technician);  // Create a new ATM instance with the technician and customer (null)
                    atm.startTechnicianSession();  // Start the technician session
                } else {  // If the password is incorrect
                    System.out.println("Invalid password.");  // Notify the technician of an invalid password
                }
            } else {  // If the user selects the Exit option
                System.out.println("Goodbye!");  // Display a goodbye message
                break;  // Exit the loop and terminate the program
            }
        }
    }
}
