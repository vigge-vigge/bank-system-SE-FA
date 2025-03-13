package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ATMTest {

    private String customerBalanceFilePath = "customer_balance.txt";
    private String atmStatusFilePath = "atm_status.txt";
    private String transferHistoryFilePath = "transfer_history.txt";

    @Before
    public void setUp() throws IOException {
        // Create default customer balance file
        createDefaultCustomerFile();

        // Create default ATM status file
        createDefaultATMStatusFile();

        // Delete transfer history file to ensure a clean state
        File transferHistoryFile = new File(transferHistoryFilePath);
        if (transferHistoryFile.exists()) {
            transferHistoryFile.delete();
        }
    }

    @After
    public void tearDown() {
        // Clean up files after each test
        deleteFile(customerBalanceFilePath);
        deleteFile(atmStatusFilePath);
        deleteFile(transferHistoryFilePath);
    }

    private void createDefaultCustomerFile() throws IOException {
        File file = new File(customerBalanceFilePath);
        if (!file.exists()) {
            Files.write(Paths.get(customerBalanceFilePath),
                    ("123456,Alice,1000.0,1234\n" +
                            "987654,Bob,500.0,5678").getBytes());
        }
    }

    private void createDefaultATMStatusFile() throws IOException {
        File file = new File(atmStatusFilePath);
        if (!file.exists()) {
            Files.write(Paths.get(atmStatusFilePath), "password123,1000.0,50.0,500.0".getBytes());
        }
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    // Unit Test 1: Test Transfer Functionality
    @Test
    public void testTransferFunds() throws Exception {
        // Create sender and recipient customers
        Customer sender = new Customer("Alice", "123456", 1000.0, "1234");
        Customer recipient = new Customer("Bob", "987654", 500.0, "5678");

        // Perform a transfer
        double amount = 200.0;
        sender.transferFunds(recipient, amount);

        // Verify balances
        assertEquals(800.0, sender.getBalance(), 0.01); // Sender balance reduced by 200
        assertEquals(700.0, recipient.getBalance(), 0.01); // Recipient balance increased by 200

        // Verify the transfer log was written
        File file = new File(transferHistoryFilePath);
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(Paths.get(transferHistoryFilePath)));
        assertTrue(content.contains("Sender: 123456 (Alice) -> Recipient: 987654 (Bob) Amount: 200.0"));
    }

    // Unit Test 2: Test File Creation and Logging
    @Test
    public void testFileCreationAndLogging() throws Exception {
        // Ensure the file does not exist initially
        File file = new File(transferHistoryFilePath);
        assertFalse(file.exists());

        // Create sender and recipient customers
        Customer sender = new Customer("Alice", "123456", 1000.0, "1234");
        Customer recipient = new Customer("Bob", "987654", 500.0, "5678");

        // Perform a transfer to trigger file creation and logging
        sender.transferFunds(recipient, 300.0);

        // Verify the file was created
        assertTrue(file.exists());

        // Read the file content and verify the log
        String content = new String(Files.readAllBytes(Paths.get(transferHistoryFilePath)));
        assertTrue(content.contains("Sender: 123456 (Alice) -> Recipient: 987654 (Bob) Amount: 300.0"));

        // Perform another transfer to ensure logs are appended
        sender.transferFunds(recipient, 100.0);

        // Read the file content again and verify both logs are present
        content = new String(Files.readAllBytes(Paths.get(transferHistoryFilePath)));
        assertTrue(content.contains("Sender: 123456 (Alice) -> Recipient: 987654 (Bob) Amount: 300.0"));
        assertTrue(content.contains("Sender: 123456 (Alice) -> Recipient: 987654 (Bob) Amount: 100.0"));
    }

    // Integration Test 1: Test Customer Login and Transfer via ATM
    @Test
    public void testCustomerLoginAndTransferViaATM() throws Exception {
        // Load technician and customer data
        ATMTechnician technician = ATMTechnician.loadTechnician();
        Customer sender = Customer.loadCustomer("1234", false); // Alice logs in with PIN
        Customer recipient = Customer.loadCustomer("987654", true); // Bob loaded by account number

        assertNotNull(sender); // Ensure sender is loaded successfully
        assertNotNull(recipient); // Ensure recipient is loaded successfully

        // Initialize ATM with the logged-in customer and technician
        ATM atm = new ATM(sender, technician);

        // Perform a transfer
        double initialSenderBalance = sender.getBalance();
        double initialRecipientBalance = recipient.getBalance();
        double transferAmount = 200.0;

        sender.transferFunds(recipient, transferAmount);

        // Verify balances are updated
        assertEquals(initialSenderBalance - transferAmount, sender.getBalance(), 0.01);
        assertEquals(initialRecipientBalance + transferAmount, recipient.getBalance(), 0.01);

        // Verify the transfer log is written to the file
        File transferHistoryFile = new File(transferHistoryFilePath);
        assertTrue(transferHistoryFile.exists());

        String content = new String(Files.readAllBytes(Paths.get(transferHistoryFilePath)));
        assertTrue(content.contains("Sender: 123456 (Alice) -> Recipient: 987654 (Bob) Amount: 200.0"));
    }

    // Integration Test 2: Test Technician Login and ATM Status Update
    @Test
    public void testTechnicianLoginAndUpdateATMStatus() throws Exception {
        // Load technician
        ATMTechnician technician = ATMTechnician.loadTechnician();
        assertNotNull(technician); // Ensure technician is loaded successfully

        // Simulate technician login with correct password
        boolean isValidPassword = technician.validatePassword("password123");
        assertTrue(isValidPassword);

        // Add cash, ink, and paper
        double initialCash = technician.getCash();
        double initialInk = technician.getInk();
        double initialPaper = technician.getPaper();

        double addedCash = 500.0;
        double addedInk = 10.0;
        double addedPaper = 200.0;

        technician.addCash(addedCash);
        technician.addInk(addedInk);
        technician.addPaper(addedPaper);

        // Verify updated ATM status
        assertEquals(initialCash + addedCash, technician.getCash(), 0.01);
        assertEquals(initialInk + addedInk, technician.getInk(), 0.01);
        assertEquals(initialPaper + addedPaper, technician.getPaper(), 0.01);

        // Verify the updated status is persisted in the file
        File atmStatusFile = new File(atmStatusFilePath);
        assertTrue(atmStatusFile.exists());

        String content = new String(Files.readAllBytes(Paths.get(atmStatusFilePath)));
        assertTrue(content.contains("password123," + (initialCash + addedCash) + "," +
                (initialInk + addedInk) + "," + (initialPaper + addedPaper)));
    }
}