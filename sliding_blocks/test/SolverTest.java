/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/8/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.*;


public class SolverTest extends TestCase {

    public void testimport() {
        Solver s = new Solver();

        String[] args = new String[2];
        args[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.4.txt";
        args[1] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.4.goal.txt";

//        s.main(args);
//
//        String[] args1 = new String[2];
//        args1[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.3.txt";
//        s.main(args1);
//
//        String[] args2 = new String[2];
//        args2[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.2.txt";
//        s.main(args2);
//
//        String[] args3 = new String[2];
//        args3[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.1.txt";
//        s.main(args3);
    }

   public void testDoMove() {
       int row = 3;
       int column = 4;
       int line = 0; //a sample test line number
       boolean iWantToChaneNotEmpty = true; // for testing purposes.
       Board board = new Board(row,column);
       String[] s = {"0 0 0 1",
               "1 0 1 0",
               "1 1 1 1",
               "0 2 0 3",
               "2 0 2 1",
               "2 2 2 3"};

       for (int i = 0; i < 6; i++){
           Solver.makeBlock(board,s[i],row,column,line,iWantToChaneNotEmpty);
       }

       boolean[][] testWhiteSpaces = {{true,true,true,true},
               {true,true,false,false},
               {true,true,true,true},
       };

       boolean[][] shouldMatchTheExpected = board.getNotEmpty();
       for (int i = 0; i < testWhiteSpaces.length; i++){
           for (int j = 0; j < testWhiteSpaces[0].length;j++){
               assertEquals(testWhiteSpaces[i][j],shouldMatchTheExpected[i][j]);
           }
       }

       int previousRow = 0;
       int previousColumn = 2;
       int nextRow = 1;
       int nextColumn = 2;
       int dimRows = 1;
       int dimColumns = 2;
       int depth = 1;

       Move move1 = new Move(previousRow,previousColumn,nextRow,nextColumn,dimRows,dimColumns,depth);
       Stack<Move> myStack = new Stack<Move>();
       PriorityQueue<Move> myQueue = new PriorityQueue<Move>();
       Solver.setMoveStack(myStack);
       Solver.setMoveQueue(myQueue);
       Solver.doMove(board,move1);

       boolean[][] firstMoveSpaces = {{true,true,false,false},
                                      {true,true,true,true},
                                      {true,true,true,true},
                               };

       shouldMatchTheExpected = board.getNotEmpty();
       for (int i = 0; i < testWhiteSpaces.length; i++){
           for (int j = 0; j < testWhiteSpaces[0].length;j++){
               assertEquals(firstMoveSpaces[i][j],shouldMatchTheExpected[i][j]);
           }
       }

       Solver.undoMove(board,move1);

       boolean[][] undoFirstMoveSpaces = {{true,true,true,true},
               {true,true,false,false},
               {true,true,true,true},
       };

       shouldMatchTheExpected = board.getNotEmpty();
       for (int i = 0; i < testWhiteSpaces.length; i++){
           for (int j = 0; j < testWhiteSpaces[0].length;j++){
               assertEquals(testWhiteSpaces[i][j],shouldMatchTheExpected[i][j]);
           }
       }

       Solver.doMove(board,move1);

       Move move2 = new Move(0,0,0,1,1,2,2);

       Solver.doMove(board,move2);
       Solver.doMove(board,move1);

       //Continue the comparison for this later


   }

    public void testMakeBlock(){
        int row = 3;
        int column = 4;
        int line = 0; //a sample test line number
        boolean iWantToChaneNotEmpty = true; // for testing purposes.
        Board board = new Board(row,column);
        String[] s = {"0 0 0 1",
                "1 0 1 0",
                "1 1 1 1",
                "0 2 0 3",
                "2 0 2 1",
                "2 2 2 3"};

        for (int i = 0; i < 6; i++){
            Solver.makeBlock(board,s[i],row,column,line,iWantToChaneNotEmpty);
        }

        HashMap<String,ArrayList<int[]>> boardMap  = board.getBoard();

        int[] upperLeft0 = {0,0};
        int[] upperLeft1 = {1,0};
        int[] upperLeft2 = {1,1};
        int[] upperLeft3 = {0,2};
        int[] upperLeft4 = {2,0};
        int[] upperLeft5 = {2,2};

        assertTrue(boardMap.containsKey("11"));
        assertTrue(boardMap.containsKey("12"));

        ArrayList<int[]> blocks11 = boardMap.get("11");
        ArrayList<int[]> blocks12 = boardMap.get("12");

        for (int i = 0; i < 2; i++){
            assertTrue(blocks11.get(0)[i] == upperLeft1[i]);
            assertTrue(blocks11.get(1)[i] == upperLeft2[i]);
            assertTrue(blocks12.get(0)[i] == upperLeft0[i]);
            assertTrue(blocks12.get(1)[i] == upperLeft3[i]);
            assertTrue(blocks12.get(2)[i] == upperLeft4[i]);
            assertTrue(blocks12.get(3)[i] == upperLeft5[i]);

        }

        boolean[][] testWhiteSpaces = {{true,true,true,true},
                                       {true,true,false,false},
                                       {true,true,true,true},
                                      };

        boolean[][] shouldMatchTheExpected = board.getNotEmpty();
        for (int i = 0; i < testWhiteSpaces.length; i++){
            for (int j = 0; j < testWhiteSpaces[0].length;j++){
                assertEquals(testWhiteSpaces[i][j],shouldMatchTheExpected[i][j]);
            }
        }

        board = new Board(5,5);
        String[] sFails = {"",         // too few inputs
                           "1",        // too few inputs
                           "1 2",      // too few inputs
                           "1 2 3",    // too few inputs
                           "1 2 3 4 5",// too many inputs
                           null,       //Null input
                           "a b c d",  //Non-numeric input
                           "-1 2 3 4", //Invalid row input
                           "1 2 -1 4", //Invalid row input
                           "1 -1 3 4", //Invalid column input
                           "1 2 3 -1", //Invalid column input
                           "3 4 1 2"}; //reversed upper left and lower right

        try{
            Solver.makeBlock(board,sFails[0],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[1],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[2],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[3],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[4],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[5],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[6],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[7],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[8],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[9],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[10],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
        try{
            Solver.makeBlock(board,sFails[11],5,5,0,false);
            fail();
        } catch (IllegalArgumentException a){}
    }

}
