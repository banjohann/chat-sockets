public enum CommandType {
    CONNECT,
    ERROR,
    FILE,
    MESSAGE,
    USERS;

    public static CommandType convert(String type) {
        return CommandType.valueOf(type.toUpperCase());
    }
}
