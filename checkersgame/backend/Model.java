package checkersgame.backend;

import checkersgame.backend.controllers.ExtraGameManager;
import checkersgame.backend.controllers.TutorialManager;
import checkersgame.backend.game.GameLoader;
import checkersgame.backend.game.board.Board;
import checkersgame.backend.game.Game;
import checkersgame.backend.game.opponents.Ai;
import checkersgame.backend.controllers.AnimationControl;
import checkersgame.backend.game.movements.Move;
import checkersgame.backend.game.board.field.*;
import checkersgame.backend.game.opponents.Opponent;
import checkersgame.backend.game.opponents.Player;
import checkersgame.backend.game.piece.Piece;
import checkersgame.backend.game.piece.PieceColor;
import checkersgame.backend.game.piece.PieceDirection;
import checkersgame.frontend.GuiManager;

import java.io.File;
import java.util.*;

public class Model {

    private final int BOARD_SIZE = 8;
    private CheckersField selectedField;
    private List<Move> selectedPieceMoves;
    private Game game;
    private AnimationControl ac;
    private TutorialManager tm;
    private ExtraGameManager egm;
    private GameLoader gameLoader;
    public boolean click;
    public boolean exit;
    public int counter;

    public Model() {
        this.ac = new AnimationControl(this);
        this.selectedPieceMoves = new ArrayList<>();
        this.tm = new TutorialManager();
        this.egm = new ExtraGameManager();
        this.gameLoader = new GameLoader();
    }

    public void startNewGame(String bottomColorString, String topColorString, String playerDirectionString, int difficulty, String playerName, boolean forceAttack, boolean withdraw, File file) {
//        PLY vs CPU mode=0
        this.exit = false;
        PieceColor bottomColor = getColorFromString(bottomColorString);
        PieceColor topColor = getColorFromString(topColorString);
        PieceColor currentColor = PieceColor.DARK;
        PieceDirection playerDirection = getDirectionFromString(playerDirectionString);
        int difficult = difficulty;
        if(file == null) {
            Board newBoard = new Board(BOARD_SIZE);
            newBoard.initBoard(topColor, bottomColor);
            this.game = new Game(bottomColor, topColor, currentColor, playerDirection, difficult, playerName, newBoard, forceAttack, withdraw);
        } else {
            Game loadedGame = gameLoader.getGameFromFile(file);
            currentColor = loadedGame.getCurrentOpponentColor();
            Board newBoard = loadedGame.getBoard();
            this.game = new Game(bottomColor, topColor, currentColor, playerDirection, difficult, playerName, newBoard, forceAttack, withdraw);
            this.game.setBoardHistory(loadedGame.getBoardHistory());
            this.game.setOpponentsMoveHistory(loadedGame.getBottomOpponent().getMoveHistory(), loadedGame.getTopOpponent().getMoveHistory());
            this.game.setGameTime(loadedGame.getGameTime());
        }
        ac.setGame(game);
        ac.clockControl();
        GuiManager.initGameBoard();
        refreshUI();
        if(game.getCurrentOpponent() instanceof Player) {
            markTheStepablePieces((Player) game.getCurrentOpponent());
        }
        GuiManager.setActiveSideBox(getSideFromOpponentPosition(), getCurrentColorToString(), withdraw);
        Opponent opponent = game.getCurrentOpponent();
        if(opponent instanceof Ai) {
            Ai currentAi = (Ai) opponent;
            ac.aiProcess(currentAi);
        }
    }

    public void startNewGame(String bottomColorString, String topColorString, String bottomName, String topName, boolean forceAttack, boolean withdraw, File file) {
//        PLY vs PLY mode=1
        this.exit = false;
        PieceColor bottomColor = getColorFromString(bottomColorString);
        PieceColor topColor = getColorFromString(topColorString);
        PieceColor currentColor = PieceColor.DARK;
        if(file == null) {
            Board newBoard = new Board(BOARD_SIZE);
            newBoard.initBoard(topColor, bottomColor);
            this.game = new Game(bottomColor, topColor, currentColor, bottomName, topName, newBoard, forceAttack, withdraw);
        } else {
            Game loadedGame = gameLoader.getGameFromFile(file);
            if(loadedGame.getGameMode() == 1) {
                this.game = loadedGame;
            } else {
                currentColor = loadedGame.getCurrentOpponentColor();
                Board newBoard = loadedGame.getBoard();
                this.game = new Game(bottomColor, topColor, currentColor, bottomName, topName, newBoard, forceAttack, withdraw);
                this.game.setBoardHistory(loadedGame.getBoardHistory());
                this.game.setOpponentsMoveHistory(loadedGame.getBottomOpponent().getMoveHistory(), loadedGame.getTopOpponent().getMoveHistory());
                this.game.setGameTime(loadedGame.getGameTime());
            }
        }
        ac.setGame(game);
        GuiManager.initGameBoard();
        refreshUI();
        markTheStepablePieces((Player) game.getCurrentOpponent());
        GuiManager.setActiveSideBox(getSideFromOpponentPosition(), getCurrentColorToString(), withdraw);
    }

    public void startNewGame(String bottomColorString , String topColorString, int bottomDifficult, int topDifficult, boolean forceAttack, File file) {
//        CPU vs CPU mode=2
        this.exit = false;
        PieceColor bottomColor = getColorFromString(bottomColorString);
        PieceColor topColor = getColorFromString(topColorString);
        PieceColor currentColor = PieceColor.DARK;
        int bottomDiff = bottomDifficult;//getDifficultyLevel(bottomDifficult);
        int topDiff = topDifficult;//getDifficultyLevel(topDifficult);
        if(file == null) {
            Board newBoard = new Board(BOARD_SIZE);
            newBoard.initBoard(topColor, bottomColor);
            this.game = new Game(bottomColor, topColor, currentColor, bottomDiff, topDiff, newBoard, forceAttack);
        } else {
            Game loadedGame = gameLoader.getGameFromFile(file);
            if(loadedGame.getGameMode() == 2) {
                this.game = loadedGame;
                this.game.setAisDifficulty(bottomDiff, topDiff);
            } else {
                currentColor = loadedGame.getCurrentOpponentColor();
                Board newBoard = loadedGame.getBoard();
                this.game = new Game(bottomColor, topColor, currentColor, bottomDiff, topDiff, newBoard, forceAttack);
                this.game.setBoardHistory(loadedGame.getBoardHistory());
                this.game.setOpponentsMoveHistory(loadedGame.getBottomOpponent().getMoveHistory(), loadedGame.getTopOpponent().getMoveHistory());
                this.game.setGameTime(loadedGame.getGameTime());
            }
        }
        ac.setGame(game);
        GuiManager.initGameBoard();
        refreshUI();
        GuiManager.setActiveSideBox(getSideFromOpponentPosition(), getCurrentColorToString(), false);
        GuiManager.setupGamePaneForDemoMode();
        GuiManager.refreshDemoController(true, false, true);
        Ai currentAi = (Ai) game.getCurrentOpponent();
//        ac.aiProcess(currentAi);
        recursiveTest(currentAi);
    }

    private void recursiveTest(Ai currentAi)  throws StackOverflowError{
        List<Move> moves = currentAi.calculateStep(game.getBoard());
        currentAi.addMovesToHistoryList(moves);
        for(Move move : moves) {
            Piece piece = game.getBoard().removePieceByMove(move);
            game.getBoard().movePieceByMoveAndPiece(move, piece);
        }
        game.getBoard().resetFieldsStatus();
        if(!isDemoGameEnd()) {
            game.nextOpponent();
            game.saveCurrentBoardState();
            Ai ai = (Ai) game.getCurrentOpponent();
            recursiveTest(ai);
        }else {
            game.setBoard(game.getFirstBoardState());
            game.reset();
            refreshUI();
            ac.initDemo(game.getBoardHistory().size());
        }
    }

    public boolean isDemoGameEnd() {
        int lightPieceNumber = game.getBoard().getLightPieceNumber();
        int darkPieceNumber = game.getBoard().getDarkPieceNumber();
        if(lightPieceNumber == 0 || !game.getNextOpponent().areTherePossibleMoves(game.getBoard(), game.isForceAttack()) && game.getNextOpponent().getColor() == PieceColor.LIGHT) {
            GuiManager.getInformationMsg("Demo eredménye", "A sötét szín nyert.");
            return true;
        } else if(darkPieceNumber == 0 || !game.getNextOpponent().areTherePossibleMoves(game.getBoard(), game.isForceAttack()) && game.getNextOpponent().getColor() == PieceColor.DARK) {
            GuiManager.getInformationMsg("Demo eredménye", "A világos szín nyert.");
            return true;
        } else if(lastFiveStepsAreTheSame() || nothingChangedSinceLastTenRound()) {
            GuiManager.getInformationMsg("Demo eredménye", "Döntetlen. A győztes nem határozható meg egyértelműen.\n(az utolsó lépések már nem változtattak a játéktábla állásán)");
            return true;
        }
        return false;
    }

    public void playDemo() {
        if(!ac.demoIsEnding() && ac.isStopped()) {
            ac.setPlay();
            GuiManager.disablePlayButton();
            Ai currentAi = (Ai) game.getCurrentOpponent();
            ac.demoProcess(currentAi);
        }
    }

    public void stopDemo() {
        ac.stop();
    }

    public void nextStateOnDemo() {
        if(ac.isStopped()) {
//            GuiManager.enablePlayButton();
            GuiManager.refreshDemoController(true, ac.getDemo().atTheEnd(), ac.getDemo().atTheStart());
        }
        if(!ac.demoIsEnding()) {
            game.nextOpponent();
            GuiManager.setActiveSideBox(getSideFromOpponentPosition(), getCurrentColorToString(), game.isWithdraw());
            Opponent opponent = game.getCurrentOpponent();
            if(opponent instanceof Ai) {
                game.getBoard().resetFieldsStatus();
                Ai currentAi = (Ai) opponent;
                ac.demoProcess(currentAi);
            }
        }else {
            game.nextOpponent();
            ac.stop();
//            GuiManager.enablePlayButton();
            GuiManager.refreshDemoController(true, ac.getDemo().atTheEnd(), ac.getDemo().atTheStart());
        }
    }

    private String getSideFromOpponentPosition() {
        Opponent opp = game.getCurrentOpponent();
        if(opp.getDirection() == PieceDirection.UP) {
            return "LEFT";
        } else {
            return "RIGHT";
        }
    }

    private String getCurrentColorToString() {
        if(game.getCurrentOpponentColor() == PieceColor.LIGHT) {
            return "LIGHT";
        } else {
            return "DARK";
        }
    }

    public void gameCompletion() {
        this.exit = true;
        ac.shutDown();
    }

    private PieceColor getColorFromString(String color) {
        return color.equals("LIGHT") ? PieceColor.LIGHT : PieceColor.DARK;
    }

    private PieceDirection getDirectionFromString(String direction) {
        return (direction.equals("UP")) ? PieceDirection.UP : PieceDirection.DOWN;
    }

    public CheckersField[][] getBoard() {
        return game.getBoard().getFieldsMatrix();
    }

    public Game getGame() {
        return game;
    }

    public void onFieldClick(int x, int y) {
        Opponent opponent = game.getCurrentOpponent();
        if (opponent instanceof Player) {
            List<Move> possibleMoves = ((Player) opponent).getAllPossibleMoves(game.getBoard(), game.isForceAttack());
            refreshBoardByGameMode(game.getGameMode());
            Position clickPosition = new Position(x, y);
            CheckersField field = game.getBoard().getField(clickPosition);
            if (!field.isEmpty() && field.getPiece().getColor() == opponent.getColor()) {
                if (selectedField != field) {
                    this.selectedField = field;
                    selectedPieceMoves = new ArrayList<>();
                    for(Move move : possibleMoves) {
                        if(move.getPiecePos().equalsTo(clickPosition)) {
                            selectedPieceMoves.add(move);
                        }
                    }
                    if(selectedPieceMoves.size() > 0) {
                        game.getBoard().resetFieldsStatus();
                        game.getBoard().markStepableField(selectedPieceMoves);
                    } else {
                        game.getBoard().resetFieldsStatus();
                        game.getBoard().setStatusSelected(possibleMoves);
                    }
                    refreshBoardByGameMode(game.getGameMode());
                }
            } else if (field.isEmpty() && field.getStatus() == FieldStatus.STEPFIELD) {
                Move selectedMove = new Move();
                for(Move move : selectedPieceMoves) {
                    if(move.getStep().getPosition().equalsTo(clickPosition)) {
                        selectedMove = move;
                    }
                }
                opponent.addMoveToHistoryList(selectedMove);
                game.getBoard().movePieceByPlayerMove(selectedMove);
                game.getBoard().resetFieldsStatus();
                finishingMethodByGameMode(game.getGameMode(), opponent, selectedMove, x, y);
            }
        }
    }

    public void refreshUI() {
        int leftNum = game.getBoard().getPieceNumberOfColor(game.getBottomOpponent().getColor());
        String leftColor = (game.getBottomOpponent().getColor() == PieceColor.LIGHT) ? "LIGHT" : "DARK";
        int rightNum = game.getBoard().getPieceNumberOfColor(game.getTopOpponent().getColor());
        String rightColor = (game.getTopOpponent().getColor() == PieceColor.LIGHT) ? "LIGHT" : "DARK";
        if(game.getGameMode() < 3) {
            int leftBSC = game.getBottomOpponent().getWithdrawCounter();
            int rightBSC = game.getTopOpponent().getWithdrawCounter();
            GuiManager.refreshSidePanels(leftNum, leftBSC, leftColor, rightNum, rightBSC, rightColor, game.isWithdraw());
            GuiManager.refreshGameBoard(getBoard());
        }else if(game.getGameMode() == 3) {
            GuiManager.refreshTutorBoard(getBoard());
        }else if (game.getGameMode() == 4) {
            GuiManager.refreshChallengeBoard(getBoard());
        }
    }

    public void checkPlayerHasNextStep(boolean canHitAgain, int x, int y) {
        if(!isGameEnd() && !this.exit) {
            if(canHitAgain) {
                onFieldClick(x, y);
            } else {
                nextRound();
            }
        }
    }

    public boolean isGameEnd() {
        int lightPieceNumber = game.getBoard().getLightPieceNumber();
        int darkPieceNumber = game.getBoard().getDarkPieceNumber();
        if(lightPieceNumber == 0 || !game.getNextOpponent().areTherePossibleMoves(game.getBoard(), game.isForceAttack()) && game.getNextOpponent().getColor() == PieceColor.LIGHT) {
            endingEvent(1);
            return true;
        } else if(darkPieceNumber == 0 || !game.getNextOpponent().areTherePossibleMoves(game.getBoard(), game.isForceAttack()) && game.getNextOpponent().getColor() == PieceColor.DARK) {
            endingEvent(2);
            return true;
        } else if(lastFiveStepsAreTheSame() || nothingChangedSinceLastTenRound()) {
            endingEvent(3);
            return true;
        }
        return false;
    }

    private void endingEvent(int endNumber) {
        if(game.getGameMode() < 3) {
            GuiManager.endOfTheGame(endNumber);
        }else if(game.getGameMode() == 4) {
            GuiManager.extraGameEnded(endNumber);
        }
    }

    private boolean lastFiveStepsAreTheSame() {
        List<List<Move>> bottomMoves = game.getBottomOpponent().getMoveHistory();
        List<List<Move>> topMoves = game.getTopOpponent().getMoveHistory();
        if(topMoves.size() > 0 && bottomMoves.size() > 0) {
            List<Move> testMove = bottomMoves.get(bottomMoves.size()-1);
            int counterB = 0;
            for(int i=bottomMoves.size()-3; i>=0 && i>=bottomMoves.size()-12; i-=2) {
                if(bottomMoves.get(i).get(0).equals(testMove.get(0))) {
                    counterB++;
                }
            }
            if(counterB == 5) {
                return true;
            }
            int counterT = 0;
            testMove = topMoves.get(topMoves.size()-1);
            for(int j=topMoves.size()-3; j>=0 && j>=topMoves.size()-12; j-=2) {
                if(topMoves.get(j).get(0).equals(testMove.get(0))) {
                    counterT++;
                }
            }
            if(counterT == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean nothingChangedSinceLastTenRound() {
        int count = 30;
        int lightPieceNumber = game.getBoard().getLightPieceNumber();
        int darkPieceNumber =  game.getBoard().getDarkPieceNumber();
        for(int i=game.getBoardHistory().size()-1; i>=0; i-=2) {
            Board iBoard = game.getBoardHistory().get(i);
            if(iBoard.getLightPieceNumber() == lightPieceNumber && iBoard.getDarkPieceNumber() == darkPieceNumber) {
                count--;
            }
            if(count == 0) {return true;}
        }
        return false;
    }

    public void nextRound() {
        if(!isGameEnd()){
            game.nextOpponent();
            game.saveCurrentBoardState();
            GuiManager.setActiveSideBox(getSideFromOpponentPosition(), getCurrentColorToString(), game.isWithdraw());
            Opponent opponent = game.getCurrentOpponent();
            if(opponent instanceof Ai) {
                game.getBoard().resetFieldsStatus();
                Ai currentAi = (Ai) opponent;
                ac.aiProcess(currentAi);
            } else if (opponent instanceof Player) {
                markTheStepablePieces((Player) opponent);
            }
        }
    }

    private void markTheStepablePieces(Player player) {
        List<Move> possibleMoves = player.getAllPossibleMoves(game.getBoard(), game.isForceAttack());
        game.getBoard().setStatusSelected(possibleMoves);
        if(game.getGameMode() < 3) {
            GuiManager.refreshGameBoard(getBoard());
        }else if(game.getGameMode() == 3) {
            GuiManager.refreshTutorBoard(getBoard());
        }else if(game.getGameMode() == 4) {
            GuiManager.refreshChallengeBoard(getBoard());
        }
    }

    public Map<String, String> getOpponentDataForUI() {
        return game.getOpponentDataMap();
    }

    public Map<String, String> getDataMapFromFile(File file) {
        Game game = gameLoader.getGameFromFile(file);
        if(game != null) {
            return game.getOpponentDataMap();
        }
        return null;
    }

    public void undoLastStep() {
        if(game.getCurrentOpponent() instanceof Player) {
            game.stepBack();
            refreshUI();
        }
    }

    public void startTutorial() {
        tm.setLevel(1);
        startTutorialLevel();
    }

    public void startTutorialLevel() {
        this.exit = false;
        this.game = tm.getGameForLevel();
        this.game.setGameMode(3);
        ac.setGame(game);
        if(game != null) {
            GuiManager.initTutorBoard(getBoard());
            GuiManager.refreshTutorPanel(tm.getDescription(), tm.getLevel(), game.getBottomOpponent().getName());
        }
    }

    public void refreshBoardByGameMode(int gameMode) {
        if(gameMode < 3) {
            GuiManager.refreshGameBoard(getBoard());
        }else if(gameMode == 3) {
            GuiManager.refreshTutorBoard(getBoard());
        }else if(gameMode == 4) {
            GuiManager.refreshChallengeBoard(getBoard());
        }
    }

    private void finishingMethodByGameMode(int gameMode, Opponent opponent, Move selectedMove, int x, int y) {
        if(gameMode < 3) {
            finishingMethodSimple(opponent, selectedMove, x, y);
        }else if(gameMode == 3) {
            finishingMethodTutor(opponent, selectedMove, x, y);
        }else if(gameMode == 4) {
            finishingMethodChallenge(opponent, selectedMove, x, y);
        }
    }

    private void finishingMethodSimple(Opponent opponent, Move selectedMove, int x, int y) {
        refreshUI();
        GuiManager.makeStepSound();
        if(selectedMove.hasHits()) {
            boolean canHitAgain = ((Player) opponent).isThereHitPossibilitiesInPosition(game.getBoard(), opponent.getColor(), new Position(x, y));
            checkPlayerHasNextStep(canHitAgain, x, y);
        } else {
            nextRound();
        }
    }

    private void finishingMethodTutor(Opponent opponent, Move selectedMove, int x, int y) {
        GuiManager.refreshTutorBoard(getBoard());
        GuiManager.makeStepSound();
        if(selectedMove.hasHits()) {
            boolean canHitAgain = ((Player) opponent).isThereHitPossibilitiesInPosition(game.getBoard(), opponent.getColor(), new Position(x, y));
            if(canHitAgain) {
                onFieldClick(x, y);
                nextTutorLevel(selectedMove);
            }else {
                nextTutorLevel(selectedMove);
            }
        } else {
            nextTutorLevel(selectedMove);
        }
    }

    private void finishingMethodChallenge(Opponent opponent, Move selectedMove, int x, int y) {
        GuiManager.refreshChallengeBoard(getBoard());
        GuiManager.makeStepSound();
        if(selectedMove.hasHits()) {
            boolean canHitAgain = ((Player) opponent).isThereHitPossibilitiesInPosition(game.getBoard(), opponent.getColor(), new Position(x, y));
            if(canHitAgain) {
                onFieldClick(x, y);
            }else {
                nextRound();
            }
        } else {
            nextRound();
        }
    }

    public void nextTutorLevel(Move selectedMove) {
        boolean validStep = false;
        for(Move move : tm.getPossiblePlayerMoves()) {
            if(selectedMove.equals(move)) {
                tm.removeStep();
                validStep = true;
                break;
            }
        }
        tm.decreaseStepCounter();
        if(!validStep) {
            GuiManager.replayTutorLevel();
        }else if(tm.getPlayerMoves().size() == 0 && tm.getStepCounter() == 0) {
            if(tm.nextLevel()) {
                GuiManager.nextTutorLevel();
            } else {
                GuiManager.finishingTutorial();
            }
        }
    }

    public TutorialManager getTm() {
        return tm;
    }

    public void backToPreviousLevel() {
        tm.goToPreviousLevel();
        startTutorialLevel();
    }

    public int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public void startExtraMode() {
        egm.start();
        loadExtraLevel(egm.getLevel());
    }

    public void loadExtraLevel(int level) {
        exit = false;
        this.game = egm.getGameByLevel(level);
        this.game.setGameMode(4);
        ac.setGame(game);
        GuiManager.initChallengeBoard(getBoard());
        GuiManager.refreshChallengePanel(egm.getDescription(), game.getBottomOpponent().getName());
    }

    public void reloadExtraLevel() {
        loadExtraLevel(egm.getLevel());
    }

    public void nextExtraLevel() {
        egm.nextLevel();
        loadExtraLevel(egm.getLevel());
    }

    public void speedUp() {
        ac.increaseSpeed();
    }

    public void speedDown() {
        ac.decreaseSpeed();
    }

    public void forwardStepOnDemo() {
        if(ac.isStopped()) {
            ac.setPlay();
            game.getBoard().resetFieldsStatus();
            ac.stepForward((Ai) game.getCurrentOpponent());
            game.nextOpponent();
        }
    }

    public void backwardStepOnDemo() {
        if(ac.getDemo().getBoardIndex() > 0 && ac.isStopped()) {
            game.nextOpponent();
            ac.backwardStep((Ai) game.getCurrentOpponent());
            int boardIndex = ac.getDemo().getBoardIndex();
            Board prevBoard = game.getBoardHistory().get(boardIndex);
            game.setBoard(prevBoard);
            refreshUI();
            GuiManager.refreshDemoController(true, ac.getDemo().atTheEnd(), ac.getDemo().atTheStart());
        }
    }

    public String getGameString() {
        return gameLoader.getGameString(game);
    }
}
