/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/5/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Solver {



    public static void main(String[] args) {
        InputSource newSource = new InputSource(args[0]); //change to args[1] when you setup the option

        //Find the length of the file
        InputSource lengthSource = new InputSource(args[0]); //change to args[1] when you setup the option
        int lengthOfFile = 0;
        String s;
        while (true){
            s = lengthSource.readLine();
            if (s == null){
                break;
            }
            lengthOfFile++;
        }

        //Everything else.
        String dimLine = newSource.readLine();
        String[] dimArray = dimLine.split("\\s+");
        if (dimArray.length != 2){
            System.err.println("You must input rows and columns in the first line of the input file.");
        }
        int row = 0;
        int col = 0;
        try {
            row = Integer.parseInt(dimArray[0]);
            col = Integer.parseInt(dimArray[1]);
            if (row < 1 || row > 256){
                System.err.println("The row must be between 1 and 256 inclusive.");
                System.exit(1);
            }
            if (col < 1 || row > 256){
                System.err.println("The row must be between 1 and 256 inclusive.");
                System.exit(1);

            }
        } catch (NumberFormatException e){
            System.err.println("The first line must contain two numbers.");
            System.exit(1);
        }

        String s;
        int trayLine = 0;
        int[][] tray = new int[lengthOfFile][4];
        while (true){
            s = newSource.readLine ();
            if (s == null) {
                System.exit (0);
            }
            tray[trayLine] = makeBlock(s).getmyPos();
        }

    }


    private static Block makeBlock (String x) {
        int rowUpper = 0;
        int colUpper = 0;
        int rowLower = 0;
        int colLower = 0;

        String[] posArray = x.split("\\s+");
        if (posArray.length != 4){
            System.err.println("You must input the row and column of the top right \n " +
                    "and bottom left of each block");
        }
        try {
            rowUpper = Integer.parseInt(posArray[0]);
            colUpper = Integer.parseInt(posArray[1]);
            rowLower = Integer.parseInt(posArray[2]);
            colLower = Integer.parseInt(posArray[3]);

        } catch (NumberFormatException e){
            System.err.println("Each line must con");
            System.exit(1);
        }
        return new Block(rowUpper,colUpper,rowLower,colLower);
//        return new Block(null);
    }
}
