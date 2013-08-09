import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/8/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Move implements Comparable{

    /**
     * Moves are stored as [previous row,
     *                      previous column,
     *                      next row,
     *                      next column,
     *                      dimension #rows,
     *                      dimension #columns,
     *                      depth]
     * under the field info...->
     */


    private int[] info = new int[7];
    private static Board goalBoard;
    private int myScore;
    public Move parentMove;

    public Move(Board goalBoard){
        Move.goalBoard = goalBoard;
    }


    public Move(int previousRow,     //0
                int previousColumn,  //1
                int nextRow,         //2
                int nextColumn,      //3
                int dimRows,         //4
                int dimColumns,      //5
                int depth,           //6
                Move parentMove){
        this.info[0] = previousRow;
        this.info[1] = previousColumn;
        this.info[2] = nextRow;
        this.info[3] = nextColumn;
        this.info[4] = dimRows;
        this.info[5] = dimColumns;
        this.info[6] = depth;
        this.myScore = calculateScore(this.info);
        this.parentMove = parentMove;
    }

    public Move(int previousRow,     //0
                int previousColumn,  //1
                int nextRow,         //2
                int nextColumn,      //3
                int dimRows,         //4
                int dimColumns,      //5
                int depth            //6
                    ){
        this(previousRow,
                previousColumn,
                nextRow,
                nextColumn,
                dimRows,
                dimColumns,
                depth,
                null);
    }

    public Move(int[] alreadyFormed, Move parentMove){
        this.info = alreadyFormed;
        this.myScore = calculateScore(this.info);
        this.parentMove = parentMove;
    }

    public Move(int[] alreadyFormed){
        this(alreadyFormed,null);
    }


    public int calculateScore(int[] info) {
        return 0;
    }

    public int[] getInfo(){
        return this.info;
    }


    @Override
    /**
     * Here the overridden compareTo follows uses the usual -1,0,1
     * output signature, but in this case a -1 output is given if the
     * specified object is a worse move than "this" object, in terms
     * of the "fitness" of the move. If the opposite is true then a 1
     * is returned, and if this move is of the same "fitness"
     * as the specified move then it will return 0.
     */
    public int compareTo(Object o) {
        Move specified = null;
        try {
            specified = (Move)o;
        } catch(ClassCastException c){
            System.err.println("COMPARETO: You are not comparing two Move objects, " +
                    "\n are you trying to break something?");
            System.exit(1);
        }

        if (this.myScore < specified.myScore){
            return 1;
        } else if (this.myScore > specified.myScore){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o){
        Move specifiedMove = null;
        try{
            specifiedMove = (Move)o;
        } catch(ClassCastException c){
            System.err.println("EQUALS: You are not comparing two Move objects, " +
                    "\n are you trying to break something?");
            System.exit(1);
        }
        int[] myInfo  = this.getInfo();
        int[] specified = specifiedMove.getInfo();
        boolean rtn = true;
        for (int i = 0; i < 7; i++) {
            if (myInfo[i] != specified[i]){
                rtn = false;
            }
        }
        return rtn;
    }

    @Override
    public String toString(){
        String s = "[";
        s += info[0] + " ";
        s += info[1] + " ";
        s += info[2] + " ";
        s += info[3] + " ";
        s += info[4] + " ";
        s += info[5] + " ";
        s += info[6];
        s += "]";
        return s;

    }
}
