import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CommandHandler {

    public static void handleCommand(Command command, ConcurrentHashMap<String, Socket> clientSockets) {
        switch (command.getType()) {
            case MESSAGE:
                handleSendMessage(command, clientSockets);
                break;
            case USERS:
                handleListUsers(command, clientSockets);
                break;
            case FILE:
                handleSendFile(command, clientSockets);
                break;
            default:
                System.err.println("Invalid command: " + command);
        }
    }

    private static void handleSendMessage(Command command, ConcurrentHashMap<String, Socket> clientSockets) {
        if (command.getContent().isEmpty()) {
            System.err.println("Invalid MESSAGE command format.");
            return;
        }

        Socket destination = clientSockets.get(command.getDestination());
        if (destination == null) {
            destination = clientSockets.get(command.getFrom());
            String message = "Usuário" + command.getDestination() + " não encontrado.";

            command.setContent(message);
            command.setFrom(command.getFrom());

            sendMessageToClient(command, destination);
            return;
        }

        sendMessageToClient(command, destination);
    }

    private static void handleSendFile(Command command, ConcurrentHashMap<String, Socket> clientSockets) {

    }

    private static void handleListUsers(Command command, ConcurrentHashMap<String, Socket> clientSockets) {
        Set<String> users = clientSockets.keySet();
        String message = "Nenhum usuário conectado!";

        if (!users.isEmpty()) {
            message = String.join(", ", clientSockets.keySet());
        }

        command.setContent(message);
        Socket destination = clientSockets.get(command.getFrom());
        sendMessageToClient(command, destination);
    }

    private static void sendMessageToClient(Command command, Socket clientSocket) {
        if (clientSocket == null) return;

        try {
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            out.write(command.getHeader().getBytes(StandardCharsets.UTF_8));
            out.write(command.getContentBytes());
            out.flush();
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}