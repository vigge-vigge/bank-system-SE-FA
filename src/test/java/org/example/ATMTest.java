package org.example;  // Declare the package that this class belongs to.

import org.junit.jupiter.api.BeforeEach;  // Import necessary JUnit annotations for test setup.
import org.junit.jupiter.api.Test;  // Import the JUnit Test annotation to mark test methods.

import java.io.ByteArrayInputStream;  // Import necessary class to simulate user input via byte array.

import static org.junit.jupiter.api.Assertions.*;  // Import JUnit assertion methods to check results.

public class ATMTest {  // Define the ATMTest class where all tests are located.
    private Customer customer;  // Declare a Customer instance for use in tests.
    private ATMTechnician technician;  // Declare an ATMTechnician instance for use in tests.
    private ATM atm;  // Declare an ATM instance for testing.

    @BeforeEach  // Annotation to indicate that the following method should run before each test method.
    public void setup() {
        // Initialize the customer with a balance via the constructor.
        customer = new Customer("Alice", "123456", 1000.0, "1234");  // Create a new customer with a balance of 1000.

        // Initialize the technician with various attributes (e.g., password, cash, ink, and paper amounts).
        technician = new ATMTechnician("password123", 1000.0, 49.5, 499.0);  // Set up a technician with initial values.

        // Initialize the ATM with customer and technician instances.
        atm = new ATM(customer, technician);  // Create an ATM object with customer and technician.
    }

    @Test  // Marks the following method as a test to be executed by the testing framework.
    public void testCheckBalance() throws Exception {
        // Check initial balance directly through the customer's getter.
        assertEquals(1000, customer.getBalance(), "Initial balance should be 1000");  // Assert that the initial balance is 1000.

        // Simulate checking balance by starting the ATM session for the customer.
        atm.startCustomerSession();  // This triggers the ATM session, which should display the balance.
    }

    @Test  // Another test method for depositing money into the ATM.
    public void testDeposit() throws Exception {
        double depositAmount = 500;  // Declare the deposit amount for the test.

        // Simulate the customer interacting with the ATM to make a deposit.
        atm.startCustomerSession();  // This starts the ATM session for the customer.
        customer.deposit(depositAmount);  // Directly call the deposit method to increase the balance by 500.

        // Assert that after the deposit, the balance should be 1500.
        assertEquals(1500, customer.getBalance(), "Balance after deposit should be 1500");  // Assert balance after deposit.
    }

    @Test  // Test method for withdrawing money from the ATM.
    public void testWithdraw() throws Exception {
        String input = "200\n";  // Simulate user input (withdrawing 200).

        // Set the input stream to simulate user input during the ATM session.
        System.setIn(new ByteArrayInputStream(input.getBytes()));  // Redirect System.in to simulate input for withdrawal.

        double initialBalance = customer.getBalance();  // Store the initial balance of the customer before withdrawal.

        atm.startCustomerSession();  // Start the ATM session, which will use the simulated input for withdrawal.

        // Assert that after the withdrawal, the balance is correctly reduced by 200.
        assertEquals(initialBalance - 200, customer.getBalance(), "Balance after withdrawal should be correct");  // Check balance after withdrawal.
    }

    @Test  // Test method for transferring money between customers.
    public void testTransferMoney() throws Exception {
        // Create a recipient customer to transfer funds to.
        Customer recipientCustomer = new Customer("67890", "67890", 500.0, "0000");  // Initialize recipient with an initial balance.

        double transferAmount = 200;  // Amount to transfer in this test case.

        // Simulate the ATM session to trigger the transfer.
        atm.startCustomerSession();  // Start the ATM session for the customer to initiate the transfer.
        customer.transferFunds(recipientCustomer, transferAmount);  // Direct method call for transfer of funds.

        // Assert that after the transfer, the balances are updated correctly.
        assertEquals(800, customer.getBalance(), "Customer's balance after transfer should be correct");  // Assert customer's balance.
        assertEquals(700, recipientCustomer.getBalance(), "Recipient's balance after transfer should be correct");  // Assert recipient's balance.
    }

    @Test  // Test method to simulate technician adding cash to the ATM.
    public void testTechnicianAddCash() {
        double cashAmount = 500;  // Amount of cash the technician will add to the ATM.

        // Simulate starting the technician session to add cash.
        atm.startTechnicianSession();  // Start the technician's session.
        technician.addCash(cashAmount);  // Call the technician's method to add the specified cash amount.

        // Add an assertion here to check the technician's cash balance if necessary, e.g.:
        // assertEquals(technician.getCash(), 500);  // This would verify the technician's cash balance.
    }

    @Test  // Test method for adding ink to the ATM.
    public void testTechnicianAddInk() {
        double inkAmount = 20;  // Amount of ink the technician will add.

        // Simulate the technician session to add ink.
        atm.startTechnicianSession();  // Start the technician's session.
        technician.addInk(inkAmount);  // Add the specified ink amount to the ATM.

        // You could add an assertion here to verify ink levels, e.g.:
        // assertEquals(technician.getInk(), 20);  // Check if ink has been added correctly.
    }

    @Test  // Test method for adding paper to the ATM.
    public void testTechnicianAddPaper() {
        double paperAmount = 100;  // Amount of paper the technician will add.

        // Simulate the technician session to add paper to the ATM.
        atm.startTechnicianSession();  // Start the technician's session.
        technician.addPaper(paperAmount);  // Add the specified amount of paper.

        // You could add an assertion here to verify the paper count, e.g.:
        // assertEquals(technician.getPaper(), 100);  // Check if paper has been added correctly.
    }
}
