import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import junit.framework.TestCase;


public class BoardTest extends TestCase{

    public void testGetNotEmpty(){
		//test a completely false notEmpty array
		Board b = new Board(5,5);
		boolean[][] test = new boolean[5][5];
		
		for (int i = 0; i< 5; i++){
			for (int j = 0; j <5; j++){
				assertFalse(b.getNotEmpty()[i][j]);
			}
		}
		
		//test adding a 1x2 block located at topleft of 0,0 
		Board b2 = new Board(4,4);
		
		int[] array = {0,0};
		b2.addBlock("1 2", array, true);
		
		for (int i = 0; i< 1; i++){
			for (int j = 0; j <2; j++){
				assertTrue(b2.getNotEmpty()[i][j]);
			}
		}

		Board b3 = new Board(2,2);
		b3.addBlock("2 2", array, true);
		boolean[][] notEmpty = b3.getNotEmpty();
//        System.out.print("[");
//        for (int i  = 0; i < notEmpty.length; i++){
//            System.out.print("[");
//            for (int j  = 0; j < notEmpty[0].length; j++){
//                System.out.print(notEmpty[i][j] + " ");
//            }
//            if (i == notEmpty.length-1){
//                System.out.print("]]" + "\n");
//            }  else{
//                System.out.print("]," + "\n");
//            }
//
//        }
        System.out.println();
		assertTrue(b3.getNotEmpty()[0][0]);
		assertTrue(b3.getNotEmpty()[0][1]);
		assertTrue(b3.getNotEmpty()[1][0]);
		assertTrue(b3.getNotEmpty()[1][1]);
		
	}
	
	public void testChangeWhiteSpaces(){
        Board board = new Board(3,3);

        int[] upperLeft = {0,0};
        board.changeWhiteSpaces("1 1",upperLeft,true);

        boolean[][] expected1 = {{true,false,false},
                                {false,false,false},
                                {false,false,false}};

        assertArrayEquals(expected1,board.getNotEmpty());

        board.changeWhiteSpaces("1 1",upperLeft,false);

        boolean[][] expected2 = {{false,false,false},
                {false,false,false},
                {false,false,false}};

        assertArrayEquals(expected2,board.getNotEmpty());


        board.changeWhiteSpaces("3 3",upperLeft,true);

        boolean[][] expected3 = {{true,true,true},
                {true,true,true},
                {true,true,true}};

        assertArrayEquals(expected3,board.getNotEmpty());


        board.changeWhiteSpaces("3 3",upperLeft,false);

        assertArrayEquals(expected2,board.getNotEmpty());


    }

	public void testAddBlock(){
		//test if adding same blocks in different order produces the same board
		Board board1 = new Board(4,4);
		int[] a1 = {1,0};
		int[] a2 = {0,1};
		int[] a3 = {3,2};
		int[] a4 = {2,2};
		int[] a5 = {1,3};
		 
		board1.addBlock("1 1",a1, false);
		board1.addBlock("1 1", a2, false);
		board1.addBlock("1 1", a3, false);
		board1.addBlock("1 1", a4, false);
		board1.addBlock("1 1", a5, false);
		
		Board board2 = new Board(4,4);
		board2.addBlock("1 1", a5, false);
		board2.addBlock("1 1", a3, false);
		board2.addBlock("1 1", a1, false);
		board2.addBlock("1 1", a2, false);
		board2.addBlock("1 1", a4, false);
		assertEquals(board1.toString(), board2.toString());
		
		//test if adding a list of different sized blocks in different orders 
		//produces the same board
		Board b1 = new Board(5,5);
		Board b2 = new Board(5,5);
		
		int[] A1 = {0,0};
		int[] A2 = {0,1};
		int[] A3 = {2,0};
		int[] A4 = {2,2};
		int[] A5 = {3,4};
		
		b1.addBlock("2 1", A1, false);
		b1.addBlock("1 3", A2, false);
		b1.addBlock("2 1", A3, false);
		b1.addBlock("2 1", A4, false);
		b1.addBlock("1 1", A5, false);
		
		b2.addBlock("2 1", A3, false);
		b2.addBlock("1 1", A5, false);
		b2.addBlock("1 3", A2, false);
		b2.addBlock("2 1", A4, false);
		b2.addBlock("2 1", A1, false);
		
		assertEquals(b1.toString(), b2.toString());
		
		//test that covering the same area does not mean equal boards
		Board B1 = new Board(2,2);
		Board B2 = new Board(2,2);
		Board B3 = new Board(2,2);
		int[] ar1 = {0,0};
		int[] ar2 = {0,1};
		int[] ar3 = {1,0};
		int[] ar4 = {1,1};
		
		B1.addBlock("2 2", ar1, false);
		
		B2.addBlock("1 1", ar1, false);
		B2.addBlock("1 1", ar2, false);
		B2.addBlock("1 1", ar3, false);
		B2.addBlock("1 1", ar4, false);
		
		B3.addBlock("2 1", ar1, false);
		B3.addBlock("2 1", ar2, false);
		
		assertNotSame(B1.toString(), B2.toString());
		assertNotSame(B2.toString(), B3.toString());
		assertNotSame(B3.toString(), B1.toString());
		
	}
	public void testRemoveBlock() throws IllegalArgumentException{
		//test removing blocks of the same size
		Board board1 = new Board(4,4);
		Board board2 = new Board(4,4);
		int[] a1 = {1,0};
		int[] a2 = {0,1};
		int[] a3 = {3,2};
		int[] a4 = {2,2};
		int[] a5 = {1,3};
		int[] a6 = {0,3};
		
		board1.addBlock("1 1",a1, false);
		board1.addBlock("1 1", a2, false);
		board1.addBlock("1 1", a3, false);
		board1.addBlock("1 1", a4, false);
		board1.addBlock("1 1", a5, false);
		
		board1.removeBlock("1 1", a1, false);
		board1.removeBlock("1 1", a2, false);
		board1.removeBlock("1 1", a3, false);
		board1.removeBlock("1 1", a4, false);
		board1.removeBlock("1 1", a5, false);
		System.out.print(board1);
		System.out.print(board2);
		assertEquals(board1.toString(), board2.toString());
		
		//test removing blocks of different sizes
		Board b1 = new Board(5,5);
		Board b2 = new Board(5,5);
		
		int[] A1 = {0,0};
		int[] A2 = {0,1};
		int[] A3 = {2,0};
		int[] A4 = {2,2};
		int[] A5 = {3,4};
		
		b1.addBlock("2 1", A1, false);
		b1.addBlock("1 3", A2, false);
		b1.addBlock("2 1", A3, false);
		b1.addBlock("2 1", A4, false);
		b1.addBlock("1 1", A5, false);
		
		b1.removeBlock("2 1", A3, false);
		b1.removeBlock("1 1", A5, false);
		b1.removeBlock("1 3", A2, false);
		
		b2.addBlock("2 1", A1, false);
		b2.addBlock("2 1", A4, false);
		assertEquals(b1.toString(), b2.toString());
		
		//test removing blocks 
	}

    public void testThisMoveWorked(){
        Board board = new Board(3,3);
        int[] upperLeft = {0,0};

        board.addBlock("1 1",upperLeft,false);
        assertTrue(board.thisMoveWorked(upperLeft, "1 1", false, true));
        board.removeBlock("1 1",upperLeft,false);
        upperLeft[1] = 1;
        board.addBlock("1 1",upperLeft,false );
        assertTrue(board.getMoveHistory().contains(board.toString()));
        board.removeBlock("1 1",upperLeft,false);
        upperLeft[1] = 2;
        board.addBlock("1 1",upperLeft,false);
        assertFalse(board.thisMoveWorked(upperLeft, "1 1", false, false));

    }
}
