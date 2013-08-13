/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/8/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class SolverTest extends TestCase {




    @Test
    public void testimport() {


        String[] args = new String[2];
        args[0] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/1x1.txt";
        args[1] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/1x1.goal.txt";

        Solver.main(args);

        args[0] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/1x2.one.block.txt";
        args[1] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/1x2.one.block.goal.txt";

        Solver.main(args);


        args[0] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/1x2.two.blocks.txt";
        args[1] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/1x2.two.blocks.goal.txt";

        Solver.main(args);

        args[0] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/140x140.txt";
        args[1] = "/Users/zrfield/GitHub/sliding_blocks/testfiles/140x140.goal.txt";

        Solver.main(args);




    }

//   @Test
//   public void testDoMove() {
//       int row = 3;
//       int column = 4;
//       Solver s = new Solver(3,4);
//       Move move = new Move(s.getGoalBoard());
//       int line = 0; //a sample test line number
//       boolean iWantToChaneNotEmpty = true; // for testing purposes.
//       String[] strings = {"0 0 0 1",
//               "1 0 1 0",
//               "1 1 1 1",
//               "0 2 0 3",
//               "2 0 2 1",
//               "2 2 2 3"};
//
//       for (int i = 0; i < 6; i++){
//           Solver.makeBlock(s.getBoard(),strings[i],row,column,line,iWantToChaneNotEmpty);
//       }
//
//       boolean[][] testWhiteSpaces = {{true,true,true,true},
//               {true,true,false,false},
//               {true,true,true,true},
//       };
//
//       boolean[][] shouldMatchTheExpected = s.getBoard().getNotEmpty();
//       assertArrayEquals(testWhiteSpaces,shouldMatchTheExpected);
//       int[] containsThis = {0,2};
//       assertTrue(s.getBoard().getBoard().containsKey("1 2"));
//
//       int previousRow = 0;
//       int previousColumn = 2;
//       int nextRow = 1;
//       int nextColumn = 2;
//       int dimRows = 1;
//       int dimColumns = 2;
//       int depth = 1;
//
//
//       Move move1 = new Move(previousRow,previousColumn,nextRow,nextColumn,dimRows,dimColumns,depth);
//       Stack<Move> myStack = new Stack<Move>();
//       PriorityQueue<Move> myQueue = new PriorityQueue<Move>();
//       s.setMoveStack(myStack);
//       s.setMoveQueue(myQueue);
//       s.doMove(s.getBoard(),move1);
//
//       boolean[][] firstMoveSpaces = {{true,true,false,false},
//                                      {true,true,true,true},
//                                      {true,true,true,true},
//                               };
//
//
//       shouldMatchTheExpected = s.getBoard().getNotEmpty();
//       assertArrayEquals(firstMoveSpaces,shouldMatchTheExpected);
//
//       s.undoMove(s.getBoard(), move1);
//
//       boolean[][] undoFirstMoveSpaces = {{true,true,true,true},
//               {true,true,false,false},
//               {true,true,true,true},
//       };
//
//       printTwoDimArray(s.getBoard().getNotEmpty());
//       shouldMatchTheExpected = s.getBoard().getNotEmpty();
//       assertArrayEquals(undoFirstMoveSpaces,shouldMatchTheExpected);
//
//       s.doMove(s.getBoard(),move1);
//
//       Move move2 = new Move(0,0,0,1,1,2,2);
//
//       printTwoDimArray(s.getBoard().getNotEmpty());
//
//       s.doMove(s.getBoard(),move2);
//
//       printTwoDimArray(s.getBoard().getNotEmpty());
//       s.doMove(s.getBoard(),move1);
//
//       printTwoDimArray(s.getBoard().getNotEmpty());
//       shouldMatchTheExpected = s.getBoard().getNotEmpty();
//       assertArrayEquals(firstMoveSpaces,shouldMatchTheExpected);
//
//       //Continue the comparison for this later
//
//
//   }
//
//    public static void printTwoDimArray(boolean[][] booleans){
//        System.out.println("WhiteSpaces");
//        System.out.print("[");
//        for (int i  = 0; i < booleans.length; i++){
//            System.out.print("[");
//            for (int j  = 0; j < booleans[0].length; j++){
//                System.out.print(booleans[i][j] + " ");
//            }
//            if (i ==booleans.length-1){
//                System.out.print("]]" + "\n");
//            }  else{
//                System.out.print("]," + "\n");
//            }
//
//        }
//    }
//
//    @Test
//    public void testMakeBlock(){
//        HashMap<String,ArrayList<int[]>> map = new HashMap<String, ArrayList<int[]>>();
//        Board goalBoard = new Board(3,4);
//        goalBoard.setBoard(map);
//        Move move = new Move(goalBoard);
//        int row = 3;
//        int column = 4;
//        int line = 0; //a sample test line number
//        boolean iWantToChaneNotEmpty = true; // for testing purposes.
//        Board board = new Board(row,column);
//        String[] s = {"0 0 0 1",
//                "1 0 1 0",
//                "1 1 1 1",
//                "0 2 0 3",
//                "2 0 2 1",
//                "2 2 2 3"};
//
//        for (int i = 0; i < 6; i++){
//            Solver.makeBlock(board,s[i],row,column,line,iWantToChaneNotEmpty);
//        }
//
//        HashMap<String,ArrayList<int[]>> boardMap  = board.getBoard();
//
//        int[] upperLeft0 = {0,0};
//        int[] upperLeft1 = {1,0};
//        int[] upperLeft2 = {1,1};
//        int[] upperLeft3 = {0,2};
//        int[] upperLeft4 = {2,0};
//        int[] upperLeft5 = {2,2};
//
//        assertTrue(boardMap.containsKey("1 1"));
//        assertTrue(boardMap.containsKey("1 2"));
//
//        ArrayList<int[]> blocks11 = boardMap.get("1 1");
//        ArrayList<int[]> blocks12 = boardMap.get("1 2");
//
//        for (int i = 0; i < 2; i++){
//            assertTrue(blocks11.get(0)[i] == upperLeft1[i]);
//            assertTrue(blocks11.get(1)[i] == upperLeft2[i]);
//            assertTrue(blocks12.get(0)[i] == upperLeft0[i]);
//            assertTrue(blocks12.get(1)[i] == upperLeft3[i]);
//            assertTrue(blocks12.get(2)[i] == upperLeft4[i]);
//            assertTrue(blocks12.get(3)[i] == upperLeft5[i]);
//
//        }
//
//        boolean[][] testWhiteSpaces = {{true,true,true,true},
//                                       {true,true,false,false},
//                                       {true,true,true,true},
//                                      };
//
//        boolean[][] shouldMatchTheExpected = board.getNotEmpty();
//        for (int i = 0; i < testWhiteSpaces.length; i++){
//            for (int j = 0; j < testWhiteSpaces[0].length;j++){
//                assertEquals(testWhiteSpaces[i][j],shouldMatchTheExpected[i][j]);
//            }
//        }
//
//        board = new Board(5,5);
//        String[] sFails = {"",         // too few inputs
//                           "1",        // too few inputs
//                           "1 2",      // too few inputs
//                           "1 2 3",    // too few inputs
//                           "1 2 3 4 5",// too many inputs
//                           null,       //Null input
//                           "a b c d",  //Non-numeric input
//                           "-1 2 3 4", //Invalid row input
//                           "1 2 -1 4", //Invalid row input
//                           "1 -1 3 4", //Invalid column input
//                           "1 2 3 -1", //Invalid column input
//                           "3 4 1 2"}; //reversed upper left and lower right
//
//        try{
//            Solver.makeBlock(board,sFails[0],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[1],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[2],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[3],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[4],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[5],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[6],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[7],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[8],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[9],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[10],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//        try{
//            Solver.makeBlock(board,sFails[11],5,5,0,false);
//            fail();
//        } catch (IllegalArgumentException a){}
//    }

}
