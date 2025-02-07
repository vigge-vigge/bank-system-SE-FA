package org.example;  // Declare the package

import java.util.Scanner;  // Import Scanner class to handle user input

public class ATM {  // Define the ATM class
    private Customer customer;  // Declare a Customer object to hold the customer information
    private ATMTechnician technician;  // Declare an ATMTechnician object to hold the technician's information

    // Constructor to initialize ATM with a customer and technician
    public ATM(Customer customer, ATMTechnician technician) {
        this.customer = customer;  // Initialize the customer object
        this.technician = technician;  // Initialize the technician object
    }

    // Method to start the customer session
    public void startCustomerSession() throws Exception {
        Scanner scanner = new Scanner(System.in);  // Create a scanner to read user input
        while (true) {  // Infinite loop to keep showing the menu until the user logs out
            System.out.println("\nCustomer Menu:");  // Display customer menu
            System.out.println("1. Check Balance");  // Option to check balance
            System.out.println("2. Withdraw");  // Option to withdraw money
            System.out.println("3. Deposit");  // Option to deposit money
            System.out.println("4. Transfer Money");  // Option to transfer money
            System.out.println("5. Logout");  // Option to logout
            System.out.print("Choose an option: ");  // Prompt the user to enter an option
            int choice = scanner.nextInt();  // Read the user's choice
            scanner.nextLine();  // Consume the leftover newline character after nextInt()

            switch (choice) {  // Switch statement to handle the user's choice
                case 1:  // If the user chooses to check balance
                    System.out.println("Current Balance: " + customer.getBalance());  // Display current balance
                    break;  // Break the switch case
                case 2:  // If the user chooses to withdraw money
                    System.out.print("Enter amount to withdraw: ");  // Prompt the user for withdrawal amount
                    double withdrawAmount = scanner.nextDouble();  // Read the withdrawal amount
                    scanner.nextLine();  // Consume the newline character

                    try {
                        customer.withdraw(withdrawAmount);  // Call the withdraw method for the customer
                        // Check and update paper and ink levels after withdrawal
                        if (technician.getPaper() > 0 && technician.getInk() > 0) {
                            technician.addPaper(-1);  // Reduce paper (1 receipt)
                            technician.addInk(-0.5);  // Reduce ink (0.5 unit per withdrawal)
                            System.out.println("Paper and Ink levels updated!");  // Notify about paper and ink update
                        } else {
                            System.out.println("Warning: Insufficient paper or ink!");  // Show warning if paper/ink is low
                        }
                        Customer.saveCustomer(customer);  // Save updated customer data after withdrawal
                        System.out.println("Withdrawal successful!");  // Notify user of success
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());  // Catch any errors during withdrawal
                    }
                    break;  // Break the switch case
                case 3:  // If the user chooses to deposit money
                    System.out.print("Enter amount to deposit: ");  // Prompt user for deposit amount
                    double depositAmount = scanner.nextDouble();  // Read the deposit amount
                    scanner.nextLine();  // Consume the newline character
                    customer.deposit(depositAmount);  // Call the deposit method for the customer
                    Customer.saveCustomer(customer);  // Save updated customer data after deposit
                    System.out.println("Deposit successful!");  // Notify user of success
                    break;  // Break the switch case
                case 4:  // If the user chooses to transfer money
                    System.out.print("Enter recipient's account number: ");  // Prompt user for recipient account number
                    String recipientAccount = scanner.nextLine();  // Read the recipient account number
                    System.out.print("Enter amount to transfer: ");  // Prompt user for transfer amount
                    double transferAmount = scanner.nextDouble();  // Read the transfer amount
                    scanner.nextLine();  // Consume the newline character

                    // Load recipient customer from file using account number
                    Customer recipientCustomer = Customer.loadCustomer(recipientAccount, "");
                    if (recipientCustomer != null) {  // If recipient exists
                        try {
                            customer.transferFunds(recipientCustomer, transferAmount);  // Transfer funds to recipient
                            Customer.saveCustomer(customer);  // Save updated customer data after transfer
                            Customer.saveCustomer(recipientCustomer);  // Save recipient data
                            System.out.println("Transfer successful!");  // Notify user of success
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());  // Catch any errors during transfer
                        }
                    } else {
                        System.out.println("Recipient not found.");  // Notify if recipient is not found
                    }
                    break;  // Break the switch case
                case 5:  // If the user chooses to logout
                    System.out.println("Logging out...");  // Notify user about logout
                    return;  // Exit the method and log the user out
                default:  // If the user enters an invalid choice
                    System.out.println("Invalid choice, try again.");  // Notify user of invalid choice
            }
        }
    }

    // Method to start the technician session
    public void startTechnicianSession() {
        Scanner scanner = new Scanner(System.in);  // Create a scanner to read user input
        while (true) {  // Infinite loop to keep showing the technician menu until they log out
            System.out.println("\nTechnician Menu:");  // Display technician menu
            System.out.println("1. Add Cash");  // Option to add cash
            System.out.println("2. Add Ink");  // Option to add ink
            System.out.println("3. Add Paper");  // Option to add paper
            System.out.println("4. Service ATM (View Status)");  // Option to service ATM
            System.out.println("5. Logout");  // Option to logout
            System.out.print("Choose an option: ");  // Prompt technician for an option
            int choice = scanner.nextInt();  // Read technician's choice
            scanner.nextLine();  // Consume the leftover newline character after nextInt()

            switch (choice) {  // Switch statement to handle the technician's choice
                case 1:  // If the technician chooses to add cash
                    System.out.print("Enter amount of cash to add: ");  // Prompt technician for cash amount
                    double cashAmount = scanner.nextDouble();  // Read cash amount
                    scanner.nextLine();  // Consume the newline
                    technician.addCash(cashAmount);  // Call the addCash method for the technician
                    System.out.println("Cash added successfully!");  // Notify technician of success
                    break;  // Break the switch case
                case 2:  // If the technician chooses to add ink
                    System.out.print("Enter amount of ink to add: ");  // Prompt technician for ink amount
                    double inkAmount = scanner.nextDouble();  // Read ink amount
                    scanner.nextLine();  // Consume the newline
                    technician.addInk(inkAmount);  // Call the addInk method for the technician
                    System.out.println("Ink added successfully!");  // Notify technician of success
                    break;  // Break the switch case
                case 3:  // If the technician chooses to add paper
                    System.out.print("Enter amount of paper to add: ");  // Prompt technician for paper amount
                    double paperAmount = scanner.nextDouble();  // Read paper amount
                    scanner.nextLine();  // Consume the newline
                    technician.addPaper(paperAmount);  // Call the addPaper method for the technician
                    System.out.println("Paper added successfully!");  // Notify technician of success
                    break;  // Break the switch case
                case 4:  // If the technician chooses to service the ATM
                    technician.serviceATM();  // Display the ATM status
                    break;  // Break the switch case
                case 5:  // If the technician chooses to logout
                    System.out.println("Logging out...");  // Notify technician of logout
                    return;  // Exit the method and log the technician out
                default:  // If the technician enters an invalid choice
                    System.out.println("Invalid choice, try again.");  // Notify technician of invalid choice
            }
        }
    }
}
