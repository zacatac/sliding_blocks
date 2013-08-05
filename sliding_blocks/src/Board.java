
public class Board {

	private int[][] tray =new int[256*256-1][4];
	int rows, columns;
	int count = 0;
	private boolean[][] whiteSpace;
	
	public Board(int rows, int columns, int[][] tray) {
		whiteSpace = new boolean[rows][columns];
		int[] init = new int[4];
		init[0] = 0;
		init[1] = 0;
		init[2] = rows-1;
		init[3] = columns-1;
		this.updateSpaces(init, true);
	}
	public Board(int rows, int columns, int[][] tray, boolean[][] whiteSpace) {
		whiteSpace = new boolean[rows][columns];
	}
	
	//add new Block, whitespaces are true when they are unoccupied
	public void addBlock(Block b){
		this.updateSpaces(b.getmyPos(), false);
		tray[count]= b.getmyPos();
		count++;
	}
	
	//helper method that takes in a target rectangle and fills in the space with T/F
	public void updateSpaces(int[] target, boolean b) {
		for (int i = target[0]; i < target[2]; i++){
			for (int j = target[1]; j < target[3]; j++){
				whiteSpace[i][j] = b;
			}
		}
	}
	public void print(){
		for (int i=0; i<count;i++) {
			for (int j=0; j<4;j++) {
				System.out.print(tray[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
