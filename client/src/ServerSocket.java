import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSocket {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerSocket(String host, int port) {
        try {
            this.clientSocket = new Socket(host, port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Erro ao se conectar com o servidor: " + e);
        }
    }
}
