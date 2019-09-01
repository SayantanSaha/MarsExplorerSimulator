package in.marsExplorer.models;

import in.marsExplorer.exceptions.AreaNotInitializedException;
import in.marsExplorer.exceptions.InvalidPositionException;
import in.marsExplorer.factories.PositionFactory;
import in.marsExplorer.lists.PieceTypes;
import in.marsExplorer.lists.PositionTypes;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


@Data
public class TableTop extends AbstractMovableArea {
    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public TableTop(Integer length,Integer breadth)
    {
        HashMap<String,Integer> dimentions = new HashMap<>();
        dimentions.put("length",length);
        dimentions.put("breadth",breadth);
        this.setDimentions(dimentions);
        this.setCurrentState(null);

    }

    public void initialize()
    {
        List currentState = new ArrayList<ArrayList<PieceTypes>>();
        for(int i=0;i<this.getDimentions().get("length");i++)
        {
            ArrayList<PieceTypes> newRow = new ArrayList<>();
            for(int j=0;j<this.getDimentions().get("breadth");j++)
            {
                newRow.add(PieceTypes.EMPTY);
            }
            currentState.add(newRow);
        }
        this.setCurrentState(currentState);
    }
    public boolean isValidPosition(AbstractPosition p)
    {
        return !( p.getLocation().containsKey("x")==false ||
                p.getLocation().containsKey("y")==false ||
                p.getLocation().get("x")==null ||
                p.getLocation().get("x")<0 ||
                p.getLocation().get("x")>=this.getDimentions().get("length") ||
                p.getLocation().get("y")==null||
                p.getLocation().get("y")<0 ||
                p.getLocation().get("y")>=this.getDimentions().get("breadth"));
    }
    public void placePiece(PieceTypes piece, AbstractPosition p) throws InvalidPositionException
    {
        if(p==null)
        {
            throw new InvalidPositionException("The position can not be null.");
        }

        if(!isValidPosition(p))
        {
            throw new InvalidPositionException("The position "+p.toString()+" is not valid");
        }
        else {
            ((ArrayList<PieceTypes>) this.getCurrentState().get(p.getLocation().get("x"))).set(p.getLocation().get("y"), piece);
        }
    }
    public PieceTypes getPositionState(AbstractPosition p) throws InvalidPositionException, AreaNotInitializedException
    {
        if(!isValidPosition(p))
        {
            throw new InvalidPositionException("The position "+p.toString()+" is not valid");
        }
        if(this.getCurrentState()==null)
        {
            LOG.severe("The area is not yet initialized");
            throw new AreaNotInitializedException("The area is not yet initialized");
        }
        else {

            return ((ArrayList<PieceTypes>) this.getCurrentState().get(p.getLocation().get("x"))).get(p.getLocation().get("y"));
        }
    }
    public void displayCurrentState() throws AreaNotInitializedException
    {
        if(this.getCurrentState()==null)
        {
            LOG.severe("The area is not yet initialized");
            throw new AreaNotInitializedException("The area is not yet initialized");
        }
        else {
            for (int i = 0; i < this.getDimentions().get("length"); i++) {
                for (int j = 0; j < this.getDimentions().get("breadth"); j++) {
                    System.out.print(((ArrayList<PieceTypes>) this.getCurrentState().get(i)).get(j).getValue());
                }
                System.out.println();
            }
        }
    }

    public HashMap<PieceTypes,List<AbstractPosition>> getPositionsOfPieces(){
        HashMap<PieceTypes,List<AbstractPosition>> listOfPieces = new HashMap<>();
        for(int i=0;i<this.getDimentions().get("length");i++)
        {
            for(int j=0;j<this.getDimentions().get("breadth");j++)
            {
                AbstractPosition position = PositionFactory.getPositionObject(PositionTypes.D2,new Integer[]{i,j});
                try {
                    PieceTypes c = this.getPositionState(position);
                    if(listOfPieces.containsKey(c))
                        listOfPieces.get(c).add(position);
                    else
                    {
                        List<AbstractPosition> l = new ArrayList<>();
                        l.add(position);
                        listOfPieces.put(c,l);
                    }

                }
                catch (InvalidPositionException ex)
                {
                    LOG.severe("The position "+position.toString()+" is not valid in current playable area. Error Received :"+ex.getMessage());


                }
                catch (AreaNotInitializedException ex)
                {
                    LOG.severe("The movable area is not yet initialized.Ignoring this command.... ");
                }
            }
        }
        return listOfPieces;
    }


}
