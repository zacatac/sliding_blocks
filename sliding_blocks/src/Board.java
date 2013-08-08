import java.util.ArrayList;
import java.util.HashSet;

public class Board {

	private int[][] tray;
    private boolean[][] whiteSpace;
    private int[][] movesSoFar;
    private HashSet<String> movesCompleted;

    public Board(int[][] tray, boolean[][] whiteSpace) {
		this.tray = tray;
        if (whiteSpace == null){
            whiteSpace = new boolean[tray.length][tray[0].length];
            this.whiteSpace = whiteSpace;
        } else {
            this.whiteSpace = whiteSpace;
        }
        movesSoFar = new int[tray.length][4];
    }

    // All blocks are added at the start in the constructor, which accepts
    // a full tray
    /*
	//add new Block, whitespaces are true when they are unoccupied
	public void addBlock(Block b){
		this.updateSpaces(b.getmyPos(), false);
		tray[count]= b.getmyPos();
		count++;
	}
	*/

	//helper method that takes in a target rectangle and fills in the space with T/F
	public void updateSpaces(int[] target, boolean b) {    //really good idea using an optional T/F
		for (int i = target[0]; i < target[2]; i++){
			for (int j = target[1]; j < target[3]; j++){
				whiteSpace[i][j] = b;
			}
		}
	}

    /**
     * Will test if the adjacent boxes of a particular block are free.
     * boolean[0] top is free
     * boolean[1] bottom is free
     * boolean[2] left is free
     * boolean[3] right is free
     *
     * @param block
     * @param notEmpty
     * @return boolean[]
     */
    private boolean[] adjacentFree (int[] block, boolean[][] notEmpty){ //not empty because it is a reverse boolean array
        boolean[] rtn = new boolean[4];
        //top is free
        if (block[0] != 0 ){
            for (int i = block[1]; i < block[3]; i++){
                if (notEmpty[block[0]-1][i]){
                    break;
                }
            }
            rtn[0] = true;
        }

        //bottom is free
        if (block[2] < notEmpty.length){
            for (int i = block[1]; i < block[3]; i++) {
                if (notEmpty[block[2]+1][i]){
                    break;
                }
            }
            rtn[1] = true;
        }

        //left is free
        if (block[1] != 0) {
            for (int i = block[0]; i < block[2]; i++){
                if (notEmpty[block[1]-1][i]){
                    break;
                }
            }
            rtn[2] = true;
        }

        //right is free
        if (block[3] < notEmpty[0].length){
            for (int i = block[0]; i < block[2]; i++) {
                if (notEmpty[block[3]+1][i]){
                    break;
                }
            }
            rtn[3] = true;
        }

        return rtn;
    }

    /**
     * Creates all of the next possible move configurations for a single block
     * Makes calls to adjacentFree to discover legal moves.
     * Returns the 4 (at max, 0 at min) new tray
     * configurations that are possible in the form of a three dimensional array.
     */
    public ArrayList<int[]> nextMoves(int[][] tray, boolean[][] notEmpty){
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        int[] block;
        boolean[] myMoves;
        String moveString;


        for (int i = 0; i < tray.length; i++){
            block = tray[i];
            myMoves = adjacentFree(block,notEmpty);
            if (myMoves[0]){
                int[] newMove = {block[0],block[1],block[0]-1,block[1],block[2]-block[0],block[3]-block[1]};
                moveString = "";
                for (int j = 0; j < 6; j++){
                    moveString += newMove[i];
                }
                if (movesCompleted.add(moveString)){
                    possibleMoves.add(newMove);
                }
            }
            if (myMoves[1]){
                    int[] newMove = {block[0],block[1],block[0]-1,block[1],block[2]-block[0],block[3]-block[1]};
                    moveString = "";
                    for (int j = 0; j < 6; j++){
                        moveString += newMove[i];
                    }
                    if (movesCompleted.add(moveString)){
                        possibleMoves.add(newMove);
                    }
            }
            if (myMoves[2]){
                int[] newMove = {block[0],block[1],block[0]-1,block[1],block[2]-block[0],block[3]-block[1]};
                moveString = "";
                for (int j = 0; j < 6; j++){
                    moveString += newMove[i];
                }
                if (movesCompleted.add(moveString)){
                    possibleMoves.add(newMove);
                }
            }
            if (myMoves[3]){
                int[] newMove = {block[0],block[1],block[0]-1,block[1],block[2]-block[0],block[3]-block[1]};
                moveString = "";
                for (int j = 0; j < 6; j++){
                    moveString += newMove[i];
                }
                if (movesCompleted.add(moveString)){
                    possibleMoves.add(newMove);
                }
            }
        }
        return possibleMoves;
    }
}
