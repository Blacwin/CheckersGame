package test;

import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.opponents.Ai;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;

public class AiTest {

    static Runtime rt;
    static long startMem;
    static long finishMem;
    static long statTime;
    static long finishTime;
    static int testCount = 0;

    static Board board;

    public static void main(String[] args) {
        rt = Runtime.getRuntime();
        Ai[] ais = aiCreator();
        board = new Board(8);
        board.initBoard(PieceColor.LIGHT, PieceColor.DARK);

//        assertTest(1000, 1, 1, true);
        while(true) {}

//        System.out.printf("%n%n%d%n%s", i, board.toString());
    }

    /**
     * Test case handler function.
     * @param numOfTests The number of test cases you want to run.
     * @param fromDif The start level of the tested computer opponent. (0 <= fromDif <= 6)
     * @param toDif The highest level of the tested computer opponent. (0 <= toDid <= 6)
     * @param resetBoard Reset the game board to default in every test case.
     */
    public static void assertTest(int numOfTests, int fromDif, int toDif, boolean resetBoard) {
        long memo = (rt.totalMemory() - rt.freeMemory())/1024;
        Ai[] ais = aiCreator();
        int level = fromDif;
        System.out.printf("%S (Reset Board: %b)%n%n", "assert test start", resetBoard);
        try {
            if(fromDif < 0 || fromDif > 6 || toDif < 0 || toDif > 6) {
                throw new ArrayIndexOutOfBoundsException("fromDif and toDif params must be in range 1 to 6");
            }
            for(int i=0; i<numOfTests; i++) {
                testCount++;
                startMem = (rt.totalMemory() - rt.freeMemory());
                statTime = System.currentTimeMillis();

//                board.movePieceByAiMove(ais[level].calculateStep(board).get(0));
//                String[] arr = new String[1000];

                finishMem = (rt.totalMemory() - rt.freeMemory());
                finishTime = System.currentTimeMillis();

                //testResult(level);

                if(toDif > level) ++level;
                if(resetBoard) board.initBoard(PieceColor.LIGHT, PieceColor.DARK);
            }
        }catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Start memory size: " + memo);
        System.out.println("Finished memory size: " + (rt.totalMemory() - rt.freeMemory())/1024);
    }

    /**
     * This method creates and fill an array of AIs. The return array contains 7 different computer oppenents.
     * The first (ais[0]) is an Ai with difficulty level 1 (easiest level) and the last (ais[6] is an Ai with
     * difficulty level 7 (hardest difficulty).
     * @return an array containing different level of Ai objects.
     */
    public static Ai[] aiCreator() {
        Ai[] ais = new Ai[7];
        for(int i=0; i<7; i++) {
            ais[i] = new Ai(PieceColor.DARK, PieceDirection.UP, 8, i+1, false);
        }
        return ais;
    }

    public static void testResult(int level) {
        String header = "#" + testCount + " difficult: " + level;
        System.out.printf("%-20s|%10s%10s%n", header, "memory", "time");
        System.out.println("----------------------------------------------------");
        System.out.printf("%-20s:%10d%10d%n", "Before test", startMem/1024, statTime%100000);
        System.out.printf("%-20s:%10d%10d%n", "After test", finishMem/1024, finishTime%100000);
        System.out.printf("%s: %d kb %s: %d ms%n", "Mem", (finishMem-startMem)/1024, "Time", finishTime-statTime);
        System.out.println("====================================================");
    }

}
