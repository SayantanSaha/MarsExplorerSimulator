package in.marsExplorer.models.commands;

import in.marsExplorer.exceptions.AreaNotInitializedException;
import in.marsExplorer.exceptions.InvalidCommandException;
import in.marsExplorer.exceptions.InvalidPositionException;
import in.marsExplorer.factories.PositionFactory;
import in.marsExplorer.lists.CommandTypes;
import in.marsExplorer.lists.PieceTypes;
import in.marsExplorer.lists.PositionTypes;
import in.marsExplorer.models.AbstractMovableArea;
import in.marsExplorer.models.AbstractPosition;

import java.util.Arrays;
import java.util.logging.Logger;

public class Block extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public Block (AbstractPosition position)
    {
        this.setTarget(position);
        this.setType(CommandTypes.BLOCK);
    }
    public Block (String s) throws InvalidCommandException
    {
        String[] commandArr = s.split(" ");
        if(CommandTypes.BLOCK == CommandTypes.valueOf(commandArr[0]))
        {
            if(commandArr.length!=2)
            {
                LOG.warning(s+" is not a valid command. Ignoring it.");
                throw new InvalidCommandException("BLOCK command format must be BLOCK X,Y");
            }
            else {
                String[] position = commandArr[1].split(",");
                if(position.length!=2)
                {
                    LOG.warning(s+" is not a valid command. Ignoring it.");
                    throw new InvalidCommandException("BLOCK command format must be BLOCK X,Y");
                }
                else
                {
                    Integer[] intPositions = Arrays.stream(position)
                            .map(Integer::parseInt)
                            .toArray(Integer[]::new);
                    AbstractPosition p = PositionFactory.getPositionObject(PositionTypes.D2,intPositions);
                    this.setTarget(p);
                    this.setType(CommandTypes.BLOCK);
                }
            }

        }
        else
        {
            LOG.warning(s+" is not a valid command. Ignoring it.");
            throw new InvalidCommandException("BLOCK command format must be BLOCK X,Y");
        }
    }
    public void execute(AbstractMovableArea tt)
    {
        try {
            PieceTypes c = tt.getPositionState(this.getTarget());
            if(c==PieceTypes.EMPTY)
                tt.placePiece(PieceTypes.BLOCK,this.getTarget());
            else
                LOG.info("The position "+this.getTarget().toString()+" is not empty. So ignoring command.");

        }
        catch (InvalidPositionException e)
        {
            LOG.severe("The position "+this.getTarget().toString()+" is not valid in current playable area. Error Received :"+e.getMessage());
        }
        catch (AreaNotInitializedException e)
        {
            LOG.severe("The movable area is not yet initialized. Error Received :"+e.getMessage());
        }
    }
}
