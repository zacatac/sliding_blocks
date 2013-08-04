
public class Block {
  private int[] myPos = new int[4];
	private String myString;
	private int length, width;
	
	public Block(String x) {
		myString = x;
		String[] input = x.split("\\s+");
		for (int i=0;i<4;i++) {
			this.myPos[i] = Integer.parseInt(input[i]);
		}
	}
	
	public int[] getmyPos(){
		return myPos;
	}
	
	public String toString() {
		return myString;
	}
}
