import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;


public class BoardTest extends TestCase{

    public void testaddBlock(){
	  
	  System.out.println( "~~~AddBlock Test, basic board printing~~~");
		Board board1 = new Board(4,4);
		
		int[] array1 = {1,0};
		int[] array2 = {2,1};
		int[] array3 = {3,2};
	
		board1.addBlock("11",array1, true);
		board1.addBlock("11", array2, true);
		board1.addBlock("12", array3, true);
		
		System.out.println(board1);
		
	}

	public void testaddBlock1(){
	  
	    System.out.println( "~~~AddBlock Test, overlapping blocks~~~");
		Board board1 = new Board(4,4);
		
		int[] array1 = {1,0};
		int[] array2 = {1,0};
		
		board1.addBlock("11",array1, true);
		try{
			board1.addBlock("11", array2, true);
			System.out.println("not supposed to see this");
		}
		catch (IllegalArgumentException e ){
			
		}
			System.out.println(board1);
	}
	
	
	public void testaddBlock2(){
		System.out.println("~~~AddBlock Test, out of bounds blocks~~~");
		Board board2 = new Board(4,4);
	
		int[] array1 = {1,0};
		int[] array2 = {10,10};
	
		board2.addBlock("12",array1, true);
	
		try{
		board2.addBlock("22", array2, true);
		System.out.println("not supposed to see this");
		}
		catch (IllegalArgumentException e ){
		
		}
			System.out.println(board2);

	}
	public void testaddBlock3(){
		System.out.println("~~~AddBlock Test, out of bounds blocks~~~");
		Board board3 = new Board(4,4);
	
		int[] array1 = {1,0};
		int[] array2 = {10,10};
	
		board3.addBlock("12",array1, true);
	
		try{
		board3.addBlock("22", array2, true);
		System.out.println("not supposed to see this");
		}
		catch (IllegalArgumentException e ){
		
		}
			System.out.println(board3);

	}
	
}
