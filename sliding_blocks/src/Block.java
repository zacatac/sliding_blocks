
public class Block {
	private int[] position;
	private int height, width; //height is num of rows, width is num of col
	private int upLeftRow, upLeftCol, lowRightRow, lowRightCol; // elements in position array
	
	//initialize a block
	public Block(int[] pos) {
		position = pos;
		upLeftRow = position[0];
		upLeftCol = position[1];
		lowRightRow = position[2];
		lowRightCol = position[3];
		height = lowRightRow - upLeftRow + 1;
		width = lowRightCol - upLeftCol + 1;
	}
	
	public int[] getmyPos(){
		return position;
	}


	public static void main(String args[]){
	
	}
	
}
