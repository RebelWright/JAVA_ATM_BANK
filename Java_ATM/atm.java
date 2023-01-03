package ATM;
import java.util.Scanner;
public class ATM {

    public static void main(String[] args) {
        // initialize Scanner
        Scanner sc = new Scanner(System.in);
        // initialize Bank
        Bank theBank = new Bank("The Bank of Wright");
        // add a user, which also creates a savings account
        User aUser = theBank.addUser("John", "Doe", "1234");
        // also add a checking account for the user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            // stay in login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);
            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);

        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        // inits
        String userID;
        String pin;
        User authUser;
        // prompt the user for user ID/pin combo until reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter User ID: ");
            userID = sc.nextLine();
            System.out.print("Enter User Pin: ");
            pin = sc.nextLine();
            // try to get user object that corresponds with the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect User ID/pin combination. " +
                        "Please try again. ");
            }
        } while (authUser == null); // continue looping until successful login
        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {
        // print a summary of the user's accounts
        theUser.printAccountsSummary();
        // init
        int choice;
        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?", theUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Choose 1-5");

            }
        } while (choice < 1 || choice > 5);
        // process the choice
        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
        }
        // redisplay this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }
    // show the transaction history of the account
    public static void showTransactionHistory(User theUser, Scanner sc) {
        int theAcct;
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you wish to view: ",
                    theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());
        // print the transaction history
        theUser.printAcctTransactionHistory(theAcct);
    }
    // process transfer of funds
    public static void transferFunds(User theUser, Scanner sc) {
        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        //get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0 ) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n"+"balance of $%.02f.\n",acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        // finally, do the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }
    // process a fund withdraw
    public static void withdrawFunds(User theUser, Scanner sc) {
        // inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;
        //get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0 ) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n"+"balance of $%.02f.\n",acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        // gobble up the rest of the input line
        sc.nextLine();
        // get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        // do the withdrawal
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }
    // process an account deposit
    public static void depositFunds(User theUser, Scanner sc) {
        // inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;
        //get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0 ) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n"+"balance of $%.02f.\n",acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        // gobble up the rest of the input line
        sc.nextLine();
        // get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        // do the withdrawal
        theUser.addAcctTransaction(toAcct, amount, memo);
    }

}