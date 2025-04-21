import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InboxListener implements Runnable {

    private final DataInputStream in;

    @Override
    public void run() {
        try {
            String header = readLine(in);
            String[] headerParts = header.split(":");
            String from = headerParts[0];
            CommandType type = CommandType.valueOf(headerParts[1].toUpperCase());
            int contentLength = Integer.parseInt(headerParts[3]);
            byte[] contentBytes = new byte[contentLength];
            in.readFully(contentBytes);

            if (type == CommandType.FILE) {
                System.out.println("Received a file from " + from);
                return;
            }

            if (type == CommandType.USERS) {
                System.out.println("Connected users: " + new String(contentBytes));
                return;
            }

            if (type == CommandType.MESSAGE) {
                String message = new String(contentBytes);
                System.out.println("Message from " + from + ": \n" + message);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
