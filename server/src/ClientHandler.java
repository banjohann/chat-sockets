import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Map<String, Socket> clientSockets;
    private String clientName;

    public ClientHandler(Socket clientSocket, Map<String, Socket> clientSockets) {
        this.clientSocket = clientSocket;
        this.clientSockets = clientSockets;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            clientName = in.readLine();
            System.out.println("Client connected: " + clientName);
            if (clientName == null || clientName.trim().isEmpty()) {
                out.println("Invalid name. Connection closing.");
                return;
            }

            out.println("name: " + clientName);

            clientSockets.put(clientName, clientSocket);

            String line;
            while ((line = in.readLine()) != null) {
                Command command = Command.fromLine(line, clientName);
                CommandHandler.handleCommand(command, clientSockets);
            }

            clientSockets.remove(clientName);
        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}