import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BankingAccountTest {

    private ATM atm;
    private Customer customer;

    @Before
    public void setUp() {
        // Create ATM with initial cash, ink, and paper levels
        atm = new ATM(100000.0, 100, 100);

        // Create a customer and an account
        customer = new Customer("John", "1234");
        customer.addAccount(new BankAccount("123456789", 1000.0));

        // Add customer to the ATM
        atm.addCustomer(customer);
    }

    @Test
    public void testCustomerDeposit() {
        BankAccount account = customer.getAccount("123456789");

        // Deposit funds into the account
        double initialBalance = account.getBalance();
        account.deposit(500.0);  // Deposit 500
        double updatedBalance = account.getBalance();

        // Assert that the balance is updated correctly
        assertEquals("Deposit should be successful, and balance should increase by 500.",
                initialBalance + 500.0, updatedBalance, 0.01);
    }
}





