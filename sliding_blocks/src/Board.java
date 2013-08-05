
public class Board {

	private int[][] tray =new int[256*256-1][4];
	private int rows, columns;
	private int count = 0;
	private boolean[][] whiteSpace;
	
	public Board(int rows, int columns) {
		whiteSpace = new boolean[rows][columns];
	}
	
	//add new Block
	public void addBlock(Block b){
		tray[count]= b.getmyPos();
		count++;
	}
	
	public void print(){
		for (int i=0; i<count;i++) {
			for (int j=0; j<4;j++) {
				System.out.print(tray[i][j] + " ");
			}
			System.out.println();
		}
	}
}
