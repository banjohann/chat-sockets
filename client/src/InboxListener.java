import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InboxListener implements Runnable {

    private final DataInputStream in;

    @Override
    public void run() {
        while (true) {
            try {
                String header = readLine(in);
                String[] headerParts = header.split(":");
                String from = headerParts[0];
                CommandType type = CommandType.valueOf(headerParts[1].toUpperCase());
                int contentLength = Integer.parseInt(headerParts[3]);
                byte[] contentBytes = new byte[contentLength];
                in.readFully(contentBytes);

                if (type == CommandType.ERROR) {
                    System.out.println("Recebido mensagem do servidor: ");
                    System.out.println(new String(contentBytes, StandardCharsets.UTF_8));
                    return;
                }

                if (type == CommandType.FILE) {
                    FileReceiver.receive(header, contentBytes);
                }

                if (type == CommandType.USERS) {
                    System.out.println("Usuários conectados: " + new String(contentBytes, StandardCharsets.UTF_8));
                    return;
                }

                if (type == CommandType.MESSAGE) {
                    String message = new String(contentBytes, StandardCharsets.UTF_8);
                    System.out.println("Mensagem de " + from + ": \n" + message);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public InboxListener(DataInputStream dataInputStream) {
        this.in = dataInputStream;
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
