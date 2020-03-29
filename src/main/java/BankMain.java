//import Bank.BankServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class BankMain {

    public static void main(String[] args) throws Exception {

        Server server = new Server(7070);
        ServletContextHandler exampleHandler = new ServletContextHandler(server, "/example");
        exampleHandler.addServlet(ExampleServlet.class, "/");
        ServletContextHandler bankHandler = new ServletContextHandler(server, "/bank");
        bankHandler.addServlet(BankServlet.class, "/");
        server.start();
    }

}