package checkersgame.backend.game.opponents;

import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.board.field.*;
import checkersgame.backend.game.movements.StepGenerator;
import checkersgame.backend.game.piece.Piece;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;

import java.util.*;


public class Ai extends Opponent {

    private PieceColor selfColor;
    private PieceColor enemyColor;
    private PieceDirection direction;
    private int difficult;
    private int maxDepth;
    private int boardSize;
    private StepGenerator sg;
    private boolean forceAttack;

    /**
     *
     * @param color: The color of the computer player instance. It should be DARK or LIGHT.
     * @param direction: The direction of the computer player from the start side to the other side. It's UP or DOWN.
     * @param size: The size of the game board. size = 8 means the game board size is 8x8.
     * @param difficult: It must be an integer and in the inclusive range of 1 to 7.
     * @param forceAttack: If it is true the computer has to execute one of the possible attack moves.
     */
    public Ai(PieceColor color, PieceDirection direction, int size, int difficult, boolean forceAttack) {
        super(color, direction);
        this.selfColor = color;
        this.enemyColor = (color == PieceColor.LIGHT) ? PieceColor.DARK : PieceColor.LIGHT;
        this.direction = direction;
        this.difficult = difficult;
        this.boardSize = size;
        this.sg = new StepGenerator(this.selfColor);
        this.forceAttack = forceAttack;
        setWithdrawCounter(0);
    }
    
    public PieceColor getColor() {
        return selfColor;
    }

    public List<Move> calculateStep(Board board) {
        switch (difficult) {
            case 1: maxDepth = 1;break; //Kezdő
            case 2: maxDepth = 2;break; //Nagyon könnyű
            case 3: maxDepth = 2;break; //Könnyű
            case 4: maxDepth = 3;break; //Normál
            case 5: maxDepth = 4;break; //Haladó
            case 6: maxDepth = 6;break; //Nehéz
            case 7: maxDepth = 8;break; //Mester
        }
        try {
            List<Move> moves = alphabeta(board, maxDepth, -1000, 1000, true);
//            System.out.println(maxDepth + " - " + moves.get(0).getScore());
            return moves;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean onlyDameOnTheBoard(Board board) {
        int sum = 0;
        for(Piece piece : board.getPiecesList(selfColor)) {
            if(piece.isDame()) {
                sum += 1;
            }
        }
        if(sum == board.getPieceNumberOfColor(selfColor)) {
            return true;
        }
        return false;
    }

    private double strategyNormal(Board board, int depth) {
        return sumOfPieces2(board) + boardPartToValue(board);
    }

    private double strategyForAdvanced(Board board, int depth) {
        double eval = 0.0;
        if(onlyDameOnTheBoard(board)) {
            eval = sumOfDistance(board)*-1 + sumOfPieces2(board);
            eval += depth;
        } else {
            eval = sumOfPieces2(board);
        }
        return eval;
    }

    private double strategyForHard(Board board, int depth) {
        double eval = 0.0;
        if(onlyDameOnTheBoard(board)) {
            eval = sumOfDistance(board)*-1 + sumOfPieces2(board);
            eval += depth;
        } else {
            eval = sumOfPieces2(board) + boardPartToValue(board);
        }
        return eval;
    }


    private double strategyForMaster(Board board, int depth) {
        double eval = 0.0;
        if(onlyDameOnTheBoard(board)) {
            eval = sumOfDistance(board)*-1 + sumOfPieces2(board);
            eval += depth;
        } else {
            eval = sumOfPieces2(board) + rowToValue(board);
        }
        return eval;
    }

    private double getEvalFunctionValue(Board board, int depth) {
        if(difficult == 1) {
            //Beginner- depth: 1
            return sumOfPieces(board);
        } else if(difficult == 2) {
            //Very Easy- depth: 2
            return sumOfPieces(board);
        } else if(difficult == 3) {
            //Easy- depth: 2
            return sumOfPieces2(board);
        } else if(difficult == 4) {
            //Normal- depth: 3
            return sumOfPieces2(board);
        } else if(difficult == 5) {
            //Advanced- depth: 4
            return strategyForAdvanced(board, depth);
        } else if(difficult == 6) {
            //Hard- depth: 6
            return strategyForHard(board, depth);
        } else if(difficult == 7) {
            //Master- depth: 8
            return strategyForMaster(board, depth);
        }
        return 0;
    }

    private int sumOfPieces(Board board) {
        int numberOfSelfPieces = board.getPieceNumberOfColor(getColor());
        int numberOfEnemyPieces = board.getPieceNumberOfColor(getOppositeColor());

        return  numberOfSelfPieces - numberOfEnemyPieces;
    }

    private double sumOfPieces2(Board board) {
        double evalScore = 0;
        double numberOfSelfPieces = 0;
        double numberOfEnemyPieces = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!board.getFieldsMatrix()[i][j].isEmpty()) {
                    Piece piece = board.getFieldsMatrix()[i][j].getPiece();
                    if (piece.getColor() != getColor()) {
                        numberOfEnemyPieces += 10.0;
                        if (piece.isDame()) numberOfEnemyPieces += 20.0;
                    } else if (piece.getColor() == getColor()) {
                        numberOfSelfPieces += 10.0;
                        if (piece.isDame()) numberOfSelfPieces += 20.0;
                    }
                }
            }
        }
        if(numberOfEnemyPieces == 0) {
            evalScore += 100;
        } else if(numberOfSelfPieces == 0) {
            evalScore += -100;
        }
        evalScore += (numberOfSelfPieces * 2) - numberOfEnemyPieces;
        return  evalScore;
    }

    private int boardPartToValue(Board board) {
        int selfScore = 0;
        int enemyScore = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if(!board.getFieldsMatrix()[i][j].isEmpty()) {
                    Piece piece = board.getFieldsMatrix()[i][j].getPiece();
                    if(piece.getColor() == selfColor && !piece.isDame()) {
                        if (piece.getDirection() == PieceDirection.UP) {
                            if (piece.getPosX() < 4) {
                                selfScore += 5;
                            }else {
                                selfScore += 3;
                            }
                        } else {
                            if (piece.getPosX() >= 4) {
                                selfScore += 5;
                            }else {
                                selfScore += 3;
                            }
                        }
                    }else if(piece.getColor() == selfColor && piece.isDame()) {
                        selfScore += 10;
                    }else if(piece.getColor() != selfColor && !piece.isDame()) {
                        if (piece.getDirection() == PieceDirection.UP) {
                            if (piece.getPosX() < 4) {
                                enemyScore += 5;
                            }else {
                                enemyScore += 3;
                            }
                        } else {
                            if (piece.getPosX() >= 4) {
                                enemyScore += 5;
                            }else {
                                enemyScore += 3;
                            }
                        }
                    }else if(piece.getColor() != selfColor && piece.isDame()) {
                        enemyScore += 10;
                    }
                }
            }
        }
        return selfScore - enemyScore;
    }

    private int rowToValue(Board board) {
        int selfScore = 0;
        int enemyScore = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (!board.getFieldsMatrix()[i][j].isEmpty()) {
                    Piece piece = board.getFieldsMatrix()[i][j].getPiece();
                    if(piece.getColor() == selfColor && !piece.isDame()) {
                        if(piece.getDirection() == PieceDirection.UP) {
                            selfScore += (5 + (7-piece.getPosX()) );
                        }else if(piece.getDirection() == PieceDirection.DOWN) {
                            selfScore += (5 + piece.getPosX());
                        }
                    } else if(piece.getColor() == selfColor && piece.isDame()) {
                        selfScore += 15;
                    } else if(piece.getColor() != selfColor && !piece.isDame()) {
                        if(piece.getDirection() == PieceDirection.UP) {
                            enemyScore += (5 + (7-piece.getPosX()) );
                        }else if(piece.getDirection() == PieceDirection.DOWN) {
                            enemyScore += (5 + piece.getPosX());
                        }
                    } else if(piece.getColor() != selfColor && piece.isDame()) {
                        enemyScore += 15;
                    }
                }
            }
        }
        return (selfScore*2) - enemyScore;
    }

    private double sumOfDistance(Board board) {
        PieceColor enemyColor = (selfColor == PieceColor.LIGHT) ? PieceColor.DARK:PieceColor.LIGHT;
        double sum = 0;
        for (Piece piece : board.getPiecesList(selfColor)) {
            Position selfPos = piece.getPosition();
            if(piece.isDame()) {
                for (Piece enemyPiece : board.getPiecesList(enemyColor)) {
                    Position pos = enemyPiece.getPosition();
                    sum += selfPos.distanceOf(pos);
                }
            }
        }
        return sum;
    }

    private boolean isGameEnd(Board board, boolean selfRound) {
        int lightPieceNumber = board.getLightPieceNumber();
        int darkPieceNumber = board.getDarkPieceNumber();
        if (lightPieceNumber == 0 || darkPieceNumber == 0 || sg.generateMoves(board, selfRound).size() == 0) {
            return true;
        }
        return false;
    }

    private List<Move> alphabeta(Board board, int depth, double a, double b, boolean selfRound) {
        List<Move> bestMove = new ArrayList<>();
        if (depth == 0 || isGameEnd(board, selfRound)) {
            Move move = new Move();
            move.setScore(getEvalFunctionValue(board, depth));
            bestMove.add(move);
            return bestMove;
        }
        if (selfRound) {
            List<List<Move>> movesList = generateMoves(board, true);
            Collections.shuffle(movesList);
            double max = (-1000);
            for (List<Move> moves : movesList) {
                Board newBoard = board;
                for (Move move : moves) {
                    newBoard = sg.executeMove(newBoard, move);
                }
                List<Move> bmove = alphabeta(newBoard, depth - 1, a, b, false);
                if (bmove.size() > 0 && bmove.get(0).getScore() > max) {
                    max = bmove.get(0).getScore();
                    bestMove = moves;
                    for (Move bm : bestMove) {
                        bm.setScore(max);
                    }
                }
                a = (Math.max(a, max));
                if (a >= b) {
                    break;
                }
            }
            return bestMove;
        } else {
            List<List<Move>> movesList = generateMoves(board, false);
            Collections.shuffle(movesList);
            double min = 1000;
            for (List<Move> moves : movesList) {
                Board newBoard = board;
                for (Move move : moves) {
                    newBoard = sg.executeMove(newBoard, move);
                }
                List<Move> bmove = alphabeta(newBoard, depth - 1, a, b, true);
                if (bmove.size() > 0 && bmove.get(0).getScore() < min) {
                    min = bmove.get(0).getScore();
                    bestMove = moves;
                    for (Move bm : bestMove) {
                        bm.setScore(min);
                    }
                }
                b = (Math.min(b, min));
                if (a >= b) {
                    break;
                }
            }
            return bestMove;
        }
    }

    @Override
    public boolean areTherePossibleMoves(Board board, boolean forceAttack) {
        if(forceAttack) {
            if(sg.generateMoves(board, true).size() == 0) {
                return false;
            }
        }else {
            if(sg.generateMovesWithoutForceAttack(board, true).size() == 0) {
                return false;
            }
        }
        return true;
    }

    private List<List<Move>> generateMoves(Board board, boolean selfStep) {
        if(forceAttack) {
            return sg.generateMoves(board, selfStep);
        }else {
            return sg.generateMovesWithoutForceAttack(board, selfStep);
        }
    }

    private boolean moveTester(Board board, Move move) {
        double preVal = sumOfPieces2(board);
        double postVal = 0;
        Board testBoard = sg.executeMove(board, move);
        List<List<Move>> testMoves = sg.generateMoves(testBoard,false);
        for (List<Move> moves : testMoves) {
            Board newBoard = new Board(boardSize);
            newBoard.setBoardFromArray(testBoard.getFieldsMatrix());
            for (Move m : moves) {
                newBoard = sg.executeMove(newBoard, m);
            }
            postVal = sumOfPieces2(newBoard);
            if(preVal > postVal) {
                return false;
            }
        }
        return true;
    }

    public PieceDirection getDirection() {
        return direction;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getDifficult() {
        return difficult;
    }

    private void printBoard(CheckersField[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].isEmpty()) {
                    System.out.print("0, ");
                } else {
                    if (board[i][j].getPiece().getColor() == PieceColor.DARK) {
                        System.out.print("1, ");
                    } else {
                        System.out.print("2, ");
                    }
                }
            }
            System.out.println("");
        }
        System.out.println("---------------------------");
    }

}
