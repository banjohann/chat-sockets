public class Command {
    private String requester;
    private String destination;
    private String content;
    private CommandType type;

    public static Command fromLine(String line, String requester) {
        String[] parts = line.split(" ", 3);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid command format.");
        }

        CommandType type = CommandType.valueOf(parts[0].toUpperCase());
        return new Command(requester, parts[1], parts.length > 2 ? parts[2] : "", type);
    }

    private Command(String requester, String destination, String content, CommandType type) {
        this.requester = requester;
        this.destination = destination;
        this.content = content;
        this.type = type;
    }

    public String getRequester() {
        return requester;
    }

    public String getDestination() {
        return destination;
    }

    public String getContent() {
        return content;
    }

    public CommandType getType() {
        return type;
    }
}
