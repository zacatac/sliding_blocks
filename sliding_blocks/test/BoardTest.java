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

		// PRINTING OUT NOTEMPTY - visual purposes only
		System.out.println("NotEmpty");
		System.out.print("[");
        for (int i  = 0; i < b.getNotEmpty().length; i++){
            System.out.print("[");
            for (int j  = 0; j < b.getNotEmpty()[0].length; j++){
                System.out.print(b.getNotEmpty()[i][j] + " ");
            }
            if (i == b.getNotEmpty().length-1){
                System.out.print("]]" + "\n");
            }  else{
                System.out.print("]," + "\n");
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
	
	public void testIsFreeToMove(){
		//test1: 5x5 board
		Board b = new Board(5,5);
		int[] a1 = {0,0};
		int[] a2 = {1,1};
		int[] a3 = {2,2};
		int[] a4 = {1,4};
		
		b.addBlock("1 1",a1, true); //1x1 block
		b.addBlock("1 1",a2, true); // 1x1 block
		b.addBlock("2 2 ", a3, true); // 2x2
		b.addBlock("2 1", a4, true); //2x1
		
		int[] dim1 = b.keyToIntArray("1 1");
		int[] dim2 = b.keyToIntArray("2 2");
		int[] dim3 = b.keyToIntArray("2 1");
		
		String expected1 = "[false, true, false, true]";
		String expected2 = "[true, true, true, true]";
		String expected3 = "[true, true, true, false]";
		String expected4 = "[true, true, false, false]";
		
		System.out.println("A1" + Arrays.toString(b.isFreeToMove(dim1, a1)));
		System.out.println("A2" + Arrays.toString(b.isFreeToMove(dim1, a2)));
		System.out.println("A3" + Arrays.toString(b.isFreeToMove(dim2, a3)));
		System.out.println("A4" + Arrays.toString(b.isFreeToMove(dim3, a4)));
        
//SECOND TEST: 10x10 board
		Board b2 = new Board(10,10);
		int[] A0 = {0,0};
		int[] A1 = {0,5};
		int[] A2 = {1,3};
		int[] A3 = {1,6};
		int[] A4 = {1,9};
		int[] A5 = {3,2};
		int[] A6 = {3,4};
		int[] A7 = {5,7};
		int[] A8 = {7,0};
		int[] A9 = {7,1};
		int[] A10 = {7,4};
		int[] A11 = {8,6};
		int[] A12 = {8,8};
		int[] A13 = {9,0};
		int[] A14 = {9,5};
		
		b2.addBlock("3 3", A0, true);
		b2.addBlock("1 1", A1, true);
		b2.addBlock("1 2", A2, true);
		b2.addBlock("2 2", A3, true);
		b2.addBlock("2 1", A4, true);
		b2.addBlock("2 1", A5, true);
		b2.addBlock("3 3", A6, true);
		b2.addBlock("2 2", A7, true);
		b2.addBlock("1 1", A8, true);
		b2.addBlock("1 2", A9, true);
		b2.addBlock("2 1", A10, true);
		b2.addBlock("1 1", A11, true);
		b2.addBlock("2 2", A12, true);
		b2.addBlock("1 3", A13, true);
		b2.addBlock("1 1", A14, true);
		
		int[] d1 = b.keyToIntArray("1 1");
		int[] d2 = b.keyToIntArray("2 2");
		int[] d3 = b.keyToIntArray("3 3");
		int[] d12 = b.keyToIntArray("1 2");
		int[] d13 = b.keyToIntArray("1 3");
		int[] d21 = b.keyToIntArray("2 1");
		
		System.out.println("A0" + Arrays.toString(b2.isFreeToMove(d3, A0))); //FFFF
		System.out.println("A1" + Arrays.toString(b2.isFreeToMove(d1, A1))); // FTTT
		System.out.println("A2" + Arrays.toString(b2.isFreeToMove(d12, A2))); // TTFT
		System.out.println("A3" + Arrays.toString(b2.isFreeToMove(d2, A3))); //TFTT
		System.out.println("A4" + Arrays.toString(b2.isFreeToMove(d21, A4))); //TTTF
		System.out.println("A5" + Arrays.toString(b2.isFreeToMove(d21, A5))); //FTTT
		System.out.println("A6" + Arrays.toString(b2.isFreeToMove(d3, A6))); //FTTF
		System.out.println("A7" + Arrays.toString(b2.isFreeToMove(d2, A7))); //TTFT
		System.out.println("A8" + Arrays.toString(b2.isFreeToMove(d1, A8))); //TTFF
		System.out.println("A9" + Arrays.toString(b2.isFreeToMove(d12, A9))); //TTFT
		System.out.println("A10" + Arrays.toString(b2.isFreeToMove(d21, A10))); //TTTT
		System.out.println("A11" + Arrays.toString(b2.isFreeToMove(d1, A11))); // TTTT
		System.out.println("A12" + Arrays.toString(b2.isFreeToMove(d2, A12))); //TFTF
		System.out.println("A13" + Arrays.toString(b2.isFreeToMove(d13, A13)));//TFFT
		System.out.println("A14" + Arrays.toString(b2.isFreeToMove(d1, A14))); //TFTT
		
		//expected strings
		String e0 = "[false, false, false, false]";
		String e1 = "[false, true, true, true]";
		String e2 = "[true, true, false, true]";
		String e3 = "[true, false, true, true]";
		String e4 = "[true, true, true, false]";
		String e5 = "[false, true, true, true]";
		String e6 = "[false, true, true, false]";
		String e7 = "[true, true, false, true]";
		String e8 = "[true, true, false, false]";
		String e9 = "[true, true, false, true]";
		String e10 = "[true, true, true, true]";
		String e11 = "[true, true, true, true]";
		String e12 = "[true, false, true, false]";
		String e13 = "[true, false, false, true]";
		String e14 = "[true, false, true, true]";
		
		assertEquals(Arrays.toString(b2.isFreeToMove(d3, A0)),e0);
		assertEquals(Arrays.toString(b2.isFreeToMove(d1, A1)),e1);
		assertEquals(Arrays.toString(b2.isFreeToMove(d12, A2)),e2);
		assertEquals(Arrays.toString(b2.isFreeToMove(d2, A3)),e3);
		assertEquals(Arrays.toString(b2.isFreeToMove(d21, A4)),e4);
		assertEquals(Arrays.toString(b2.isFreeToMove(d21, A5)),e5);
		assertEquals(Arrays.toString(b2.isFreeToMove(d3, A6)),e6);
		assertEquals(Arrays.toString(b2.isFreeToMove(d2, A7)),e7);
		assertEquals(Arrays.toString(b2.isFreeToMove(d1, A8)),e8);
		assertEquals(Arrays.toString(b2.isFreeToMove(d12, A9)),e9);
		assertEquals(Arrays.toString(b2.isFreeToMove(d21, A10)),e10);
		assertEquals(Arrays.toString(b2.isFreeToMove(d1, A11)),e11);
		assertEquals(Arrays.toString(b2.isFreeToMove(d2, A12)),e12);
		assertEquals(Arrays.toString(b2.isFreeToMove(d13, A13)),e13);
		assertEquals(Arrays.toString(b2.isFreeToMove(d1, A14)),e14);
		
	}
	
	
//	public void testFindMoves(){
//		Board b = new Board(5,5);
//		int[] a1 = {0,0};
//		int[] a2 = {1,1};
//		int[] a3 = {2,2};
//		int[] a4 = {1,4};
//		
//		b.addBlock("1 1",a1, true); //1x1 block
//		b.addBlock("1 1",a2, true); // 1x1 block
//		b.addBlock("2 2 ", a3, true); // 2x2
//		b.addBlock("2 1", a4, true);
//		b.findMoves(1);
//	}
	
}
