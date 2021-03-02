package maxconnect4;

import java.util.*;

public class AiPlayer {

    public int depth;
    public GameBoard gameBoardShallow;

    /**
     * The constructor essentially does nothing except instantiate an AiPlayer object.
     * 
     * @param currentGame
     * 
     */
    public AiPlayer(int depth, GameBoard currentGame) {
        this.depth = depth;
        this.gameBoardShallow = currentGame;
    }

    /**
     * This method makes the decision to make a move for the computer using 
     * the min and max value from the below given two functions.
     * 
     * @param currentGame The GameBoard object that is currently being used to play the game.
     * @return an integer indicating which column the AiPlayer would like to play in.
     * @throws CloneNotSupportedException
     */
    public int findBestPlay(GameBoard currentGame) throws CloneNotSupportedException {
        int playChoice = 99;
        if (currentGame.getCurrentTurn() == 1) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < 7; i++) {
                if (currentGame.isValidPlay(i)) {
                    GameBoard nextMoveBoard = new GameBoard(currentGame.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = getMax(nextMoveBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v > value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        } else {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < 7; i++) {
                if (currentGame.isValidPlay(i)) {
                    GameBoard nextMoveBoard = new GameBoard(currentGame.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = getMin(nextMoveBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v < value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        }
        return playChoice;
    }

    /**
     * This method calculates the min value.
     * 
     * @param gameBoard The GameBoard object that is currently being used to play the game.
     * @param depth depth to which computer will search for making best possible next move
     * @param alpha_value maximum utility value for MAX player
     * @param beta_value maximum utility value for MIN player 
     * @return an integer indicating which column the AiPlayer would like to play in.
     * @throws CloneNotSupportedException
     */

    private int getMin(GameBoard gameBoard, int depth, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
        if (!gameBoard.isBoardFull() && depth > 0) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < 7; i++) {
                if (gameBoard.isValidPlay(i)) {
                    GameBoard newBoard = new GameBoard(gameBoard.getGameBoard());
                    newBoard.playPiece(i);
                    int value = getMax(newBoard, depth - 1, alpha_value, beta_value);
                    if (v > value) {
                        v = value;
                    }
                    if (v <= alpha_value) {
                        return v;
                    }
                    if (beta_value > v) {
                        beta_value = v;
                    }
                }
            }
            return v;
        } else {
            // this is a terminal state
            return evaluationFunction(gameBoard);
        }
    }

    /**
     * This method calculates the max value.
     * 
     * @param gameBoard The GameBoard object that is currently being used to play the game.
     * @param depth depth to which computer will search for making best possible next move
     * @param alpha_value maximum utility value for MAX player
     * @param beta_value maximum utility value for MIN player 
     * @return an integer indicating which column the AiPlayer would like to play in.
     * @throws CloneNotSupportedException
     */

    private int getMax(GameBoard gameBoard, int depth, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
        if (!gameBoard.isBoardFull() && depth > 0) {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < 7; i++) {
                if (gameBoard.isValidPlay(i)) {
                    GameBoard newBoard = new GameBoard(gameBoard.getGameBoard());
                    newBoard.playPiece(i);
                    int value = getMin(newBoard, depth - 1, alpha_value, beta_value);
                    if (v < value) {
                        v = value;
                    }
                    if (v >= beta_value) {
                        return v;
                    }
                    if (alpha_value < v) {
                        alpha_value = v;
                    }
                }
            }
            return v;
        } else {
            // this is a terminal state
            return evaluationFunction(gameBoard);
        }
    }
    
    /**
     * When the depth limit is reached it calculates value giving weighted average 
     * to connections of 4,3, and 2.
     * 4 has weight of 100
     * 3 has weight of 20
     * 2 has weight of 5
     */
    private int evaluationFunction(GameBoard currentGame)
    {
        int result = 0;
        if (currentGame.isBoardFull()) {
            if((currentGame.getScore(2) - currentGame.getScore(1)) > 0){
                return Integer.MAX_VALUE;
            } else if((currentGame.getScore(2) - currentGame.getScore(1)) == 0){
                return 0;
            } else {
                return Integer.MIN_VALUE;
            }
        } else {

            result = (currentGame.getScore(1) * 100) + (currentGame.connectThree(1) * 20) + (currentGame.connectTwo(1) * 5)
                    - (currentGame.getScore(2) * 100) - (currentGame.connectThree(2) * 20) - (currentGame.connectTwo(2) * 5);
        }
        return (-result);
    }
}