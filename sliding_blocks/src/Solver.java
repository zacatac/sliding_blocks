import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zrfield
 * Date: 8/5/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
// GITHUB TEST - CHERYL
public class Solver {
    public static final boolean iamDebugging = false;
    private static Stack<Move> moveStack;
    private static PriorityQueue<Move> moveQueue;


    public Stack<Move> getMoveStack(){
        return moveStack;
    }
    public static void setMoveStack(Stack<Move> myStack){
        moveStack = myStack;
    }
    public static PriorityQueue<Move> getMoveQueue() {
        return moveQueue;
    }
    public static void setMoveQueue(PriorityQueue<Move> myQueue){
        moveQueue = myQueue;
    }

    public static void main(String[] args) {
        if (args.length == 0){
            System.err.println("You must input something");
            System.exit(1);
        }

        InputSource newSource = new InputSource(args[0]); //change to args[1] when you setup the option
        //Everything else.
        String dimLine = newSource.readLine();
        String[] dimArray = dimLine.split("\\s+");
//        if (iamDebugging){
//        System.out.println(dimLine);
//        }
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
        } catch (NumberFormatException e){
            System.err.println("The first line must contain two numbers.");
            System.exit(1);
        }
        Board board = new Board(row,col);
        while (true){
            String s = newSource.readLine ();
//            if (iamDebugging){
//            System.out.println(s);
//            }
            if (s == null) {
                break;
            }
            makeBlock(board, s, row, col, newSource.lineNumber(),true);
        }

        InputSource goalSource = new InputSource(args[1]); //change to args[1] when you setup the option
        //Everything else.
        Board goalBoard= new Board();
        while (true){
            String s = goalSource.readLine ();
//            if (iamDebugging){
//                System.out.println(s);
//            }
            if (s == null) {
                break;
            }
            makeBlock(goalBoard, s, row, col, goalSource.lineNumber(),false);
        }

        if (iamDebugging){
            System.out.println("INITIAL BOARD: ");
            System.out.println(board);
            System.out.println("GOAL BOARD: ");
            System.out.println(goalBoard);
        }
        Move firstMove = new Move(goalBoard);

        solveTheDamnPuzzle(board,goalBoard);

        
    }

    public static void solveTheDamnPuzzle (Board board, Board goalBoard){
        int depth = 1;

        ArrayList<Move> firstAvailableMoves = board.findMoves(depth);
        for (Move move:firstAvailableMoves){

            moveQueue.add(move);
        }

        while (!moveQueue.isEmpty()){
            Move bestMove = moveQueue.poll();

            if (iamDebugging){
                System.out.println("BEST MOVE: " + bestMove);
            }
            doMove(board,bestMove);

            moveStack.push(bestMove);


            //Winning case
            if (board.equals(goalBoard)) {
                System.out.println("CONGRATULATIONS! You took all the fun out of \n" +
                        "a classic puzzle by having a computer solve it for you \n" +
                        "here's your prize...\n");
                ArrayList<Move> movesBackward = new ArrayList<Move>();
                while (!moveStack.isEmpty()) {
                    movesBackward.add(0, moveStack.pop());
                }
                for (Move move:movesBackward){
                int[] info = move.getInfo();
                System.out.println("[ ("+ info[0] + "," + info[1]+") ("+ info[2] + "," + info[3]+") ]");
                }
            }
            depth = bestMove.getInfo()[6]++;
            ArrayList<Move> nextAvailableMoves = board.findMoves(depth);
            for (Move move: nextAvailableMoves){
                moveQueue.add(move);
            }
        }
        System.out.println("You're shit outta luck...");
    }


    //"makes" and adds a block to a board
    public static void makeBlock (Board b, String x, int maxRow, int maxCol, int line,boolean onOff) {
        int rowUpper = 0;
        int colUpper = 0;
        int rowLower = 0;
        int colLower = 0;

        if (x == null){
            throw new IllegalArgumentException("You must pass in a string");
        }
        String[] posArray = x.split("\\s+");
        if (posArray.length != 4){
            throw new IllegalArgumentException("You must input the row and column of the top right +\n" +
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
                throw new IllegalArgumentException("Invalid row inputs at line " + line);
            }
            if ((colUpper < 0 || colUpper> maxCol) || (colLower < 0 || colLower > maxRow)){
                throw new IllegalArgumentException("Invalid column inputs at line " + line);
            }
            if (rowUpper > rowLower) {
                throw  new IllegalArgumentException("The upper row must be less than or equal to the lower row. Line: "+ line);
            }
            if (colUpper > colLower) {
                throw new IllegalArgumentException("The upper column must be greater than or equal to the lower column. LIne: "+ line);
            }

        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Each line must contain 4 numbers.");
        }
        String dimentions = "";
        dimentions += rowLower - rowUpper + 1;
        dimentions += colLower - colUpper + 1;
        int[] upperLeft = new int[2];
        upperLeft[0] = rowUpper;
        upperLeft[1] = colUpper;
        b.addBlock(dimentions, upperLeft,onOff);
        
    }

    //Completes an actual move and updates the move.
    public static void doMove(Board board, Move move){
        int[] info = move.getInfo();
        int depth = info[6];
        String blockDimension = "" + info[4] + info[5];
        int[] upperLeftPrevious = {info[0],info[1]};
        int[] upperLeftNext = {info[2],info[3]};

        //This while loop will bring you back to the depth
        // at which you can complete the move you were given.
        Move currentMove = null;
        if (!moveStack.isEmpty()){
            Move topMove = moveStack.peek();
            while (!moveStack.isEmpty() || depth >= topMove.getInfo()[6]){
                undoMove(board,moveStack.pop());
                topMove = moveStack.peek();
            }
            currentMove = moveStack.peek();
        }
        if (moveStack.isEmpty() || currentMove.equals(move.parentMove)){
            board.removeBlock(blockDimension,upperLeftPrevious,true);
            board.addBlock(blockDimension,upperLeftNext,true);
        } else {
            Move commonAncsestor  = moveStack.pop();
            Move bestMoveAncestor = move.parentMove;
            Stack<Move> travelBackDown = new Stack<Move>();
            while (!moveStack.isEmpty() && bestMoveAncestor != null){
                moveQueue.add(commonAncsestor);
                commonAncsestor = moveStack.pop();

                undoMove(board,commonAncsestor);

                travelBackDown.push(bestMoveAncestor);
                bestMoveAncestor = bestMoveAncestor.parentMove;

                if (commonAncsestor.equals(bestMoveAncestor)){
                    while (!travelBackDown.isEmpty()){
                        Move movingMove = travelBackDown.pop();
                        moveStack.push(movingMove);
                        doMove(board, movingMove);
                    }
                } else {
                    continue;
                }

            }
        }
    }

    public static void undoMove(Board board, Move move){
        //Remove the children from the queue here.
        int[] info;
        String blockDimension;
        int[] upperLeftPrevious;
        int[] upperLeftNext;

        moveQueue.add(move); //YOU MUST ADD THIS MOVE BACK TO THE QUEUE!
        info = move.getInfo();
        blockDimension = "" + info[4] + info[5];

        upperLeftPrevious = new int[2];
        upperLeftPrevious[0] = info[0];
        upperLeftPrevious[1] = info[1];

        upperLeftNext = new int[2];
        upperLeftNext[0] = info[2];
        upperLeftNext[1] = info[3];

        board.removeBlock(blockDimension,upperLeftNext,true);
        board.addBlock(blockDimension, upperLeftPrevious, true);
    }
}
