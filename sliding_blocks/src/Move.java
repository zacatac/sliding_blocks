import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/8/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Move implements Comparable {

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
    private static HashMap<String,ArrayList<int[]>> goalBoard;
    private static int goalRows, goalColumns;
    protected int myScore;
    public Move parentMove;

    public Move(Board goalBoard){
        Move.goalBoard = goalBoard.getBoard();
        int[] rowsANDcolumns = goalBoard.getRowsAndColumns();
        Move.goalRows = rowsANDcolumns[0];
        Move.goalColumns = rowsANDcolumns[1];
    }

    //MAY HAVE TO HANDLE INTEGER OVERFLOWS FOR THE DEPTH!
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
        this.parentMove = parentMove;
        if (this.parentMove!=null) {
        	this.myScore = calculateScore(this.info);
        } else {
        	this.myScore = 9000001;
        }
        
    }


    public Move(boolean dummy, Move parentMove){//delete this
    	this.myScore = 9001;
    }
    public Move(int[] alreadyFormed, Move parentMove){
        this.info = alreadyFormed;
        this.myScore = calculateScore(this.info);
        this.parentMove = parentMove;
    }

    public Move(int[] alreadyFormed){
        this(alreadyFormed,null);
    }
    
    public static int calculateScore(int[] info) {
    	int toGoal = goalRows + goalColumns;//0;  //can be optimized
        int fromStart = info[6];
        String key = info[4] + " " + info[5];
        ArrayList<int[]> blocks = null;
        for (String i:goalBoard.keySet()) {
        	if (i.equals(key)) {
        		blocks = goalBoard.get(i);
        	}
        }
        
        //System.out.println("blocks" + blocks);
      
//        System.out.println("Size " + blocks.size());
//        for (int[] i:blocks){
//        	System.out.println(Arrays.toString(i));
//        }
//        
        if (!(blocks == null)){

            System.out.println("DAS EST EIN TEST3");
            Iterator<int[]> iter = blocks.iterator();
            while (iter.hasNext()){
                int[] thisBlock = iter.next();
                System.out.println("next block " + Arrays.toString(thisBlock));
                int temp = Math.abs(info[2]-thisBlock[0])  + Math.abs(info[3] - thisBlock[1]);
//                if (temp==0) {
//                	return 9001;//LARGE SCORE ARBRITARY ATM since if we're at the goal don't want to move
//                }
                if (temp < toGoal){
                    toGoal = temp;
                }
            }
        }
        System.out.println("toGoal " + toGoal);
        System.out.println("fromStart " + fromStart);
        //System.out.println(fromStart + toGoal + "     SCORE:   ");
        //myScore = fromStart + toGoal;
        return fromStart + toGoal;
//        return 0;
    }

    public int[] getInfo(){
        return this.info;
    }


    @Override
    public boolean equals(Object o){
        Move specifiedMove = null;
        try{
            specifiedMove = (Move)o;
        } catch(ClassCastException c){
            System.err.println("EQUALS: You are not comparing two Move objects, " +
                    "\n are you trying to break something?");
            return false;
        }
        if (specifiedMove == null){
            return false;
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

    @Override
    public int compareTo(Object o) {
        MoveComparator mc = new MoveComparator();
        return mc.compare(this,(Move)o);
    }

    private class MoveComparator implements Comparator<Move>{

        @Override
        /**
         * Here the overridden compareTo follows uses the usual -1,0,1
         * output signature, but in this case a -1 output is given if the
         * specified object is a worse move than "this" object, in terms
         * of the "fitness" of the move. If the opposite is true then a 1
         * is returned, and if this move is of the same "fitness"
         * as the specified move then it will return 0.
         */
        public  int compare(Move o1, Move o2) {
            Move specified = null;
            if (o1.myScore < o2.myScore){
                return 1;
            } else if (o1.myScore > o2.myScore){
                return -1;
            } else {
                return 0;
            }
        }
    }
}
