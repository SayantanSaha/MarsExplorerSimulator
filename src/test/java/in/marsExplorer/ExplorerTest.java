package in.marsExplorer;


import in.marsExplorer.exceptions.InvalidPositionException;
import in.marsExplorer.factories.PositionFactory;
import in.marsExplorer.lists.PieceTypes;
import in.marsExplorer.lists.PositionTypes;
import in.marsExplorer.models.AbstractPosition;
import in.marsExplorer.models.TableTop;
import in.marsExplorer.models.commands.Explorer;
import org.junit.jupiter.api.*;

@DisplayName("Test for explorer commands")
public class ExplorerTest {
    TableTop tableTop;

    @BeforeEach
    void setUp() throws Exception {
        System.out.println("Creating table top of dimention 5");
        tableTop = new TableTop(5, 5);
        System.out.println("Initializing table top");
        tableTop.initialize();
        PieceTypes[][] tableState = {
                {PieceTypes.EXPLORER,PieceTypes.EMPTY,PieceTypes.EMPTY,PieceTypes.EMPTY,PieceTypes.EMPTY},
                {PieceTypes.BLOCK,PieceTypes.BLOCK,PieceTypes.EMPTY,PieceTypes.BLOCK,PieceTypes.BLOCK},
                {PieceTypes.EMPTY,PieceTypes.EMPTY,PieceTypes.EMPTY,PieceTypes.EMPTY,PieceTypes.EMPTY},
                {PieceTypes.BLOCK,PieceTypes.EMPTY,PieceTypes.BLOCK,PieceTypes.EMPTY,PieceTypes.BLOCK},
                {PieceTypes.BLOCK,PieceTypes.EMPTY,PieceTypes.EMPTY,PieceTypes.EMPTY,PieceTypes.EMPTY}
        };
        for(int i =0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                AbstractPosition p = PositionFactory.getPositionObject(PositionTypes.D2,new Integer[]{i,j});
                tableTop.placePiece(tableState[i][j],p);
            }
        }
    }

    @AfterEach
    void tearDown() throws Exception {

    }



    @Test
    void testMinPath(){
        AbstractPosition p = PositionFactory.getPositionObject(PositionTypes.D2,new Integer[]{4,4});
        Explorer e = new Explorer(p);
        //e.execute(tableTop);
        try {
            tableTop.placePiece(PieceTypes.DESTINATION,p);
            e.markVisted(tableTop);
            Assertions.assertEquals(8,e.findShortestPath(tableTop));
        } catch (InvalidPositionException e1) {
            e1.printStackTrace();
        }


    }

    @Test
    void testInvalidDestination(){
        AbstractPosition p = PositionFactory.getPositionObject(PositionTypes.D2,new Integer[]{4,5});
        Explorer e = new Explorer(p);
        //e.execute(tableTop);

            Assertions.assertThrows(InvalidPositionException.class,() -> {tableTop.placePiece(PieceTypes.DESTINATION,p);});




    }




}
