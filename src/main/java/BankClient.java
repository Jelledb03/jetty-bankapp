import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class BankClient {

    public static void main(String[] args) throws IOException{

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Person Info Command Line Editor.");
        System.out.println("(PICLER for short.)");
        System.out.println("Do you want to get or set a person's info?");
        System.out.println("(Type 'get' or 'set' now.)");
        String getOrSet = scanner.nextLine();
        if("get".equalsIgnoreCase(getOrSet)){
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
        }
        else if("set".equalsIgnoreCase(getOrSet)){
            System.out.println("Whose info do you want to set?");
            System.out.println("(Type a person's name now.)");
            String name = scanner.nextLine();

            System.out.println("When was " + name + " born?");
            System.out.println("(Type a year now.)");
            String birthYear = scanner.nextLine();

            System.out.println("Can you tell me about " + name + "?");
            System.out.println("(Type a sentence now.)");
            String about = scanner.nextLine();

            setPersonData(name, birthYear, about);
        }

        scanner.close();

        System.out.println("Thanks for using PICLER.");

    }

    public static String getPersonData(String name) throws IOException{

        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:7070/bank/" + name).openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            StringBuilder response = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response.append(scanner.nextLine());
                response.append("\n");
            }
            scanner.close();

            return response.toString();
        }

        // an error happened
        return null;
    }

    public static void setPersonData(String name, String birthYear, String about) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8000/restwebapp_war_exploded/bank/" + name).openConnection();

        connection.setRequestMethod("POST");

        String postData = "name=" + URLEncoder.encode(name);
        postData += "&about=" + URLEncoder.encode(about);
        postData += "&birthYear=" + birthYear;

        connection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(postData);
        wr.flush();

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            System.out.println("POST was successful.");
        }
        else {
            System.out.println("POST failed.");
        }
    }
}