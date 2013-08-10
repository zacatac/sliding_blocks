import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;


public class BoardTest extends TestCase{

	public void testAddBlock(){
	  
	  System.out.println( "~~~AddBlock Test, basic board printing~~~");
		Board board1 = new Board(4,4);
		
		int[] array1 = {1,0};
		int[] array2 = {2,1};
		int[] array3 = {3,2};
	
		board1.addBlock("11",array1, true);
		board1.addBlock("11", array2, true);
		board1.addBlock("12", array3, true);
		
		System.out.println(board1);

	  
	    System.out.println( "~~~AddBlock Test, overlapping blocks~~~");
		Board board2 = new Board(4,4);
		
		int[] array4 = {1,0};
		int[] array5 = {1,0};
		
		board2.addBlock("11",array4, true);
		try{
			board2.addBlock("11", array5, true);
			System.out.println("not supposed to see this");
		}
		catch (IllegalArgumentException e ){
			
		}
			System.out.println(board2);

			
		System.out.println("~~~AddBlock Test, out of bounds blocks~~~");
		Board board3 = new Board(4,4);
	
		int[] array6 = {1,0};
		int[] array7 = {10,10};
	
		board3.addBlock("12",array6, true);
	
		try{
		board3.addBlock("22", array7, true);
		System.out.println("not supposed to see this");
		}
		catch (IllegalArgumentException e ){
		
		}
			System.out.println(board3);

	}
	public void testRemoveBlock(){
		System.out.println("~~~Remove block test~~~");
		Board board4 = new Board(4,4);
		int[] array8 = {0,2};
		int[] array9 = {1,1};
		
		board4.addBlock("11", array8, true);
		board4.addBlock("11", array9, true);
		board4.removeBlock("11", array9, true);
		
		System.out.println(board4);
	}
	
}
