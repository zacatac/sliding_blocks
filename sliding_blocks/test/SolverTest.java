/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/8/13
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
import junit.framework.TestCase;


public class SolverTest extends TestCase {
    public void testimport() {
        Solver s = new Solver();

        String[] args = new String[2];
        args[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.4.txt";
        args[1] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.4.goal.txt";

        s.main(args);
//
//        String[] args1 = new String[2];
//        args1[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.3.txt";
//        s.main(args1);
//
//        String[] args2 = new String[2];
//        args2[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.2.txt";
//        s.main(args2);
//
//        String[] args3 = new String[2];
//        args3[0] = "/Users/zrfield/GitHub/sliding_blocks/sliding_blocks/test/big.block.1.txt";
//        s.main(args3);
    }

}
