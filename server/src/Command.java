import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Command {
    private String from;
    private String destination;
    private String filePath;
    private byte[] contentBytes;
    private CommandType type;

    public Command() {
    }

    public Command withHeader(String header) {
        String[] parts = header.split(":", 5);
        this.from = parts[0];
        this.type = CommandType.valueOf(parts[1].toUpperCase());
        this.destination = parts[2];
        this.contentBytes = new byte[Integer.parseInt(parts[3])];

        if (CommandType.FILE.equals(this.type)) {
            this.filePath = parts[4];
        }

        return this;
    }

    public Command withContent(DataInputStream in) throws IOException {
        in.readFully(this.contentBytes);
        return this;
    }

    public String getHeader() {
        return String.format("%s:%s:%s:%d:%s\n", from, type.name(), destination, contentBytes.length, filePath);
    }

    public static Command ofError(String destination, String message) {
        return new Command("SERVER", destination, CommandType.ERROR, message.getBytes(StandardCharsets.UTF_8));
    }

    private Command(String from, String destination, CommandType commandType, byte[] contentBytes) {
        this.from = from;
        this.destination = destination;
        this.type = commandType;
        this.contentBytes = contentBytes;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setContent(String content) {
        this.contentBytes = content.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getContentBytes() {
        return contentBytes;
    }

    public String getFrom() {
        return from;
    }

    public String getDestination() {
        return destination;
    }

    public String getContent() {
        if (CommandType.MESSAGE.equals(type)) {
            return new String(contentBytes);
        }

        return "Não serializável";
    }

    public CommandType getType() {
        return type;
    }
}
