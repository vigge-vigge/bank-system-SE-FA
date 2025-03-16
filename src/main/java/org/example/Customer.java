package org.example;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer {
    private String name;
    private String accountNumber;
    private double balance;
    private String pin;

    public Customer(String name, String accountNumber, double balance, String pin) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) throws Exception {
        if (amount > this.balance) {
            throw new Exception("Insufficient funds");
        }
        this.balance -= amount;
    }

    public void transferFunds(Customer destinationCustomer, double amount) throws Exception {
        this.withdraw(amount);
        destinationCustomer.deposit(amount);
        logTransferHistory(this, destinationCustomer, amount);
    }

    public static Customer loadCustomer(String identifier, boolean isAccountNumber) throws IOException {
        File file = new File("customer_balance.txt");
        if (!file.exists()) {
            createDefaultCustomerFile(file);
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (isAccountNumber) {
                if (data[0].equals(identifier)) {
                    reader.close();
                    return new Customer(data[1], data[0], Double.parseDouble(data[2]), data[3]);
                }
            } else {
                if (data[3].equals(identifier)) {
                    reader.close();
                    return new Customer(data[1], data[0], Double.parseDouble(data[2]), data[3]);
                }
            }
        }
        reader.close();
        return null;
    }

    private static void createDefaultCustomerFile(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("123456,Alice,890.0,1234\n");
        writer.write("987654,Bob,2000.0,5678\n");
        writer.write("567890,Vigge,20000.0,1234\n");
        writer.close();
    }

    public static void saveCustomer(Customer customer) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("customer_balance.txt"));
        StringBuilder sb = new StringBuilder();
        String line;
        boolean updated = false;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(customer.getAccountNumber())) {
                sb.append(customer.getAccountNumber()).append(",")
                        .append(customer.getName()).append(",")
                        .append(customer.getBalance()).append(",")
                        .append(customer.getPin()).append("\n");
                updated = true;
            } else {
                sb.append(line).append("\n");
            }
        }
        if (!updated) {
            sb.append(customer.getAccountNumber()).append(",")
                    .append(customer.getName()).append(",")
                    .append(customer.getBalance()).append(",")
                    .append(customer.getPin()).append("\n");
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter("customer_balance.txt"));
        writer.write(sb.toString());
        writer.close();
    }

    private void logTransferHistory(Customer sender, Customer recipient, double amount) {
        File file = new File("transfer_history.txt");

        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Created transfer_history.txt");
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());

            writer.write("[" + timestamp + "] Sender: " + sender.getAccountNumber() + " (" + sender.getName() + ") -> "
                    + "Recipient: " + recipient.getAccountNumber() + " (" + recipient.getName() + ") Amount: " + amount + "\n");
            writer.close();

            System.out.println("Transfer logged successfully.");
        } catch (IOException e) {
            System.out.println("Error creating or writing to transfer_history.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}