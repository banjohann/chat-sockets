public class Command {
    private final String requester;
    private final String destination;
    private String content;
    private final CommandType type;

    public static Command fromLine(String line, String requester) {
        String[] parts = line.split(" ", 3);

        CommandType type = CommandType.valueOf(parts[0].toUpperCase());
        if (CommandType.USERS.equals(type)) {
            return new Command(requester, "", type);
        }

        return new Command(requester, parts[1], parts.length > 2 ? parts[2] : "", type);
    }

    private Command(String requester, String destination, CommandType type) {
        this.requester = requester;
        this.destination = destination;
        this.type = type;
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
