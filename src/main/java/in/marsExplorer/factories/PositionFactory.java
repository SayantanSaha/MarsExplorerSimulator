package in.marsExplorer.factories;

import in.marsExplorer.lists.PositionTypes;
import in.marsExplorer.models.AbstractPosition;
import in.marsExplorer.models.Position2d;

public class PositionFactory {
    public static AbstractPosition getPositionObject(PositionTypes type, Integer[] coords)
    {
        switch (type){
            case D2:
                return new Position2d(coords[0],coords[1]);
            default:
                return null;
        }

    }
}
