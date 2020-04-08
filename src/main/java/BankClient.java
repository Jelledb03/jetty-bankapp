import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class BankClient {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("Exit")) {
            System.out.println("Do you want to get or alter a person's info?");
            System.out.println("(Type 'get' or 'alter' now (Exit to close program)");
            input = scanner.nextLine();
            if ("get".equalsIgnoreCase(input)) {
                System.out.println("Whose info do you want to get?");
                System.out.println("(Type a person's name now.)");
                String name = scanner.nextLine();

                String jsonString = getPersonData(name);
                JSONObject jsonObject = null;
                try {
                    assert jsonString != null;
                    jsonObject = new JSONObject(jsonString);
                    String lastName = jsonObject.getString("lastName");
                    System.out.println(name + " has last name " + lastName);

                    String address = jsonObject.getString("address");
                    System.out.println(name + " lives at " + address);

                    int balance = jsonObject.getInt("balance");
                    System.out.println(name + " has " + balance + " euro in his account");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if ("alter".equalsIgnoreCase(input)) {
                System.out.println("Whose info do you want to set?");
                System.out.println("(Type a person's name now.)");
                String firstName = scanner.nextLine();

                System.out.println("What is " + firstName + "'s last name?");
                System.out.println("(Type a person's last name now.)");
                String lastName = scanner.nextLine();

                System.out.println("Can you tell me about " + firstName + " address?");
                System.out.println("(Type an address now.)");
                String address = scanner.nextLine();

                System.out.println("Can you tell me about " + firstName + " balance?");
                System.out.println("(Type a balance now.)");
                int balance = scanner.nextInt();

                setPersonData(firstName, lastName, address ,balance);
            }
        }
        scanner.close();
    }

    public static String getPersonData(String name) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:7070/bank/" + name).openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            StringBuilder response = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
                response.append("\n");
            }
            scanner.close();

            return response.toString();
        }
        // an error happened
        return null;
    }

    public static void setPersonData(String firstName, String lastName, String address, int balance) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:7070/bank/" + firstName).openConnection();

        connection.setRequestMethod("POST");

        //Nog aanpassen naar de juiste variabele and so on
        String postData = "firstName=" + URLEncoder.encode(firstName);
        postData += "&lastName=" + URLEncoder.encode(lastName);
        postData += "&address=" + address;
        postData += "&balance=" + balance;

        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            System.out.println("POST was successful.");
        } else {
            System.out.println("POST failed.");
        }
    }
}