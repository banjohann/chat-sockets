import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            server.start();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
