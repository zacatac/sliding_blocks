import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/12/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class MoveTest {
    @Test
    public void testCalculateScore() throws Exception {
        Board goalBoard = new Board(10,10);
        HashMap<String, ArrayList<int[]>> myBoard = new HashMap<String, ArrayList<int[]>>();

        ArrayList<int[]> twobytwoblocks = new ArrayList<int[]>();
        int[] block1 = {0,5};
        int[] block2 = {6,0};
        int[] block3 = {8,8};

        ArrayList<int[]> onebyoneblocks = new ArrayList<int[]>();
        int[] block4 = {0,7};
        int[] block5 = {9,3};
        int[] block6 = {8,0};

        myBoard.put("1 1", onebyoneblocks);
        myBoard.put("2 3", twobytwoblocks);

        goalBoard.addBlock("2 2",block1,false);
        goalBoard.addBlock("2 2",block2,false);
        goalBoard.addBlock("2 2",block3,false);
        goalBoard.addBlock("1 1",block4,false);
        goalBoard.addBlock("1 1",block5,false);
        goalBoard.addBlock("1 1",block6,false);
        Move initialize = new Move(goalBoard);

        Move move1 = new Move(0,0,0,1,1,1,0); //Nearest block is {0,7} score: 6
        Move move2 = new Move(0,0,0,1,1,1,2); //Nearest block is {0,7} score: 8
        Move move3 = new Move(8,3,8,4,1,1,0); //Nearest block is {9,3} score: 2
        Move move4 = new Move(4,0,5,0,1,1,0); //Nearest block is {5,0} score: 3
        Move move5 = new Move(6,5,6,6,2,2,0); //Nearest block is {8,8} score: 4

        assertEquals(6,move1.myScore);
        assertEquals(8,move2.myScore);
        assertEquals(2,move3.myScore);
        assertEquals(3,move4.myScore);
        assertEquals(4,move5.myScore);



    }


    @Test
    public void testEquals() throws Exception {
        Move move = new Move(0,0,0,0,0,0,0);

        Move move1 = null;
        assertFalse(move.equals(move1));

        Move move2 = new Move(0000000,0,0,0,0,0,0);
        assertTrue(move.equals(move2));

        Move move3 = new Move(0,0,0,0,0,0,1);
        assertFalse(move.equals(move3));

        Move move4 = new Move(0,0,0,0,1,0,0);
        assertFalse(move.equals(move4));

        Move move5 = new Move(0,0,0,1,0,0,0);
        assertFalse(move.equals(move5));

        Move move6 = new Move(0,0,1,0,0,0,0);
        assertFalse(move.equals(move6));

        Move move7 = new Move(0,1,0,0,0,0,1);
        assertFalse(move.equals(move7));

    }

    @Test
    public void testToString() throws Exception {
        Move move = new Move(0,1,1,1,2,2,5);
        String expected = "[0 1 1 1 2 2 5]";
        assertEquals(expected,move.toString());

    }
}
