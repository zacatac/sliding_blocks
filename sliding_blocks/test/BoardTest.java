import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

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
		
		//test adding a 2x2 block covering the whole board
//WHY DOES THIS NOT WORK D:
		Board b3 = new Board(2,2);
		b3.addBlock("2 2", array, true);
		assertTrue(b2.getNotEmpty()[0][0]);
		assertTrue(b2.getNotEmpty()[0][1]);
		assertTrue(b2.getNotEmpty()[1][0]);
		assertTrue(b2.getNotEmpty()[1][1]);
//SAME THING, DIFFERENT FORMAT
		for (int i = 0; i< 2; i++){
			for (int j = 0; j <2; j++){
				assertTrue(b2.getNotEmpty()[i][j]);
			}
		}
		
	}
	
	public void testAddBlock(){
		Board board1 = new Board(4,4);
		Board board2 = new Board(4,4);
		System.out.println("HELP");

		int[] array1 = {1,0};
		int[] array2 = {2,1};
		int[] array3 = {3,2};

		 
		board1.addBlock("1 1",array1, true);
		board1.addBlock("1 1", array2, true);
		board1.addBlock("1 2", array3, true);
		
	    //test adding block onto existing block
		int[] array4 = {1,0};

		board2.addBlock("1 1",array4, true);
		try {
			board2.addBlock("1 1", array4, true);
			fail("Did Not Catch Illegal Argument Exception");
		} catch (IllegalArgumentException e) {}
		

		//testing adding block outside of board
		Board board3 = new Board(4,4);		
		int[] array5 = {10,10};
		try{
			board3.addBlock("2 2", array5, true);
			fail("not supposed to see this");
		}
		catch (IllegalArgumentException e ){
		}
	}
	public void testRemoveBlock() throws IllegalArgumentException{
		System.out.println("~~~Remove block test~~~");
		Board board4 = new Board(4,4);
		int[] array8 = {0,2};
		int[] array9 = {1,1};
		int[] array10 = {6,6};

		board4.addBlock("11", array8, true);
		board4.addBlock("11", array9, true);
		board4.removeBlock("11", array9, true);
		System.out.println(board4);
		
		//Remove block test: no block to remove
		Board board5 = new Board(4,4);
		try{
			board5.removeBlock("11", array8, true);
			fail("Couldn't catch Illegal Argument Ex for removing non-existing block");
		}catch (IllegalArgumentException e) {}
		
		
		//Remove block test: out of bounds
		Board board6 = new Board(4,4);
		try {
			board6.removeBlock("11", array10, true);
			fail("Can't catch");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		System.out.println(board6);
	}
	
	public void testFindMoves(){
		Board b = new Board(4,4);
		int[] array1 = {1,0};
		int[] array2 = {2,1};
		int[] array3 = {3,2};
		
		b.addBlock("11",array1, true);
		b.addBlock("11",array2, true);
		b.addBlock("12", array3, true);
		System.out.println("TEST FIND MOVES / not working");
		System.out.println(b.findMoves(2));
	}
	
	
	//These tests are
	public void testChangeWhiteSpaces(){
		System.out.println( "~~~AddBlock Test, change white spaces~~~");
		Board board7 = new Board(4,4);
	}
	
	public void testThisMoveWorked(){
		System.out.println("~~~test this move worked");
		Board board = new Board(4,4);
		
	}
	
	public void testIsFreeToMove(){
		System.out.println("Testing isFreeToMove");
		Board board = new Board(5,4);
		
		
	}

	public void testKeyToIntArray(){
		System.out.println("~~~testing key to int array");
		Board board = new Board(5,4);
		int[] array1 = {1,0};
		int[] array2 = {2,1};
		int[] array3 = {3,2};
	}
	
}
