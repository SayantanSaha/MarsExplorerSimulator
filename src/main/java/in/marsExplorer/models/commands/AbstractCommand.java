package in.marsExplorer.models.commands;


import in.marsExplorer.lists.CommandTypes;
import in.marsExplorer.models.AbstractMovableArea;
import in.marsExplorer.models.AbstractPosition;
import lombok.Data;

@Data
public abstract class AbstractCommand {
    private AbstractPosition target;
    private CommandTypes type;
    public abstract void execute(AbstractMovableArea tt);

}
