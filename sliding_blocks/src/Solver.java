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
    public static final boolean iamDebugging = true;
    private Stack<Move> moveStack = new Stack<Move>();
    private  PriorityQueue<Move> moveQueue = new PriorityQueue<Move>(1000);
    private Board board;
    private Board goalBoard;

    public Solver(int row,int column){
        board = new Board(row,column);
        goalBoard = new Board(row,column);
    }
    public Stack<Move> getMoveStack(){
        return moveStack;
    }
    public  void setMoveStack(Stack<Move> myStack){
        this.moveStack = myStack;
    }
    public  PriorityQueue<Move> getMoveQueue() {
        return this.moveQueue;
    }
    public  void setMoveQueue(PriorityQueue<Move> myQueue){
        this.moveQueue = myQueue;
    }
    public Board getBoard() {
        return this.board;
    }
    public Board  getGoalBoard(){
        return this.goalBoard;
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
        Solver solver = new Solver(row,col);

//        Board board = new Board(row,col);
        while (true){
            String s = newSource.readLine ();
//            if (iamDebugging){
//            System.out.println(s);
//            }
            if (s == null) {
                break;
            }
            makeBlock(solver.board, s, row, col, newSource.lineNumber(), true);
        }
//        solver.board.addCurrentBoardToHistory();

        InputSource goalSource = new InputSource(args[1]); //change to args[1] when you setup the option
        //Everything else.
        Board goalBoard= new Board();
        while (true){
            String s = goalSource.readLine ();
            if (iamDebugging){
                System.out.println("GOAL LINE BY LINE: " + s);
            }
            if (s == null) {
                break;
            }
            solver.makeBlock(solver.goalBoard, s, row, col, goalSource.lineNumber(), false);
        }
        String initialconfig = solver.board.toString(); //
        solver.board.setMoveHistory(initialconfig);     //
        if (iamDebugging){
            System.out.println("INITIAL BOARD: ");
            System.out.println(solver.board);
            System.out.println("GOAL BOARD: ");
            System.out.println(solver.goalBoard);
        }
        Move firstMove = new Move(solver.goalBoard);
        solver.solveTheDamnPuzzle(solver.board, solver.goalBoard);

        
    }

    public  void solveTheDamnPuzzle (Board board, Board goalBoard){
    	if (board.equals(goalBoard)){
            theWinnersCircle();
            return;
        }

        int depth = 1;
        Move initialMove = new Move(0,0,0,0,0,0,0, null);

        moveStack.push(initialMove); //must be omitted when printing out final movestack.
        System.out.println("initialMove's myscore: " + initialMove.myScore);
        ArrayList<Move> firstAvailableMoves = board.findMoves(depth, initialMove);
        

        for (Move move:firstAvailableMoves){

            moveQueue.add(move);

        }
        System.out.println("moveQueue ~~~" + moveQueue);
        for (Move i: moveQueue) {
        	System.out.println(i+ " score " + i.myScore + " depth " + i.getInfo()[6]);
        }

        //System.out.println("WIN STATUS: " + board.equals(goalBoard));
        

        while (!moveQueue.isEmpty()){
            Move bestMove = moveQueue.poll();
            System.out.println("BEST MOVE NOW: " + bestMove);
            if (iamDebugging){
//                System.out.println("BEST MOVE: " + bestMove);
//                System.out.println("SIZE OF QUEUE: " + moveQueue.size());

            }
            System.out.println("BEFORE DOING MOVE: "+ board);
            doMove(board,bestMove);
            System.out.println("AFTER DOING MOVE: "+ board);
            

            moveStack.push(bestMove);
            
            System.out.println("moveStack ~~~" + moveStack);
            System.out.println("WIN STATUS: " + board.equals(goalBoard));
            //Winning case
            if (board.equals(goalBoard)) {
            	System.out.println("win shoul");
                theWinnersCircle();
            }
            depth = bestMove.getInfo()[6];
            depth++;
            System.out.println("MOVE HISTORY SIZE BEFORE: " + board.getMoveHistory().size());
            ArrayList<Move> nextAvailableMoves = board.findMoves(depth,bestMove);
            for (Move move: nextAvailableMoves){
                moveQueue.add(move);

            }
            System.out.println("MOVE HISTORY SIZE AFTER: " + board.getMoveHistory().size());
            System.out.println("moveQueue ~~~" + moveQueue);
            for (Move i: moveQueue) {
            	System.out.println(i+ " score " + i.myScore + " depth " + i.getInfo()[6]);
            }
        }
        System.out.println("You're shit outta luck...");
    }

    private void theWinnersCircle(){
//        System.out.println("CONGRATULATIONS! You took all the fun out of \n" +
//                "a classic puzzle by having a computer solve it for you \n" +
//                "here's your prize...\n");
        System.out.println("WIN")  ;
        ArrayList<Move> movesBackward = new ArrayList<Move>();
        while (!moveStack.isEmpty()) {
            movesBackward.add(0, moveStack.pop());
        }
        movesBackward.remove(0);
        for (Move move:movesBackward){
            int[] info = move.getInfo();
            System.out.println("[ ("+ info[0] + "," + info[1]+") ("+ info[2] + "," + info[3]+") ]");
        }
        System.exit(9001);
    }


    //"makes" and adds a block to a board
    public static void makeBlock (Board b,String x, int maxRow, int maxCol, int line,boolean onOff) {
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
//            if (iamDebugging){
//            System.out.println(rowUpper + "" + colUpper + "" + rowLower + "" + colLower);
//            }
            if ((rowUpper < 0 || rowUpper > maxRow) || (rowLower < 0 || rowLower > maxRow)){
                throw new IllegalArgumentException("Invalid row inputs at line " + line);
            }
            if ((colUpper < 0 || colUpper> maxCol) || (colLower < 0 || colLower > maxCol)){//changed from colLower>maxRow
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
        String dimensions = "";
        dimensions += rowLower - rowUpper + 1 + " ";
        dimensions += colLower - colUpper + 1;
        int[] upperLeft = new int[2];
        upperLeft[0] = rowUpper;
        upperLeft[1] = colUpper;
        b.addBlock(dimensions, upperLeft, onOff);

    }

    //Completes an actual move and updates the move.
    public  void doMove(Board board, Move nextMove){

        //This while loop will bring you back to the depth
        // at which you can complete the move you were given.
        Move currentMove = null;
        Stack<Move> travelBackDown = new Stack<Move>();
        Move travelFrom = nextMove;
        if (!moveStack.isEmpty()){
            Move topMove = moveStack.peek();
//            if (topMove.parentMove == null){
//            	return;
//            }
//            if (iamDebugging){
//                System.out.println("MOVE DEPTH: " + topMove.getInfo()[6]);
//                System.out.println("NEXT MOVE DEPTH: " + nextMove.getInfo()[6]);
//
//            }

            while (travelFrom.getInfo()[6] != 1 + topMove.getInfo()[6]){
                if (travelFrom.getInfo()[6] <= topMove.getInfo()[6]) {
                    undoMove(board,moveStack.pop());
                    topMove = moveStack.peek();
                    continue;
                } else if (travelFrom.getInfo()[6] > topMove.getInfo()[6] + 1){
                    topMove = moveStack.peek();
                    travelBackDown.push(travelFrom);
                    travelFrom = travelFrom.parentMove;
                    continue;
                } else{
                    System.out.println("WHAAAAAAA?");
                }

            }
            currentMove = moveStack.peek();
        }

        if (currentMove.equals(travelFrom.parentMove)){
            int[] info = travelFrom.getInfo();
            String blockDimension = "" + info[4] +" "+ info[5];
            int[] upperLeftPrevious = {info[0],info[1]};
            int[] upperLeftNext = {info[2],info[3]};

            board.removeBlock(blockDimension,upperLeftPrevious,true);
            board.addBlock(blockDimension,upperLeftNext,true);

        } else {
            Move commonAncsestorCandidate  = moveStack.pop();
            Move TravellingFromAncestor = travelFrom.parentMove;
                while (true){
                    if (commonAncsestorCandidate.equals(TravellingFromAncestor)){
                        while (!travelBackDown.isEmpty()){
                            Move movingMove = travelBackDown.pop();
                            moveStack.push(movingMove);
                            doMove(board, movingMove);
                        }
                        break;
                    }
                    commonAncsestorCandidate = moveStack.pop();

                    undoMove(board,commonAncsestorCandidate);

                    travelBackDown.push(TravellingFromAncestor);
                    TravellingFromAncestor = TravellingFromAncestor.parentMove;
                }
        }
    }

    public void undoMove(Board board, Move move){
        int[] info;
        String blockDimension;
        int[] upperLeftPrevious;
        int[] upperLeftNext;

        info = move.getInfo();
        blockDimension = "" + info[4] +" "+  info[5];

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
