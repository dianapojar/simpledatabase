package db.commands.parser;

import db.commands.CommandType;
import db.commands.impl.*;

public class SimpleCommandParser implements ICommandParser {
    public static final String SEPARATOR = " ";

    @Override
    public Command getCommand(String rawCommand) {
        CommandType basicCommand = CommandType.getCommandFromType(rawCommand.trim());
        //TODO: use just one getCommandFromType
        if (basicCommand != null) {
            return createSimpleCommand(basicCommand);
        } else {
            Integer spacePos = rawCommand.indexOf(SEPARATOR);
            if (spacePos == -1) {
                return new InvalidCommand("The inserted command does not exist!");
            }

            String type = rawCommand.substring(0, spacePos);
            CommandType complexCommand = CommandType.getCommandFromType(type);
            if (complexCommand == null) {
                return new InvalidCommand("The inserted command does not exist!");
            }
            String arguments = rawCommand. substring(spacePos + 1);
            return createCommandByType(complexCommand, arguments);
        }
    }

    private Command createSimpleCommand(CommandType basicCommand) {
        switch (basicCommand) {
            case END:
                return new EndCommand();
            case BEGIN:
                return new BeginCommand();
            case COMMIT:
                return new CommitCommand();
            case ROLLBACK:
                return new RollbackCommand();
            default:
                return new InvalidCommand("Parameters missing for the command");
        }
    }

    private Command createCommandByType(CommandType type, String arguments) {
        //TODO: send directly string to command
        String[] args = arguments.trim().split(" ");
        switch (type) {
            case SET:
                if (args.length == 2) {
                    return new SetCommand(args[0], args[1]);
                }
                break;
            case GET:
                if (args.length == 1) {
                    return new GetCommand(args[0]);
                }
                break;
            case NUMEQUALTO:
                if (args.length == 1) {
                    return new NumEqualToCommand(args[0]);
                }
                break;
            case UNSET:
                if (args.length == 1) {
                    return new UnsetCommand(args[0]);
                }
                break;
        }
        return new InvalidCommand("Invalid number of arguments");
    }
}
