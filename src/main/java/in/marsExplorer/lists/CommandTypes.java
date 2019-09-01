package in.marsExplorer.lists;

public enum CommandTypes {
    PLACE,BLOCK,REPORT,EXPLORER;

    public static boolean contains(String s)
    {
        try{
            CommandTypes.valueOf(s);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
