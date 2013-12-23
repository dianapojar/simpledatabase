package db.commands;

public enum CommandType {
    SET("set"),
    UNSET("unset"),
    GET("get"),
    NUMEQUALTO("numequalto"),
    END("end"),
    BEGIN("begin"),
    COMMIT("commit"),
    ROLLBACK("rollback"),
    INVALID("invalid");

    private String command;

    private CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType getCommandFromType(String type) {
        for (CommandType validCommand : CommandType.values()) {
            if (validCommand.getCommand().equals(type)) {
                return validCommand;
            }
        }

        return null;
    }
}
