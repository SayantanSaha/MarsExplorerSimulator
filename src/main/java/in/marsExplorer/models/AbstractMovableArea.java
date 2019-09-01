package in.marsExplorer.models;

import in.marsExplorer.exceptions.AreaNotInitializedException;
import in.marsExplorer.exceptions.InvalidPositionException;
import in.marsExplorer.lists.PieceTypes;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
public abstract class AbstractMovableArea {
    private HashMap<String,Integer> dimentions;
    private List currentState;

    public boolean isInitialized()
    {
        return currentState!=null;
    }
    public abstract void initialize();
    public abstract void placePiece(PieceTypes piece, AbstractPosition p) throws InvalidPositionException;
    public abstract PieceTypes getPositionState(AbstractPosition p)  throws InvalidPositionException, AreaNotInitializedException;
    public abstract void displayCurrentState() throws AreaNotInitializedException;
    public abstract boolean isValidPosition(AbstractPosition p);
    public abstract HashMap<PieceTypes,List<AbstractPosition>> getPositionsOfPieces();

}
