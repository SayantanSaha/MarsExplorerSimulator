package in.marsExplorer.models;

import lombok.Data;

import java.util.HashMap;
@Data
public abstract class AbstractPosition {
    private HashMap<String,Integer> location;
    public abstract String toString();
}
