
import java.util.*;

public class Board {
	private HashMap<int[], ArrayList<int[]>> board = new HashMap<int[],ArrayList<int[]>> ();
	private boolean[][] whiteSpace;
	public void addBlock(int[] blockdimention, int[] upperleft) {
		if (!board.containsKey(blockdimention)) {
			ArrayList<int[]> entriesbydimention = new ArrayList<int[]> ();
			entriesbydimention.add(upperleft);
			board.put(blockdimention, entriesbydimention);
		} else {
			ArrayList<int[]> entriesbydimention = board.get(blockdimention);
			for (int i = 0; i<entriesbydimention.size(); i++) {
				if (entriesbydimention.get(i)[0]>blockdimention[0]) {
					entriesbydimention.add(i, blockdimention);
					break;
				}
				if (entriesbydimention.get(i)[0]==blockdimention[0]) {
					if (entriesbydimention.get(i)[1]>blockdimention[1]) {
						entriesbydimention.add(i, blockdimention);
						break;
					} else {
						if (i==entriesbydimention.size()-1) {
							entriesbydimention.add(blockdimention);
							break;
						}
						continue;
					}
				} else {
					if (i==entriesbydimention.size()-1) {
						entriesbydimention.add(blockdimention);
						break;
					}
					continue;
				}
			}
		}
	}
	public String toString() {
		String rtn="";
		for (int[] i:board.keySet()) {
			/*System.out.println(i);
			System.out.println(i[0]);
			System.out.println(i[1]);
			*/
			String buildup="";
			Iterator<int[]> iter = board.get(i).iterator();
			while (iter.hasNext()) {
				int[] next = iter.next();
				buildup += next[0];
				buildup += next[1] + " ";
			}
			rtn += "Dimention: " + i[0] + "," + i[1] + " Blocks" + buildup;
		}
		return rtn;
	}
}
