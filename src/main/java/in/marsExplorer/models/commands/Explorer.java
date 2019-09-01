package in.marsExplorer.models.commands;

import in.marsExplorer.exceptions.AreaNotInitializedException;
import in.marsExplorer.exceptions.InvalidCommandException;
import in.marsExplorer.exceptions.InvalidPositionException;
import in.marsExplorer.factories.PositionFactory;
import in.marsExplorer.lists.CommandTypes;
import in.marsExplorer.lists.PieceTypes;
import in.marsExplorer.lists.PositionTypes;
import in.marsExplorer.models.AbstractMovableArea;
import in.marsExplorer.models.AbstractPosition;

import java.util.*;
import java.util.logging.Logger;

public class Explorer extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static QNode source;
    static boolean[][] visited;


    public Explorer(AbstractPosition position)
    {
        this.setTarget(position);
        this.setType(CommandTypes.EXPLORER);
    }
    public String toString()
    {
        return this.getType()+" "+this.getTarget().toString();
    }
    public Explorer (String s) throws InvalidCommandException
    {
        String[] commandArr = s.split(" ");
        if(CommandTypes.EXPLORER == CommandTypes.valueOf(commandArr[0]))
        {
            if(commandArr.length!=2)
            {
                LOG.warning(s+" is not a valid command. Ignoring it.");
                throw new InvalidCommandException("EXPLORER command format must be EXPLORER X,Y");
            }
            else {
                String[] position = commandArr[1].split(",");
                if(position.length!=2)
                {
                    LOG.warning(s+" is not a valid command. Ignoring it.");
                    throw new InvalidCommandException("BLOCK command format must be BLOCK X,Y");
                }
                else
                {
                    Integer[] intPositions = Arrays.stream(position)
                            .map(Integer::parseInt)
                            .toArray(Integer[]::new);
                    AbstractPosition p = PositionFactory.getPositionObject(PositionTypes.D2,intPositions);
                    this.setTarget(p);
                    this.setType(CommandTypes.EXPLORER);
                }
            }

        }
        else
        {
            LOG.warning(s+" is not a valid command. Ignoring it.");
            throw new InvalidCommandException("EXPLORER command format must be EXPLORER X,Y");
        }
    }
    public void execute(AbstractMovableArea tt){


        if(tt.isInitialized())
        {

            try {
                if(tt.getPositionState(this.getTarget())== PieceTypes.EMPTY) {
                    tt.placePiece(PieceTypes.DESTINATION, this.getTarget());
                }
                else {
                    LOG.info("The target position is already occupied.Ignoring command "+this.toString());
                    return;
                }
            } catch (InvalidPositionException e) {
                LOG.severe(e.getMessage());
                return ;
            }
            catch (AreaNotInitializedException e) {
                LOG.severe(e.getMessage());
                return ;
            }

            markVisted(tt);

            int result = findShortestPath(tt);
            System.out.println("Length of shortest path : "+result);
            if(result != -1) {
                HashMap<PieceTypes, List<AbstractPosition>> l = tt.getPositionsOfPieces();
                try {
                    tt.placePiece(PieceTypes.EMPTY,l.get(PieceTypes.EXPLORER).get(0));
                    tt.placePiece(PieceTypes.EXPLORER,l.get(PieceTypes.DESTINATION).get(0));
                } catch (InvalidPositionException e) {
                    LOG.severe(e.getMessage());
                }

            }


        }
        else
        {
            LOG.info("Area not initialized. So ignoring command EXPLORER "+this.getTarget().toString());
        }

    }

    public void markVisted(AbstractMovableArea tt) {

        int N = tt.getDimentions().get("length");
        int M = tt.getDimentions().get("breadth");

        source = new QNode(0, 0, 0, null);

        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {

            for (int j = 0; j < M; j++) {
                AbstractPosition p = PositionFactory.getPositionObject(PositionTypes.D2,new Integer[]{i,j});

                try {
                    visited[i][j] = tt.getPositionState(p) == PieceTypes.BLOCK;

                    if (tt.getPositionState(p) == PieceTypes.EXPLORER) {

                        source.position = p;


                    }
                }
                catch (AreaNotInitializedException ex)
                {
                    LOG.severe("Area not initialized");
                }
                catch (InvalidPositionException ex)
                {
                    LOG.severe("The position "+p.toString()+" is invalid for the current area.");
                }

            }

        }
    }



    public int findShortestPath(AbstractMovableArea tt) {
        try {
            tt.displayCurrentState();
            int N = tt.getDimentions().get("length");
            int M = tt.getDimentions().get("breadth");

            Queue<QNode> q = new LinkedList<QNode>();

            q.add(source);

            visited[source.position.getLocation().get("x")][source.position.getLocation().get("y")] = true;

            while (!q.isEmpty()) {

                //System.out.println("size of q ---- " + q.size());

                QNode p = q.peek();

                q.remove();

                // Destination found;

                if (tt.getPositionState(p.position) == PieceTypes.DESTINATION) {

                    p.getStringPath();

                    return p.distance;

                }

                // moving up

                if (p.position.getLocation().get("x") - 1 >= 0 && visited[p.position.getLocation().get("x") - 1][p.position.getLocation().get("y")] == false) {

                    q.add(new QNode(p.position.getLocation().get("x") - 1, p.position.getLocation().get("y"), p.distance + 1, p.getPath()));

                    visited[p.position.getLocation().get("x") - 1][p.position.getLocation().get("y")] = true;

                    //System.out.println("move up");

                }

                // moving down

                if (p.position.getLocation().get("x") + 1 < N && visited[p.position.getLocation().get("x") + 1][p.position.getLocation().get("y")] == false) {

                    q.add(new QNode(p.position.getLocation().get("x") + 1, p.position.getLocation().get("y"), p.distance + 1, p.getPath()));

                    visited[p.position.getLocation().get("x") + 1][p.position.getLocation().get("y")] = true;

                    //System.out.println("move down");

                }

                // moving right

                if (p.position.getLocation().get("y") + 1 < M && visited[p.position.getLocation().get("x")][p.position.getLocation().get("y") + 1] == false) {

                    q.add(new QNode(p.position.getLocation().get("x"), p.position.getLocation().get("y") + 1, p.distance + 1, p.getPath()));

                    visited[p.position.getLocation().get("x")][p.position.getLocation().get("y") + 1] = true;

                    //System.out.println("move right");

                }

                // moving left

                if (p.position.getLocation().get("y") - 1 >= 0 && visited[p.position.getLocation().get("x")][p.position.getLocation().get("y") - 1] == false) {

                    q.add(new QNode(p.position.getLocation().get("x"), p.position.getLocation().get("y") - 1, p.distance + 1, p.getPath()));

                    visited[p.position.getLocation().get("x")][p.position.getLocation().get("y") - 1] = true;

                    //System.out.println("move left");

                }

                //System.out.println("------------------");

            }
        }
        catch (AreaNotInitializedException ex)
        {
            LOG.severe("Area not initialized");
            return -1;
        }
        catch (InvalidPositionException ex)
        {
            LOG.severe(ex.getMessage());
            return -1;
        }
        return -1;

    }


    private static class QNode {

        AbstractPosition position;

        int distance;

        List<QNode> path = new LinkedList<QNode>();

        public List<QNode> getPath() {

            return path;

        }

        public void getStringPath() {

            System.out.print("PATH:");
            for (QNode qNode : path) {

                System.out.print(qNode.position.toString());



            }
            System.out.println();
        }

        QNode(int row, int col, int distance, List<QNode> prePath) {

            position = PositionFactory.getPositionObject(PositionTypes.D2,new Integer[]{row,col});

            this.distance = distance;

            if (prePath == null)

                this.path.add(this);

            else {

                this.path.addAll(prePath);

                this.path.add(this);

            }

        }

    }

}
