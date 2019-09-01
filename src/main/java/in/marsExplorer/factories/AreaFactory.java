package in.marsExplorer.factories;

import in.marsExplorer.lists.AreaTypes;
import in.marsExplorer.models.AbstractMovableArea;
import in.marsExplorer.models.TableTop;

import java.util.List;

public class AreaFactory {
    public static AbstractMovableArea getMovableAreaObject(AreaTypes type, List<Integer> dimentions)
    {
        switch (type)
        {
            case SQUARE:
                if(dimentions.size()==1)
                    return new TableTop(dimentions.get(0),dimentions.get(0));
                else if (dimentions.size()==2)
                    return new TableTop(dimentions.get(0),dimentions.get(1));
                else
                    return null;
            default:
                return null;

        }
    }
}
