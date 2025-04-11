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

            out.write("name");
            out.flush();

            String resp = in.readLine();
            System.out.println(resp);

            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Erro ao se conectar com o servidor: " + e);
        }
    }
}