import java.util.*;

public class Board {
	private HashMap<String, ArrayList<int[]>> board = new HashMap<String, ArrayList<int[]>>();
	private boolean[][] notEmpty;
	private HashSet<String> moveHistory = new HashSet<String>();

	public Board(int numberOfRows, int numberOfColumns) {
		notEmpty = new boolean[numberOfRows][numberOfColumns];
	}

	// For constructing the goal board.
	public Board() {
		
	}

	public HashMap<String, ArrayList<int[]>> getBoard() {
		return board;
	}

	public void addBlock(String blockdimension, int[] upperleft,
			boolean iWantToChangeNotEmpty) {
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

	public void removeBlock(String blockdimension, int[] upperleft,
			boolean iWantToChangeNotEmpty) {
		ArrayList<int[]> blocks = board.get(blockdimension);
		blocks.remove(upperleft);
		if (iWantToChangeNotEmpty) {
			changeWhiteSpaces(blockdimension, upperleft, false);
		}

	}
	
	public void changeWhiteSpaces(String blockdimension, int[] upperleft,
			boolean changeTo /*, boolean fromAddBlock*/) {
		int[] dimensions = keyToIntArray(blockdimension);
		int blockRows = dimensions[0];
		int blockColumns = dimensions[1];
		int i = upperleft[0];
		int j = upperleft[1];
		while (i < blockRows + upperleft[0]) {
			while (j < blockColumns + upperleft[1]) {
				//if (fromAddBlock){
					//if (notEmpty[i][j] == true) {
						//throw new IllegalArgumentException("Cannot overlap"); 
				//	}
			//	}
				//System.out.println(i + " " + j);
 				j++;
				//System.out.println(i + " " + j);
				
			}
			i++;
		}
	}
	
	//public void getWhiteSpace (String blockdimension, int[] upperleft, boolean wanttoget){
		
		
		
	//}

	public String toString() {
		String rtn = "";
		for (String i : board.keySet()) {
			String buildup = " ";
			Iterator<int[]> iter = board.get(i).iterator();
			while (iter.hasNext()) {
				int[] next = iter.next();
				buildup += next[0];
				buildup += next[1] + " ";
			}
			rtn += "[Dimension: " + i + " Blocks:" + buildup + "]";
		}
		return rtn;
	}

	public ArrayList<Move> findMoves(int depth) {
		depth++; // iterate the depth counter

		ArrayList<Move> moves = new ArrayList<Move>();
		Set<String> keys = board.keySet();
		for (String key : keys) {
			Iterator<int[]> iter = board.get(key).iterator();
			while (iter.hasNext()) {
				int[] block = iter.next();
				int[] dimensions = keyToIntArray(key);
				boolean[] notFree = isFreeToMove(dimensions, block);

				if (!notFree[0]) {
					if (thisMoveWorked(true, false, block, key)) {
						Move move = new Move(block[0], block[1], block[0] - 1,
								block[1], dimensions[0], dimensions[1], depth);
						moves.add(move);
					}
				}
				if (!notFree[1]) {
					if (thisMoveWorked(true, true, block, key)) {
						Move move = new Move(block[0], block[1], block[0] + 1,
								block[1], dimensions[0], dimensions[1], depth);
						moves.add(move);
					}
				}
				if (!notFree[2]) {
					if (thisMoveWorked(false, false, block, key)) {
						Move move = new Move(block[0], block[1], block[0],
								block[1] - 1, dimensions[0], dimensions[1],
								depth);
						moves.add(move);
					}

				}
				if (!notFree[3]) {
					if (thisMoveWorked(false, true, block, key)) {
						Move move = new Move(block[0], block[1], block[0],
								block[1] + 1, dimensions[0], dimensions[1],
								depth);
						moves.add(move);
					}

				}
			}
		}
		return moves;
	}

	private boolean thisMoveWorked(boolean isARow, boolean isIncreasing,
			int[] block, String key) {
		boolean rtn = false;
		int[] newBlock = { block[0] - 1, block[1] };

		this.removeBlock(key, block, false);
		this.addBlock(key, newBlock, false);
		if (moveHistory.add(board.toString())) {
			rtn = true;
		}
		this.removeBlock(key, newBlock, false);
		this.addBlock(key, block, false);
		return rtn;
	}

	private boolean[] isFreeToMove(int[] dimensions, int[] block) {
		boolean[] moveNotAvailable = new boolean[4];

		// checking for top and bottom move
		for (int i = block[1]; i < block[1] + dimensions[1]; i++) {
			// check top move
			if (notEmpty[block[0] - 1][i]) {
				moveNotAvailable[0] = true;
			}

			// check down move
			if (notEmpty[block[0] + dimensions[0] + 1][i]) {
				moveNotAvailable[1] = true;
			}
		}

		// checking for left and right move
		for (int i = block[0]; i < block[0] + dimensions[0]; i++) {
			// check left move
			if (notEmpty[i][block[1] - 1]) {
				moveNotAvailable[2] = true;
			}

			// check right move
			if (notEmpty[i][block[1] + dimensions[1] + 1]) {
				moveNotAvailable[3] = true;
			}
		}
		return moveNotAvailable;
	}

	private int[] keyToIntArray(String key) {
		int[] rtn = new int[2];
		rtn[0] = Integer.parseInt(key.substring(0, 1));
		rtn[1] = Integer.parseInt(key.substring(1));
		return rtn;
	}
}
