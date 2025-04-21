import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        String header = String.format("%s:%s:%s:%d\n", from, type.name(), destination, contentBytes.length);
        out.write(header.getBytes(StandardCharsets.UTF_8));
        out.write(contentBytes);
        out.flush();
    }

    private Message(String from, String destination, CommandType type, String content, String filePath) {
        this.from = from;
        this.destination = destination;
        this.type = type;
        this.content = content;
        this.filePath = filePath;
    }
}
