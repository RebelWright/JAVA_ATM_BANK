package ATM;

import java.util.Date;

public class Transaction {
    // Amount of this transaction
    private double amount;
    // Time and Date of this transaction
    private Date timestamp;
    // Memo for this transaction
    private String memo;
    // Account in which the transaction was performed
    private Account inAccount;

    // Create a new transaction
    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }
    // Create a new transaction
    public Transaction(double amount, String memo, Account inAccount){
        // call the 2-argument constructor first
        this(amount, inAccount);
        // set the memo
        this.memo = memo;
    }
    // get the amount of the transaction
    public double getAmount() {
        return this.amount;
    }
    // get a string summary of transaction
    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),this.amount, this.memo);
        }
    }

}