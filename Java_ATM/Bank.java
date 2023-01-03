package ATM;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    // Bank's name
    private String name;
    // Array of users
    private ArrayList<User> users;
    // Array of users' accounts
    private ArrayList<Account> accounts;

    // Create a new bank object with empty user and accounts lists
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    // generate a new UUID for user
    public String getNewUserUUID(){
        // inits
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        // continue looping until unique
        do {
            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            // check to make sure number is unique
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }
    // generate a new UUID for account
    public String getNewAccountUUID(){
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        // continue looping until unique
        do {
            // generate the number
            uuid = "";
            for (int c = 0; c < len; c++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            // check to make sure number is unique
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;

    }
    // add an Account
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }
    public User addUser(String firstName, String lastName, String pin) {
        // create a new User object
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        // create a savings account for the user and add to User and Bank accounts lists
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }
    // Get User object assoc. with a particular userID and pin, if valid
    public User userLogin(String userID, String pin) {
        // search through list of users
        for (User u : this.users) {
            // check if UUID is correct
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        // if we haven't found the user or have an incorrect pin
        return null;
    }
    // method to get the name of the bank
    public String getName() {
        return this.name;
    }
}