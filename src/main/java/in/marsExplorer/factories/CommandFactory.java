package in.marsExplorer.factories;

import in.marsExplorer.exceptions.InvalidCommandException;
import in.marsExplorer.lists.CommandTypes;
import in.marsExplorer.models.AbstractPosition;
import in.marsExplorer.models.commands.*;


public class CommandFactory {
    public static AbstractCommand getCommandObject(CommandTypes type, AbstractPosition target)
    {
        switch (type)
        {
            case BLOCK:
                return new Block(target);
            case PLACE:
                return new Place(target);
            case EXPLORER:
                return new Explorer(target);
            case REPORT:
                return new Report();
            default:
                return null;
        }
    }

    public static AbstractCommand getCommandObject(CommandTypes type, String str) throws InvalidCommandException
    {

        switch (type)
        {
            case BLOCK:
                return new Block(str);
            case PLACE:
                return new Place(str);
            case EXPLORER:
                return new Explorer(str);
            case REPORT:
                return new Report(str);
            default:
                return null;
        }
    }
}
