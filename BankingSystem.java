import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Class representing a bank account
class BankAccount {
    private String accountNumber; // Unique identifier for the account
    private double balance;       // Current balance in the account

    // Constructor to initialize the account
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for account balance
    public double getBalance() {
        return balance;
    }

    // Method to deposit funds into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount; // Add the amount to the balance
            System.out.println("Deposit successful. New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount."); // Handle invalid deposit
        }
    }

    // Method to withdraw funds from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount; // Deduct the amount from the balance
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds."); // Handle invalid withdrawal
        }
    }

    // Method to transfer funds to another account
    public void transfer(BankAccount targetAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);       // Withdraw from the source account
            targetAccount.deposit(amount); // Deposit into the target account
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Invalid transfer amount or insufficient funds."); // Handle invalid transfer
        }
    }
}

// Class representing a customer
class Customer {
    private String name; // Name of the customer
    private String pin;  // PIN for authentication
    private Map<String, BankAccount> accounts; // Map of accounts owned by the customer

    // Constructor to initialize the customer
    public Customer(String name, String pin) {
        this.name = name;
        this.pin = pin;
        this.accounts = new HashMap<>(); // Initialize the accounts map
    }

    // Getter for customer name
    public String getName() {
        return name;
    }

    // Getter for customer PIN
    public String getPin() {
        return pin;
    }

    // Method to add a bank account to the customer
    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account); // Add account to the map
    }

    // Method to retrieve a bank account by account number
    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Method to check the balance of a specific account
    public void checkBalance(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println("Balance for account " + accountNumber + ": " + account.getBalance());
        } else {
            System.out.println("Account not found."); // Handle invalid account number
        }
    }
}

// Class representing an ATM technician
class ATMTechnician {
    private String name; // Name of the technician
    private String pin;  // PIN for authentication

    // Constructor to initialize the technician
    public ATMTechnician(String name, String pin) {
        this.name = name;
        this.pin = pin;
    }

    // Getter for technician name
    public String getName() {
        return name;
    }

    // Getter for technician PIN
    public String getPin() {
        return pin;
    }
}

// Class representing an ATM
class ATM {
    private Map<String, Customer> customers;       // Map of registered customers
    private Map<String, ATMTechnician> technicians; // Map of registered technicians
    private double cashAvailable;                  // Amount of cash available in the ATM
    private int inkLevel;                          // Level of ink in the ATM
    private int paperLevel;                        // Level of printer paper in the ATM

    // Constructor to initialize the ATM
    public ATM(double cashAvailable, int inkLevel, int paperLevel) {
        this.customers = new HashMap<>();
        this.technicians = new HashMap<>();
        this.cashAvailable = cashAvailable;
        this.inkLevel = inkLevel;
        this.paperLevel = paperLevel;
    }

    // Method to add a customer to the ATM
    public void addCustomer(Customer customer) {
        customers.put(customer.getName(), customer); // Add customer to the map
    }

    // Method to add a technician to the ATM
    public void addTechnician(ATMTechnician technician) {
        technicians.put(technician.getName(), technician); // Add technician to the map
    }

    // Method for customer login
    public void customerLogin(String name, String pin) {
        Customer customer = customers.get(name); // Retrieve customer by name
        if (customer != null && customer.getPin().equals(pin)) { // Validate name and PIN
            System.out.println("Customer login successful. Welcome, " + customer.getName() + "!");
            customerMenu(customer); // Display customer menu
        } else {
            System.out.println("Invalid customer name or PIN."); // Handle invalid login
        }
    }

    // Method for technician login
    public void technicianLogin(String name, String pin) {
        ATMTechnician technician = technicians.get(name); // Retrieve technician by name
        if (technician != null && technician.getPin().equals(pin)) { // Validate name and PIN
            System.out.println("Technician login successful. Welcome, " + technician.getName() + "!");
            technicianMenu(); // Display technician menu
        } else {
            System.out.println("Invalid technician name or PIN."); // Handle invalid login
        }
    }

    // Method to display the customer menu
    private void customerMenu(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Cash");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accNumber = scanner.next();
                    customer.checkBalance(accNumber); // Check account balance
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accNumber = scanner.next();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    BankAccount depositAccount = customer.getAccount(accNumber);
                    if (depositAccount != null) {
                        depositAccount.deposit(depositAmount); // Deposit funds
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    accNumber = scanner.next();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (withdrawAmount > cashAvailable) {
                        System.out.println("ATM does not have enough cash."); // Handle insufficient cash
                    } else {
                        BankAccount withdrawAccount = customer.getAccount(accNumber);
                        if (withdrawAccount != null) {
                            withdrawAccount.withdraw(withdrawAmount); // Withdraw funds
                            cashAvailable -= withdrawAmount; // Update ATM cash
                        } else {
                            System.out.println("Account not found.");
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter source account number: ");
                    String sourceAccNumber = scanner.next();
                    System.out.print("Enter target account number: ");
                    String targetAccNumber = scanner.next();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    BankAccount sourceAccount = customer.getAccount(sourceAccNumber);
                    BankAccount targetAccount = customer.getAccount(targetAccNumber);
                    if (sourceAccount != null && targetAccount != null) {
                        sourceAccount.transfer(targetAccount, transferAmount); // Transfer funds
                    } else {
                        System.out.println("One or both accounts not found.");
                    }
                    break;
                case 5:
                    running = false;
                    System.out.println("Logging out. Goodbye, " + customer.getName() + "!"); // Logout
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid choice
            }
        }
    }

    // Method to display the technician menu
    private void technicianMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nTechnician Menu:");
            System.out.println("1. Add Cash");
            System.out.println("2. Add Ink");
            System.out.println("3. Add Printer Paper");
            System.out.println("4. Check ATM Status");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount of cash to add: ");
                    double cashToAdd = scanner.nextDouble();
                    cashAvailable += cashToAdd; // Add cash to the ATM
                    System.out.println("Cash added. Total cash available: " + cashAvailable);
                    break;
                case 2:
                    System.out.print("Enter amount of ink to add: ");
                    int inkToAdd = scanner.nextInt();
                    inkLevel += inkToAdd; // Add ink to the ATM
                    System.out.println("Ink added. Total ink level: " + inkLevel);
                    break;
                case 3:
                    System.out.print("Enter amount of printer paper to add: ");
                    int paperToAdd = scanner.nextInt();
                    paperLevel += paperToAdd; // Add printer paper to the ATM
                    System.out.println("Printer paper added. Total paper level: " + paperLevel);
                    break;
                case 4:
                    System.out.println("ATM Status:");
                    System.out.println("Cash Available: " + cashAvailable);
                    System.out.println("Ink Level: " + inkLevel);
                    System.out.println("Printer Paper Level: " + paperLevel); // Display ATM status
                    break;
                case 5:
                    running = false;
                    System.out.println("Logging out. Goodbye!"); // Logout
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid choice
            }
        }
    }
}

// Main class to run the banking system
public class BankingSystem {
    public static void main(String[] args) {
        // Create ATM with initial cash, ink, and paper levels
        ATM atm = new ATM(100000.0, 100, 100);

        // Create customers
        Customer customer1 = new Customer("John", "1234");
        customer1.addAccount(new BankAccount("123456789", 1000.0));
        customer1.addAccount(new BankAccount("987654321", 500.0));

        Customer customer2 = new Customer("Jane", "5678");
        customer2.addAccount(new BankAccount("111111111", 2000.0));

        // Add customers to ATM
        atm.addCustomer(customer1);
        atm.addCustomer(customer2);

        // Create ATM technician
        ATMTechnician technician = new ATMTechnician("Tech1", "4321");

        // Add technician to ATM
        atm.addTechnician(technician);

        // Login system
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to the ATM!");
            System.out.println("1. Customer Login");
            System.out.println("2. ATM Technician Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter your PIN: ");
                    String customerPin = scanner.nextLine();
                    atm.customerLogin(customerName, customerPin); // Handle customer login
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String techName = scanner.nextLine();
                    System.out.print("Enter your PIN: ");
                    String techPin = scanner.nextLine();
                    atm.technicianLogin(techName, techPin); // Handle technician login
                    break;
                case 3:
                    System.out.println("Exiting. Goodbye!"); // Exit the program
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid choice
            }
        }
    }
}