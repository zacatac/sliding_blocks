
import java.util.*;

public class Board {
	private HashMap<String, ArrayList<int[]>> board = new HashMap<String,ArrayList<int[]>> ();
	private boolean[][] whiteSpace;
	public void addBlock(String blockdimention, int[] upperleft) {
		if (!board.containsKey(blockdimention)) {
			ArrayList<int[]> entriesbydimention = new ArrayList<int[]> ();
			entriesbydimention.add(upperleft);
			board.put(blockdimention, entriesbydimention);
		} else {
			ArrayList<int[]> entriesbydimention = board.get(blockdimention);
			for (int i = 0; i<entriesbydimention.size(); i++) {
				if (entriesbydimention.get(i)[0]>upperleft[0]) {
					entriesbydimention.add(i, upperleft);
					break;
				}
				if (entriesbydimention.get(i)[0]==upperleft[0]) {
					if (entriesbydimention.get(i)[1]>upperleft[1]) {
						entriesbydimention.add(i, upperleft);
						break;
					} else {
						if (i==entriesbydimention.size()-1) {
							entriesbydimention.add(upperleft);
							break;
						}
						continue;
					}
				} else {
					if (i==entriesbydimention.size()-1) {
						entriesbydimention.add(upperleft);
						break;
					}
					continue;
				}
			}
		}
	}
	public String toString() {
		String rtn="";
		for (String i:board.keySet()) {
			/*System.out.println(i);
			System.out.println(i[0]);
			System.out.println(i[1]);
			*/
			String buildup=" ";
			Iterator<int[]> iter = board.get(i).iterator();
			while (iter.hasNext()) {
				int[] next = iter.next();
				buildup += next[0];
				buildup += next[1] + " ";
			}
			rtn += "[Dimention: " + i + " Blocks:" + buildup + "]";
		}
		return rtn;
	}
}
