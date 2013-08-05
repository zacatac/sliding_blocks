/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/5/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Solver {
    public static final boolean iamDebugging = false;



    public static void main(String[] args) {
        if (args.length == 0){
            System.err.println("You must input something");
            System.exit(1);
        }

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
        if (iamDebugging){
        System.out.println(dimLine);
        }
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

        int trayLine = 0;
        int[][] tray = new int[lengthOfFile-1][4];
        while (true){
            s = newSource.readLine ();
            if (iamDebugging){
            System.out.println(s);
            }
            if (s == null) {
                break;
            }
            tray[trayLine] = makeBlock(s, row, col, newSource.lineNumber()).getmyPos();
            trayLine++;
        }
        if (iamDebugging){
            for (int i = 0; i < tray.length; i++ ){
                int[] currentPos = tray[i];
                for (int j = 0; j <4; j++){
                   System.out.print(currentPos[j]);
                }
                System.out.println();
            }

        }
        if (iamDebugging){
            System.out.println("ROW: " + row + " COL: " +col);
        }
        Board board = new Board(row,col,tray);
    }


    private static Block makeBlock (String x, int maxRow, int maxCol, int line) {
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
            if (iamDebugging){
            System.out.println(rowUpper + "" + colUpper + "" + rowLower + "" + colLower);
            }
            if ((rowUpper < 0 || rowUpper > maxRow) || (rowLower < 0 || rowLower > maxRow)){
                System.err.println("Invalid row inputs at line " + line);
            }
            if ((colUpper < 0 || colUpper> maxCol) || (colLower < 0 || colLower > maxRow)){
                System.err.println("Invalid column inputs at line " + line);
            }
            if (rowUpper > rowLower) {
                System.err.println("The upper row must be less than or equal to the lower row. Line: "+ line);
            }
            if (colUpper < colLower) {
                System.err.println("The upper column must be greater than or equal to the lower column. LIne: "+ line);
            }

        } catch (NumberFormatException e){
            System.err.println("Each line must contain 4 numbers.");
            System.exit(1);
        }
        int[] rtn = {rowUpper,colUpper,rowLower,colLower};
//        return rtn;
        return new Block(rtn);
    }
}
