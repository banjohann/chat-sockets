public enum CommandType {
    CONNECT,
    FILE,
    MESSAGE,
    USERS;

    public static CommandType convert(String type) {
        return CommandType.valueOf(type.replace("/", "").toUpperCase());
    }
}
