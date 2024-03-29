import java.util.*;
import java.util.Arrays;

public class Board{
	private HashMap<String, ArrayList<int[]>> board = new HashMap<String,ArrayList<int[]>> ();
	private boolean[][] notEmpty;
    private HashSet<String> moveHistory = new HashSet<String>();
    private int row, col;
    

    public Board(int numberOfRows, int numberOfColumns){
    	row = numberOfRows;
    	col = numberOfColumns;
        notEmpty = new boolean[numberOfRows][numberOfColumns];
    }

    //For constructing the goal board.
    public Board(){}

    public boolean[][] getNotEmpty(){
    	return notEmpty;
    }
    public HashMap<String,ArrayList<int[]>> getBoard(){
        return board;
    }
    public HashSet<String> getMoveHistory(){
        return moveHistory;
    }
    public void setMoveHistory(String s){
    	moveHistory.add(s);
    }
    public int[] getRowsAndColumns(){
        int[] rtn = {row,col};
        return rtn;
    }

    public void setNotEmpty(boolean[][] notEmpty){
        this.notEmpty = notEmpty;
    }
    public void setBoard(HashMap<String, ArrayList<int[]>> board){
        this.board = board;
    }

    //adds a block to the board
    public void addBlock(String blockdimension, int[] upperleft, boolean iWantToChangeNotEmpty){
    	int[] dimensions = new int[2];
    	dimensions = keyToIntArray(blockdimension);
    	if (!board.containsKey(blockdimension)) {
			ArrayList<int[]> entriesbydimention = new ArrayList<int[]>();
			entriesbydimention.add(upperleft);
			board.put(blockdimension, entriesbydimention);
		} else {
			ArrayList<int[]> entriesbydimention = board.get(blockdimension);
			for (int i = 0; i < entriesbydimention.size(); i++) {
				if (entriesbydimention.get(i)[0] > upperleft[0]) {
					entriesbydimention.add(i, upperleft);
					break;
				}
				if (entriesbydimention.get(i)[0] == upperleft[0]) {
					if (entriesbydimention.get(i)[1] > upperleft[1]) {
						entriesbydimention.add(i, upperleft);
						break;
					} else {
						if (i == entriesbydimention.size() - 1) {
							entriesbydimention.add(upperleft);
							break;
						}
					}
				} else {
					if (i == entriesbydimention.size() - 1) {
						entriesbydimention.add(upperleft);
						break;
					}
				}
			}
		}
		if (iWantToChangeNotEmpty) {
			changeWhiteSpaces(blockdimension, upperleft, true);
		}
	}

    //removes a block from the board
    public void removeBlock(String blockdimension, int[] upperleft,boolean iWantToChangeNotEmpty) {
    	ArrayList<int[]> blocks = board.get(blockdimension);
    	if (blocks == null) {
    		throw new NullPointerException("no blocks to remove");
    	}
    	int[] temp = new int[2];
    	for (int[] b:blocks) {
    		if (Arrays.toString(b).equals(Arrays.toString(upperleft))) {
    			temp = b;
    			break;
    		}
    	}
    	blocks.remove(temp);
        if (blocks.isEmpty()){
            board.remove(blockdimension);
        }
        if (iWantToChangeNotEmpty){
            changeWhiteSpaces(blockdimension,upperleft,false);
        }
    }

    public void changeWhiteSpaces(String blockdimension, int[] upperleft, boolean changeTo){
    	int[] dimensions = keyToIntArray(blockdimension);
        int blockRows = dimensions[0];
        int blockColumns = dimensions[1];

        if (upperleft[0] < 0  || upperleft[1] < 0 || upperleft[0]+blockRows > row || upperleft[1]+blockColumns > col){
            System.err.println("Internal inconsistency in changeWhiteSpaces");
        }
        for (int i = upperleft[0]; i < blockRows+upperleft[0]; i++){
            for(int j = upperleft[1]; j < blockColumns+upperleft[1]; j++){
                notEmpty[i][j] = changeTo;
            }
        }
    }

    public void addCurrentBoardToHistory(){
        this.moveHistory.add(this.toString());
    }

    @Override
	public String toString() {
		String rtn="";
		for (String i:board.keySet()) {
			String buildup=" ";
			Iterator<int[]> iter = board.get(i).iterator();
			while (iter.hasNext()) {
				int[] next = iter.next();
				buildup += next[0];
				buildup += " " + next[1] + " : ";
			}
			rtn += "[Dimension: " + i + " Blocks:" + buildup + "]";
		}
		return rtn;
	}
    
    @Override
    public boolean equals(Object o){
    	Boolean rtn = true;
        Board argBoard = null;
        try{
            argBoard = (Board)o;
        } catch (ClassCastException c ){
            return false;
        }
        if (argBoard == null){
            return false;
        }
        
        for (String i:argBoard.board.keySet()) {
        	ArrayList<int[]> goalArrayforDim = argBoard.board.get(i);
        	ArrayList<String> strings = new ArrayList();
        	for (int[] x: this.board.get(i)){
        		strings.add(Arrays.toString(x));
        	}
        	System.out.println(strings);
        	for (int[] test : goalArrayforDim) {
        		String testVar = Arrays.toString(test);
        		if (!strings.contains(testVar)){
        			return false;
        		}
        	}
        }

        return rtn;
//      for (String i:argBoard.board.keySet()) {
//    	Iterator<int[]> iter = argBoard.board.get(i).iterator();
//		while (iter.hasNext()) {
//			int[] next = iter.next();
//
//
//
//			for (int[] test: this.board.get(i)) {
//				if (!(test[0]==next[0]&&test[1]==next[1])) {
//					rtn = false;
//				}
//			}
//		}
//    }
//    return rtn;
    }

	// finds all possible moves
    public ArrayList<Move> findMoves(int depth, Move parentMove){
//if parentMove is null (initial condition) write base case

        ArrayList<Move> moves = new ArrayList<Move>();
        Set<String> keySet = board.keySet();
        Object[] keyArray = keySet.toArray();
        for (int i = 0; i < keyArray.length; i++ ){
            String key = (String)keyArray[i];
            ArrayList<int[]> blockList =  board.get(key);
            for (int j = 0; j < blockList.size(); j++){
                int[] block = blockList.get(j);

                int[] dimensions = keyToIntArray(key);
                boolean[] freeToMove = isFreeToMove(dimensions,block);

                if (freeToMove[0]){
                    if (thisMoveWorked(block,key,true,false)){
                        Move move = new Move(block[0],block[1],block[0]-1,block[1],dimensions[0],dimensions[1],depth,parentMove);
                        moves.add(move);
                    }
                }
                if (freeToMove[1]){
                    if(thisMoveWorked(block,key,true,true)){
                        Move move = new Move(block[0],block[1],block[0]+1,block[1],dimensions[0],dimensions[1],depth,parentMove);
                        moves.add(move);
                    }
                }
                if (freeToMove[2]){
                    if(thisMoveWorked(block,key,false,false)){
                        Move move = new Move(block[0],block[1],block[0],block[1]-1,dimensions[0],dimensions[1],depth,parentMove);
                        moves.add(move);
                    }

                }
                if (freeToMove[3]){
                    if(thisMoveWorked(block,key,false,true)){
                        Move move = new Move(block[0],block[1],block[0],block[1]+1,dimensions[0],dimensions[1],depth,parentMove);
                        moves.add(move);
                    }

                }
            }
        }

        return moves;
    }

    public boolean thisMoveWorked(int[] block,String key, boolean isARow, boolean isIncreasing){
        boolean rtn = false;
        int[] newBlock = new int[2];
        if (isARow && isIncreasing){
            newBlock[0] = block[0]+1;
            newBlock[1] = block[1];
        } else if (isARow && !isIncreasing){
            newBlock[0] = block[0]-1;
            newBlock[1] = block[1];
        } else if (!isARow && isIncreasing){
            newBlock[0] = block[0];
            newBlock[1] = block[1]+1;
        } else if (!isARow && !isIncreasing){
            newBlock[0] = block[0];
            newBlock[1] = block[1]-1;
        }

        this.removeBlock(key,block,false);
        this.addBlock(key,newBlock,false);
        if (!moveHistory.contains(this.toString())) {
            rtn = true;
//            System.out.println();
//            System.out.println("LALALALALALALA OVER HERERERERERERERE;laksdsl;afsdl;dsal;al;adkls;dslkl;jl");
            addCurrentBoardToHistory();
        }
        this.removeBlock(key,newBlock,false);
        this.addBlock(key,block,false);
        return rtn;
    }


     public boolean[] isFreeToMove(int[] dimensions,int[] block) {
        boolean[] moveAvailable = new boolean[4];
        moveAvailable[0] = true; //top
        moveAvailable[1] = true; //bottom
        moveAvailable[2] = true; //left
        moveAvailable[3] = true; //right
        
        

        //Check top move
        if (block[0] > 0) {
        	for (int i = block[1]; i < block[1] + dimensions[1]; i++){
                if (notEmpty[block[0]-1][i]){
                    moveAvailable[0] = false;
                }
            }
        }   else {
            moveAvailable[0] = false;
        }
        //Check bottom move
        if(block[0] + dimensions[0] < row) {
        	for (int i = block[1]; i < block[1] + dimensions[1]; i++){
                if (notEmpty[block[0]+dimensions[0]][i]){
                	moveAvailable[1] = false;
                }
            }
        }   else {
            moveAvailable[1] = false;
        }
        //Check left move
        if (block[1] > 0){
            for (int i = block[0]; i < block[0] + dimensions[0]; i++){
            	if (notEmpty[i][block[1]-1]){
                    moveAvailable[2] = false;
                }
            	
            }
        }   else {
            moveAvailable[2] = false;
        }
        
        //Check right move
        if (block[1] + dimensions[1] < col){
        	
            for (int i = block[0]; i < block[0] + dimensions[0]; i++){
                if (notEmpty[i][block[1]+dimensions[1]]){
                    moveAvailable[3] = false;
                }
            }
        } else{
            moveAvailable[3] = false;
        }

	    return moveAvailable;
    }
    //CHANGED KEY TO BE String "row + " " + col"
    public static int[] keyToIntArray(String key){
    	String[] rtnKey = key.split("\\s+");
        if (rtnKey.length != 2){
            System.out.println("Internal inconsistency in dimension keys");
        }
    	int[] rtn = new int[2];
        rtn[0] = Integer.parseInt(rtnKey[0]);
        rtn[1] = Integer.parseInt(rtnKey[1]);
        return rtn;
    }


}
