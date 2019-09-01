package in.marsExplorer.models;

import lombok.Data;

import java.util.HashMap;

@Data
public  class Position2d extends AbstractPosition{
    public Position2d(Integer x, Integer y)
    {
        HashMap<String,Integer> tempLocation = new HashMap<>();
        tempLocation.put("x",x);
        tempLocation.put("y",y);
        this.setLocation(tempLocation);

    }
    public String toString()
    {
        return "("+this.getLocation().get("x")+","+this.getLocation().get("y")+")";
    }
}
