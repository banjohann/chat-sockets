import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private String clientName;
    private final Socket clientSocket;
    private final ConcurrentHashMap<String, Socket> clientSockets;

    public ClientHandler(Socket clientSocket, ConcurrentHashMap<String, Socket> clientSockets) {
        this.clientSocket = clientSocket;
        this.clientSockets = clientSockets;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
            String connectHeader = readLine(in);
            Command connectCmd = new Command().withHeader(connectHeader).withContent(in);
            clientName = connectCmd.getFrom();

            clientSockets.put(clientName, clientSocket);

            while (true) {
                String header = readLine(in);
                Command currentCmd = new Command().withHeader(header).withContent(in);
                CommandHandler.handleCommand(currentCmd, clientSockets);
            }

        } catch (Exception e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSockets.remove(clientName);
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private static String readLine(DataInputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte b;
        while ((b = in.readByte()) != '\n') {
            buffer.write(b);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}