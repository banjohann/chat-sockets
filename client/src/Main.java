import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 8080);
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());

            System.out.println("Enter your name:");
            String name = new BufferedReader(new InputStreamReader(System.in)).readLine();

            Message.ofConnect(name).write(out);

            // Inicia thread que recebe mensagens
            new Thread(new InboxListener(in)).start();

            System.out.println("""
                    Comandos disponíveis:
                     /send message <user> <message> -> Para enviar uma mensagem para um usuário
                     /send file <user> <message> -> Para enviar um arquivo para um usuário
                     /users -> Para listar os usuários conectados
                     /quit -> Para sair da aplicação
                     /help -> Para listar os comandos disponíveis
                    """);
            while (true) {
                String command = new BufferedReader(new InputStreamReader(System.in)).readLine();

                if (command.startsWith("/send message")) {
                    String parts[] = command.split(" ", 4);
                    String user = parts[2];
                    String message = parts[3];
                    Message.ofSendMessage(name, user, message).write(out);
                }

                if ("/users".equalsIgnoreCase(command)) {
                    Message.ofListUsers(name).write(out);
                }

                if ("/quit".equalsIgnoreCase(command)) {
                    System.out.println("Saindo...");
                    break;
                }

                if ("/help".equalsIgnoreCase(command)) {
                    System.out.println("""
                    Comandos disponíveis:
                     /send message <user> <message> -> Para enviar uma mensagem para um usuário
                     /send file <user> <message> -> Para enviar um arquivo para um usuário
                     /users -> Para listar os usuários conectados
                     /quit -> Para sair da aplicação
                     /help -> Para listar os comandos disponíveis
                    """);
                }
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}