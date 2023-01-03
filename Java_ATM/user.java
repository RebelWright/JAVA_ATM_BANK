package ATM;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;
public class User {
    // First name of user
    private String firstName;
    // Last name of user
    private String lastName;
    // ID number of user
    private String uuid;
    // MD5 Hash of the user's pin number
    private byte pinHash[];
    // List of accounts for user
    private ArrayList<Account> accounts;
    // Constructor to create a new user
    public User(String firstName, String lastName, String pin, Bank theBank) {
        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;
        /* store the pin's MD5 hash instead of original
           value, for security reasons */
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        // get a new, unique universal id for the user
        this.uuid = theBank.getNewUserUUID();
        // create empty list of accounts
        this.accounts = new ArrayList<Account>();
        // print log message
        System.out.printf("New User %s, %s with ID %s created. \n", lastName,
                firstName, this.uuid);
    }
    // Add an account for the user
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }
    // return the User's UUID
    public String getUUID() {
        return this.uuid;
    }
    // Check whether a given pin matches the true User pin
    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
    // return the User's first name
    public String getFirstName() {
        return this.firstName;
    }
    // print summaries for user accounts
    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n", a + 1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    // return the number of accounts for the user
    public int numAccounts() {
        return this.accounts.size();
    }
    // return account transaction history
    public void printAcctTransactionHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }
    // get the balance of this account
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }
    // get the UUID of a particular account
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }
    // add a transaction to an account
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}