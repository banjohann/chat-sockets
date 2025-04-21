import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 8080);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Enter your name:");
            String name = new BufferedReader(new InputStreamReader(System.in)).readLine();

            out.println(name);
            out.flush();

            System.out.println(in.readLine());

            new Thread(() -> {//TODO: mover essa lógica para um novo arquivo, InboxListener.java
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Mensagem recebida:\n" + serverMessage);
                    }
                } catch (Exception e) {
                    System.err.println("Error receiving messages: " + e.getMessage());
                }
            }).start();

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

                if (command.startsWith("/send")) {
                    String user = command.split(" ", 3)[1];
                    String message = command.split(" ", 3)[2];
                    out.println("message " + user + " " + message);
                    out.flush();
                }

                if ("/users".equalsIgnoreCase(command)) {
                    out.println("users");
                    out.flush();

                    String response = in.readLine();
                    System.out.println("Users: " + response);
                }
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Erro ao se conectar com o servidor: " + e);
        }
    }
}