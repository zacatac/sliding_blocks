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

    //adds a block to the board
    public void addBlock(String blockdimension, int[] upperleft, boolean iWantToChangeNotEmpty){
    	int[] dimensions = new int[2];
    	dimensions = keyToIntArray(blockdimension);
    	if (upperleft[0] < 0 || upperleft[0] + dimensions[0] > row +1 || upperleft[1] < 0 
    			|| upperleft[1] > col +1) {
    		System.err.println("Cannot add Block on top of an existing block");
    		System.exit(1);
    	}
    	
    	
    	//This for loop is supposed to catch adding overlapping blocks
    	//either throw a new illegal argument exception 
    	for (int i = upperleft[0]; i < upperleft[0] + dimensions[0]; i++){
    		for (int j = upperleft[1]; i < upperleft[1] + dimensions[1]; j++){
    			if (notEmpty[i][j] == true) { // THIS LINE IS BUGGY
    				throw new ArrayIndexOutOfBoundsException("Cannot have overlapping blocks");
    			}
    		}
    	}
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
						continue;
					}
				} else {
					if (i == entriesbydimention.size() - 1) {
						entriesbydimention.add(upperleft);
						break;
					}
					continue;
				}
			}
		}
		if (iWantToChangeNotEmpty) {
			changeWhiteSpaces(blockdimension, upperleft, true);
		}
	}

    //removes a block from the board
    public void removeBlock(String blockdimension, int[] upperleft,boolean iWantToChangeNotEmpty) {
    	int[] dimensions = new int[2];
    	dimensions = keyToIntArray(blockdimension);
    	if (upperleft[0] < 0 || upperleft[0] + dimensions[0] > row +1 || upperleft[1] < 0 
    			|| upperleft[1] > col +1) {
    		throw new ArrayIndexOutOfBoundsException("cannot remove block from outside board");
    	}
    	for (int i = upperleft[0]; i < upperleft[0] + dimensions[0]; i++){
    		for (int j = upperleft[1]; i < upperleft[1] + dimensions[1]; j++){
    			if (notEmpty[i][j] == false){
    				System.err.println("Cannot remove a block that does not exist");
    				System.exit(1);
    			}
    		}
    	}

    	ArrayList<int[]> blocks = board.get(blockdimension);
    	if (blocks == null) {
    		throw new NullPointerException("no blocks to remove");
    	}
    	blocks.remove(upperleft);
        
        if (iWantToChangeNotEmpty){
            changeWhiteSpaces(blockdimension,upperleft,false);
        }


    }

    //changes the whitespace, takes in 
    public void changeWhiteSpaces(String blockdimension, int[] upperleft, boolean changeTo){
    	int[] dimensions = keyToIntArray(blockdimension);
        int blockRows = dimensions[0];
        int blockColumns = dimensions[1];
        int i = upperleft[0];
        int j = upperleft[1];
//        for (int i = upperleft[0]; i <= blockRows + upperleft[0]-1; i++){
//        	Arrays.fill(notEmpty[i][], upperleft[1], upperleft[1] + blockColumns -1, changeTo);
//        }
        
        while (i <= blockRows+upperleft[0]-1){
            while(j <= blockColumns+upperleft[1]-1){
                if (changeTo == true) {
                	notEmpty[i][j] = true;
                } else {
                	notEmpty[i][j] = false;
                }
                j= j +1;
            }
            i = i + 1;;
        }
    }


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


	// finds all possible moves
    public ArrayList<Move> findMoves(int depth){
        depth++;  //iterate the depth counter

        ArrayList<Move> moves = new ArrayList<Move>();
        Set<String> keys = board.keySet();
        for (String key:keys){
            Iterator<int[]> iter = board.get(key).iterator();
            while (iter.hasNext()){
                int[] block = iter.next();
                int[] dimensions = keyToIntArray(key);
                boolean[] notFree = isFreeToMove(dimensions,block);

                if (!notFree[0]){
                    if (thisMoveWorked(block,key)){
                        Move move = new Move(block[0],block[1],block[0]-1,block[1],dimensions[0],dimensions[1],depth);
                        moves.add(move);
                    }
                }
                if (!notFree[1]){
                    if(thisMoveWorked(block,key)){
                        Move move = new Move(block[0],block[1],block[0]+1,block[1],dimensions[0],dimensions[1],depth);
                        moves.add(move);
                    }
                }
                if (!notFree[2]){
                    if(thisMoveWorked(block,key)){
                        Move move = new Move(block[0],block[1],block[0],block[1]-1,dimensions[0],dimensions[1],depth);
                        moves.add(move);
                    }

                }
                if (!notFree[3]){
                    if(thisMoveWorked(block,key)){
                        Move move = new Move(block[0],block[1],block[0],block[1]+1,dimensions[0],dimensions[1],depth);
                        moves.add(move);
                    }

                }
            }
        }
        return moves;
    }

    private boolean thisMoveWorked(int[] block,String key){
        boolean rtn = false;
        int[] newBlock = {block[0]-1,block[1]};

        this.removeBlock(key,block,false);
        this.addBlock(key,newBlock,false);
        if (moveHistory.add(board.toString())) {
            rtn = true;
        }
        this.removeBlock(key,newBlock,false);
        this.addBlock(key,block,false);
        return rtn;
    }


    private boolean[] isFreeToMove(int[] dimensions,int[] block) {
        boolean[] moveNotAvailable = new boolean[4];
        
        //checking for top move - block[1] is num of row
        if (block[0] - 1 >= 0){
        	for (int i = block[1]; i < block[1] + dimensions[1]; i++){
        		if (notEmpty[block[0]-1][i]){
                    moveNotAvailable[0] = true;
                }
        	}
        } else {
        	moveNotAvailable[0] = true;
        }
        
        //checking for bottom move
        if (block[0] + dimensions[0] <= row){
        	for (int i = block[1]; i < block[1] + dimensions[1]; i++){
        		if (notEmpty[block[0]+1][i]){
                    moveNotAvailable[0] = true;
                }
        	}
        } else {
        	moveNotAvailable[1] = true;
        }
        
        //checking for left move
        if (block[1] - 1 >= 0){
        	for (int i = block[0]; i < block[0] + dimensions[0]; i++){
        		if (notEmpty[i][block[1]-1]){
                  moveNotAvailable[2] = true;
                }
        	}
        } else {
        	moveNotAvailable[2] = true;
        }
        
        //checking for right move
        if (block[1] + dimensions[1] <= col){
        	for (int i = block[0]; i < block[0] + dimensions[0]; i++){
                if (notEmpty[i][block[1]+dimensions[1]+1]){
                	moveNotAvailable[3] = true;
                }
        	}
        } else {
        	moveNotAvailable[3] = true;
        }
        System.out.println("movenotavail" +Arrays.toString(moveNotAvailable));
	    return moveNotAvailable;
	}
        
        
    
    //CHANGED KEY TO BE String "row + " " + col"
    private int[] keyToIntArray(String key){
    	String[] rtnKey = key.split("\\s+");
    	int[] rtn = new int[2];
        rtn[0] = Integer.parseInt(rtnKey[0]);
        rtn[1] = Integer.parseInt(rtnKey[1]);
        return rtn;
    }
    
    //TESTING PURPOSES ONLY
    public static void main(String args[]){
    	Board b = new Board(5,4);
    	//upperleft row and col arrays
    	int[] array1 = {0,0};
		int[] array2 = {2,1};
		int[] array3 = {3,2};

		b.addBlock("1 1", array1, true);
		b.addBlock("1 1", array1, true);
		b.addBlock("1 2", array2, true);
		b.addBlock("1 1",array3, true);
		
		System.out.println(b);
		int[] dimensions = b.keyToIntArray("1 1");
		int[] block = array1;
		int[] block2 = array2;
		b.isFreeToMove(dimensions, block);
		b.isFreeToMove(dimensions, block2);
    }

}
