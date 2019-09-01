package in.marsExplorer.models.commands;

import in.marsExplorer.exceptions.InvalidCommandException;
import in.marsExplorer.lists.CommandTypes;
import in.marsExplorer.lists.PieceTypes;
import in.marsExplorer.models.AbstractMovableArea;
import in.marsExplorer.models.AbstractPosition;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Report extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public Report()
    {
        this.setTarget(null);
        this.setType(CommandTypes.REPORT);
    }
    public Report (String s) throws InvalidCommandException
    {

        if(CommandTypes.REPORT == CommandTypes.valueOf(s.trim()))
        {
            this.setTarget(null);
            this.setType(CommandTypes.REPORT);
        }
        else
        {
            LOG.warning(s+" is not a valid command. Ignoring it.");
            throw new InvalidCommandException("REPORT command format must be REPORT");
        }
    }
    public void execute(AbstractMovableArea tt)
    {
        HashMap<PieceTypes, List<AbstractPosition>> l = tt.getPositionsOfPieces();



        System.out.print("E:");
        if(l.containsKey(PieceTypes.EXPLORER) && l.get(PieceTypes.EXPLORER)!=null)
            l.get(PieceTypes.EXPLORER).forEach(p-> System.out.print(p.toString()+" "));
        else
            System.out.print(" ");
        System.out.print("B:");
        if(l.containsKey(PieceTypes.BLOCK) && l.get(PieceTypes.BLOCK)!=null)
            l.get(PieceTypes.BLOCK).forEach(p-> System.out.print(p.toString()+" "));


        System.out.println();


    }
}
