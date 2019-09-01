package in.marsExplorer.lists;

public enum PieceTypes {

    EXPLORER('e'),BLOCK('b'),DESTINATION('d'),EMPTY('*');

    private Character value;
    PieceTypes(Character value)
    {
        this.value = value;

    }
    public Character getValue()
    {
        return this.value;
    }
}
