package org.example;  // Declare the package

import java.io.*;  // Import necessary classes for file handling and input/output

public class Customer {  // Define the Customer class
    private String name;  // Customer's name
    private String accountNumber;  // Customer's account number
    private double balance;  // Customer's account balance
    private String pin;  // Customer's PIN

    // Constructor to initialize customer with necessary details
    public Customer(String name, String accountNumber, double balance, String pin) {
        this.name = name;  // Set customer's name
        this.accountNumber = accountNumber;  // Set customer's account number
        this.balance = balance;  // Set initial account balance
        this.pin = pin;  // Set customer's PIN
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Getter method for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter method for balance
    public double getBalance() {
        return balance;
    }

    // Getter method for PIN
    public String getPin() {
        return pin;
    }

    // Method to deposit amount into the customer's account
    public void deposit(double amount) {
        this.balance += amount;  // Add the deposit amount to the current balance
    }

    // Method to withdraw amount from the customer's account
    public void withdraw(double amount) throws Exception {
        // Check if the withdrawal amount is greater than the balance
        if (amount > this.balance) {
            throw new Exception("Insufficient funds");  // Throw exception if funds are insufficient
        }
        this.balance -= amount;  // Subtract the withdrawal amount from the balance
    }

    // Method to transfer funds from this customer to another
    public void transferFunds(Customer destinationCustomer, double amount) throws Exception {
        this.withdraw(amount);  // Withdraw the amount from this customer’s account
        destinationCustomer.deposit(amount);  // Deposit the amount into the destination customer's account
    }

    // Static method to load a customer from a file based on account number and PIN
    public static Customer loadCustomer(String accountNumber, String pin) throws IOException {
        File file = new File("customer_balance.txt");  // File where customer data is stored
        if (!file.exists()) {  // If the file doesn't exist
            createDefaultCustomerFile();  // Create a default customer file with some initial data
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));  // Read the customer file
        String line;
        while ((line = reader.readLine()) != null) {  // Loop through each line in the file
            String[] data = line.split(",");  // Split the line by commas into an array
            // If the account number matches, and either the PIN is empty or the PIN matches
            if (data[0].equals(accountNumber) && (pin.isEmpty() || data[3].equals(pin))) {
                reader.close();  // Close the reader
                return new Customer(data[1], data[0], Double.parseDouble(data[2]), data[3]);  // Return the matching customer
            }
        }
        reader.close();  // Close the reader when done
        return null;  // Return null if no match was found
    }

    // Private method to create a default customer file with some initial data
    private static void createDefaultCustomerFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("customer_balance.txt"));  // Create a BufferedWriter to write to the file
        writer.write("123456,Alice,1000.0,1234\n");  // Write Alice’s customer data
        writer.write("987654,Bob,2000.0,5678\n");  // Write Bob’s customer data
        writer.close();  // Close the writer after writing
    }

    // Static method to save the customer data to the file
    public static void saveCustomer(Customer customer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("customer_balance.txt"));  // Read the current customer file
        StringBuilder sb = new StringBuilder();  // StringBuilder to hold the updated content
        String line;
        boolean updated = false;  // Flag to check if customer data was updated
        while ((line = reader.readLine()) != null) {  // Read each line in the customer file
            String[] data = line.split(",");  // Split the line by commas into an array
            if (data[0].equals(customer.getAccountNumber())) {  // If the account number matches
                sb.append(customer.getAccountNumber()).append(",")  // Append account number
                        .append(customer.name).append(",")  // Append customer name
                        .append(customer.getBalance()).append(",")  // Append balance
                        .append(customer.getPin()).append("\n");  // Append PIN and move to next line
                updated = true;  // Mark that data was updated
            } else {
                sb.append(line).append("\n");  // Otherwise, append the line as is
            }
        }
        if (!updated) {  // If no data was updated
            sb.append(customer.getAccountNumber()).append(",")  // Append the new customer data
                    .append(customer.name).append(",")
                    .append(customer.getBalance()).append(",")
                    .append(customer.getPin()).append("\n");
        }
        reader.close();  // Close the reader after processing the file
        BufferedWriter writer = new BufferedWriter(new FileWriter("customer_balance.txt"));  // Open a BufferedWriter to overwrite the file
        writer.write(sb.toString());  // Write the updated content to the file
        writer.close();  // Close the writer after writing
    }
}
