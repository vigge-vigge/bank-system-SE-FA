import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ATMTechnicianTest {

    private ATM atm;
    private ATMTechnician technician;

    @Before
    public void setUp() {
        // Create ATM with initial cash, ink, and paper levels
        atm = new ATM(100000.0, 100, 100);

        // Create an ATM technician
        technician = new ATMTechnician("Tech1", "4321");

        // Add technician to the ATM
        atm.addTechnician(technician);
    }

    @Test
    public void testTechnicianAddCash() {
        // Get initial cash available in ATM
        double initialCash = atm.getCashAvailable();

        // Add cash to the ATM by the technician
        double cashToAdd = 2000.0;
        atm.addCash(cashToAdd);

        // Get the updated cash available in ATM
        double updatedCash = atm.getCashAvailable();

        // Assert that the cash is updated correctly
        assertEquals("Cash should be added correctly to the ATM.",
                initialCash + cashToAdd, updatedCash, 0.01);
    }
}
