package org.example;

import java.util.Scanner;

public class ATM {
    private Customer customer;
    private ATMTechnician technician;

    // Constructor to initialize the ATM session with a customer and a technician
    public ATM(Customer customer, ATMTechnician technician) {
        this.customer = customer;
        this.technician = technician;
    }

    // Method to start a customer session
    public void startCustomerSession() throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer Money");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            // Switch-case to handle different customer menu choices
            switch (choice) {
                case 1:
                    System.out.println("Current Balance: " + customer.getBalance());
                    break;
                case 2: // Withdraw money
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        customer.withdraw(withdrawAmount);
                        // Checks if paper and ink are available, then deducts from the technician's stock
                        if (technician.getPaper() > 0 && technician.getInk() > 0) {
                            technician.addPaper(-1);
                            technician.addInk(-0.5);
                            System.out.println("Paper and Ink levels updated!");
                        } else {
                            System.out.println("Warning: Insufficient paper or ink!"); // Warns if resources are insufficient
                        }
                        Customer.saveCustomer(customer); // Saves the customer data after withdrawal
                        System.out.println("Withdrawal successful!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3: // Deposit money
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();
                    customer.deposit(depositAmount);
                    Customer.saveCustomer(customer);
                    System.out.println("Deposit successful!");
                    break;
                case 4: // Transfer money to another account
                    System.out.print("Enter recipient's account number: ");
                    String recipientAccount = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();

                    // Attempts to load the recipient customer by account number
                    Customer recipientCustomer = Customer.loadCustomer(recipientAccount, true);
                    if (recipientCustomer != null) {
                        try {
                            customer.transferFunds(recipientCustomer, transferAmount);
                            Customer.saveCustomer(customer);
                            Customer.saveCustomer(recipientCustomer);
                            System.out.println("Transfer successful!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5: // Logout
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    // Method to start a technician session
    public void startTechnicianSession() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nTechnician Menu:");
            System.out.println("1. Add Cash");
            System.out.println("2. Add Ink");
            System.out.println("3. Add Paper");
            System.out.println("4. Service ATM (View Status)");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            // Switch-case to handle different technician menu choices
            switch (choice) {
                case 1:
                    System.out.print("Enter amount of cash to add: ");
                    double cashAmount = scanner.nextDouble();
                    scanner.nextLine();
                    technician.addCash(cashAmount);
                    System.out.println("Cash added successfully!");
                    break;
                case 2: // Add ink to the ATM
                    System.out.print("Enter amount of ink to add: ");
                    double inkAmount = scanner.nextDouble();
                    scanner.nextLine();
                    technician.addInk(inkAmount);
                    System.out.println("Ink added successfully!");
                    break;
                case 3:
                    System.out.print("Enter amount of paper to add: ");
                    double paperAmount = scanner.nextDouble();
                    scanner.nextLine();
                    technician.addPaper(paperAmount);
                    System.out.println("Paper added successfully!");
                    break;
                case 4:
                    technician.serviceATM();
                    break;
                case 5: // Logout
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
