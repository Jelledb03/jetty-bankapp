import Bank.BankAccount;
import Bank.BankStorage;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/BankServlet")
public class BankServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestUrl = request.getRequestURI();
        String name = requestUrl.substring("/bank/".length());
        BankAccount bankAccount = BankStorage.getInstance().getPerson(name);

        if(bankAccount != null){
            String json = "{\n";
            json += "\"name\": " + JSONObject.quote(bankAccount.getFirstName()) + ",\n";
            json += "\"lastName\": " + JSONObject.quote(bankAccount.getLastName()) + ",\n";
            json += "\"address\": " + JSONObject.quote(bankAccount.getAddress()) + ",\n";
            json += "\"balance\": " + bankAccount.getBalance() + "\n";
            json += "}";
            response.getOutputStream().println(json);
        }
        else{
            //That person wasn't found, so return an empty JSON object. We could also return an error.
            response.getOutputStream().println("No Bankaccount found for: " + name);
        }
    }



    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String name = request.getParameter("name");
        String firstName = request.getParameter("firstName");
        String address = request.getParameter("Address");
        int balance = Integer.parseInt(request.getParameter("balance"));

        BankStorage.getInstance().putPerson(new BankAccount(name, firstName, address, balance));
    }
}
