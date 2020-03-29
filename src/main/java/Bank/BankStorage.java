package Bank;

import java.util.HashMap;
import java.util.Map;

/**
 * Example DataStore class that provides access to user data.
 * Pretend this class accesses a database.
 */
public class BankStorage {

    //Map of names to Person instances.
    private Map<String, BankAccount> personMap = new HashMap<>();

    //this class is a singleton and should not be instantiated directly!
    private static BankStorage instance = new BankStorage();
    public static BankStorage getInstance(){
        return instance;
    }

    //private constructor so people know to use the getInstance() function instead
    private BankStorage(){
        //dummy data
        personMap.put("Joske", new BankAccount("Joske", "Vermeulen", "Trammezandlei 122, 2990 Schoten", 0));
        personMap.put("Jelle", new BankAccount("Jelle", "De Boeck", "Campus Groenenborger, 2610 Wilrijk", 50));
        personMap.put("Alice", new BankAccount("Alice", "Smith", "In een huis", 10000));
    }

    public BankAccount getPerson(String name) {
        return personMap.get(name);
    }

    public void putPerson(BankAccount bankAccount) {
        personMap.put(bankAccount.getFirstName(), bankAccount);
    }
}