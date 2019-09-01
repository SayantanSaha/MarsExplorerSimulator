package in.marsExplorer;

import in.marsExplorer.exceptions.InvalidCommandException;
import in.marsExplorer.factories.AreaFactory;
import in.marsExplorer.factories.CommandFactory;
import in.marsExplorer.lists.AreaTypes;
import in.marsExplorer.lists.CommandTypes;
import in.marsExplorer.models.AbstractMovableArea;
import in.marsExplorer.models.commands.AbstractCommand;
import lombok.Data;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;

@Data
public class MarsExplorerSimulator {
    private Queue<AbstractCommand> commandQueue;
    private AbstractMovableArea tableTop;
    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Scanner s;
    public MarsExplorerSimulator(InputStream in)
    {
        tableTop = AreaFactory.getMovableAreaObject(AreaTypes.SQUARE, Arrays.asList(5));
        commandQueue = new LinkedList<>();
        s = new Scanner(in);
    }

    public void run()
    {
        LOG.info("Starting to read commands.");
        while (s.hasNextLine())
        {
            String command = s.nextLine();
            LOG.info("Command found :"+command);
            String[] commandArray = command.split(" ");
            if(CommandTypes.contains(commandArray[0]))
            {
                try {
                    commandQueue.add(CommandFactory.getCommandObject(CommandTypes.valueOf(commandArray[0]), command));
                }
                catch (InvalidCommandException e)
                {
                    LOG.warning(e.getMessage());
                }
            }
            else
            {
                LOG.warning("Command "+command+" is not a valid command. So ignoring it.");
            }
        }
        LOG.info("Finished reading commands.");
        if(commandQueue.isEmpty())
        {
            LOG.info("Command queue is empty. Nothing to process.");
        }
        else
        {
            LOG.info("Starting to process commands.");
            boolean placeCommandFound = false;
            while(commandQueue.peek()!=null)
            {
                AbstractCommand abstractCommand = commandQueue.poll();
                if(abstractCommand.getType()==CommandTypes.PLACE) {
                    placeCommandFound = true;
                    LOG.info("Place command found.Process starting.");
                    abstractCommand.execute(tableTop);
                }
                //Skipping commands till PLACE is found
                if(!placeCommandFound && abstractCommand.getType()!=CommandTypes.REPORT)
                {
                    LOG.info("PLACE command not yet found. Rejecting command "+abstractCommand.getType() );
                    continue;
                }
                else if(!placeCommandFound && abstractCommand.getType()==CommandTypes.REPORT){
                    LOG.info("PLACE command not yet found. But REPORT found. Rejecting this command and no further commands will be processed." );
                    break;
                }
                if(abstractCommand.getType()==CommandTypes.REPORT)
                {
                    abstractCommand.execute(tableTop);
                    //Rejecting all commands after REPORT
                    LOG.info("REPORT found. No further commands will be processed." );
                    break;

                }
                else
                {
                    abstractCommand.execute(tableTop);
                }

            }
            LOG.info("Completed processing commands");
        }
    }

}
