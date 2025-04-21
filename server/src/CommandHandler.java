import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class CommandHandler {

    public static void handleCommand(Command command, Map<String, Socket> clientSockets) {
        switch (command.getType()) {
            case MESSAGE:
                handleSendMessage(command, clientSockets);
                break;
            case USERS:
                handleListUsers(command.getRequester(), clientSockets);
                break;
            case FILE:
                handleSendFile(command, clientSockets);
                break;
            default:
                System.err.println("Invalid command: " + command);
        }
    }

    private static void handleSendMessage(Command command, Map<String, Socket> clientSockets) {
        if (command.getContent().isEmpty()) {
            System.err.println("Invalid MESSAGE command format.");
            return;
        }

        Socket destination = clientSockets.get(command.getDestination());
        if (destination == null) {
            destination = clientSockets.get(command.getRequester());
            String message = "User " + command.getDestination() + " not found.";

            sendMessageToClient(message, destination);
            return;
        }

        sendMessageToClient(command.getContent(), destination);
    }

    private static void handleSendFile(Command command, Map<String, Socket> clientSockets) {
        if (command.getContent().isEmpty()) {
            System.err.println("Invalid FILE command format.");
            return;
        }

        Socket destination = clientSockets.get(command.getDestination());
        if (destination == null) {
            destination = clientSockets.get(command.getRequester());
            String message = "User " + command.getDestination() + " not found.";

            sendMessageToClient(message, destination);
            return;
        }

        sendMessageToClient(command.getContent(), destination);
    }

    private static void handleListUsers(String requester, Map<String, Socket> clientSockets) {
        Set<String> users = clientSockets.keySet();
        users.remove(requester);

        String message = "Nenhum usuário conectado!";

        if (!users.isEmpty()) {
            message = String.join(", ", clientSockets.keySet());
        }

        Socket destination = clientSockets.get(requester);
        sendMessageToClient(message, destination);
    }

    private static void sendMessageToClient(String message, Socket clientSocket) {
        if (clientSocket == null) return;

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}