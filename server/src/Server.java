import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private final ServerSocket serverSocket;
    private final Map<String, Socket> clientSockets = new ConcurrentHashMap<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        while (true) {
            try {
                Socket newClient = serverSocket.accept();
                new Thread(new ClientHandler(newClient, clientSockets)).start();
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }
}
