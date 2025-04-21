import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Command {
    private String from;
    private String destination;
    private byte[] contentBytes;
    private CommandType type;

    public Command() {
    }

    public Command withHeader(String header) {
        String[] parts = header.split(":", 4);
        this.from = parts[0];
        this.type = CommandType.valueOf(parts[1].toUpperCase());
        this.destination = parts[2];
        this.contentBytes = new byte[Integer.parseInt(parts[3])];

        return this;
    }

    public Command withContent(DataInputStream in) throws IOException {
        in.readFully(this.contentBytes);
        return this;
    }

    public Command(String from, String destination, byte[] contentBytes, CommandType type) {
        this.from = from;
        this.destination = destination;
        this.contentBytes = contentBytes;
        this.type = type;
    }

    public String getHeader() {
        return String.format("%s:%s:%s:%d\n", from, type.name(), destination, contentBytes.length);
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
