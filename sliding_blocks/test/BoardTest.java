import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import junit.framework.TestCase;


public class BoardTest extends TestCase{

    public void testKeyToIntArray(){
        Board board = new Board(2,2);
        String s4 = "1 2";
        String s1 = "2 3";
        String s2 = "234 456";
        String s3 = "1 2";


        int[] i4 = board.keyToIntArray(s4);
        int[] i1 = board.keyToIntArray(s1);
        int[] i2 = board.keyToIntArray(s2);
        int[] i3 = board.keyToIntArray(s3);

        int[] exp4 = {1,2};
        int[] exp1 = {2,3};
        int[] exp2 = {234,456};
        int[] exp3 = {1,2};

        assertArrayEquals(exp4,i4);
        assertArrayEquals(exp1,i1);
        assertArrayEquals(exp2,i2);
        assertArrayEquals(exp3,i3);

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


}
