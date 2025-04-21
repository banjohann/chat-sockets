import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Message {

    private final String from;
    private final String destination;
    private final CommandType type;
    private final String content;
    private final String filePath;

    public static Message ofConnect(String name) {
        return new Message(name, "&", CommandType.CONNECT, "&", "&");
    }

    public static Message ofListUsers(String from) {
        return new Message(from, "&", CommandType.USERS, "&", "&");
    }

    public static Message ofSendMessage(String from, String destination, String content) {
        return new Message(from, destination, CommandType.MESSAGE, content, "&");
    }

    public static Message ofSendFile(String from, String destination, String filePath) {
        return new Message(from, destination, CommandType.FILE, "&", filePath);
    }

    public void write(DataOutputStream out) throws IOException {
        if (type == CommandType.FILE) {
            sendFile(out);
            return;
        }

        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        String header = String.format("%s:%s:%s:%d\n", from, type.name(), destination, contentBytes.length);
        out.write(header.getBytes(StandardCharsets.UTF_8));
        out.write(contentBytes);
        out.flush();
    }

    private void sendFile(DataOutputStream out) throws IOException {
        try {
            Path path = Paths.get("client/files", filePath).toAbsolutePath();
            byte[] fileBytes = Files.readAllBytes(path);
            String header = String.format("%s:%s:%s:%d:%s\n", from, type.name(), destination, fileBytes.length, filePath);
            out.write(header.getBytes(StandardCharsets.UTF_8));
            out.write(fileBytes);
            out.flush();
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado: " + filePath);
        }
    }

    private Message(String from, String destination, CommandType type, String content, String filePath) {
        this.from = from;
        this.destination = destination;
        this.type = type;
        this.content = content;
        this.filePath = filePath;
    }
}
