package Bank;

public class BankAccount {
    private String firstName;
    private String lastName;
    private String address;
    private int balance;

    public BankAccount(String firstName, String lastName, String address, int balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getBalance() {
        return balance;
    }
}