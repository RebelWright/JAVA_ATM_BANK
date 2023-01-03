package ATM;

import java.util.ArrayList;

public class Account {
    // Name of the account
    private String name;
    // Account ID number
    private String uuid;
    // User object that owns this account
    private User holder;
    // List of transactions for this account
    private ArrayList<Transaction> transactions;
    // Constructor to create a new Account
    public Account(String name, User holder, Bank theBank){
        // set the account name and holder
        this.name = name;
        this.holder = holder;
        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();
        // initialize transactions
        this.transactions = new ArrayList<Transaction>();
    }
    // get the account ID
    public String getUUID() {
        return this.uuid;
    }
    // get summary line for the account
    public String getSummaryLine() {
        // get the account balance
        double balance = this.getBalance();
        // format summary line depending on if the balance is negative
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance,
                    this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance,
                    this.name);
        }
    }
    // get the balance of the accounts by adding the amounts of transactions
    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }
    // print transaction history of the account
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();

    }
    public void addTransaction(double amount, String memo) {
        // create a new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}