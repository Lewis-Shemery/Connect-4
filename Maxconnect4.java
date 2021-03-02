package maxconnect4;
/**
 *
 * @author Lewis Shemery
 * 1001402131
 * 
 */

import java.util.Scanner;

public class Maxconnect4 {

    public static Scanner input_stream = null;
    public static GameBoard currentGame = null;
    public static AiPlayer aiPlayer = null;

    public static void main(String[] args) throws CloneNotSupportedException {
        // check for the correct number of arguments
        if (args.length != 4) {
            System.out.println("Four command-line arguments are needed:\n"
                + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function(0);
        }

        // parse the input arguments
        String game_mode = args[0].toString(); // the game mode
        String inputFilePath = args[1].toString(); // the input game file
        int depthLevel = Integer.parseInt(args[3]); // the depth level of the ai search

        // create and initialize the game board
        currentGame = new GameBoard(inputFilePath);

        // create the Ai Player
        aiPlayer = new AiPlayer(depthLevel, currentGame);

        if (game_mode.equalsIgnoreCase("interactive")) {
            if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("C")) {
                // if it is computer next, make the computer make a move
                currentGame.setFirstTurn("computer-next");
                interactive();
            } else if (args[2].toString().equalsIgnoreCase("human-next") || args[2].toString().equalsIgnoreCase("H")){
                currentGame.setFirstTurn("human-next");
                MakeHumanPlay();
            } else {
                System.out.println("\n" + "value for 'next turn' doesn't recognized.  \n try again. \n");
                exit_function(0);
            }

            if (currentGame.isBoardFull()) {
                System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
                exit_function(0);
            }

        } else if (!game_mode.equalsIgnoreCase("one-move")) {
            System.out.println("\n" + game_mode + " is an unrecognized game mode \n try again. \n");
            exit_function(0);
        } else {
            /////////// one-move mode ///////////
            String output = args[2].toString(); // the output game file
            oneMove(output);
        }
    }
    
    /**
     * This method handles computer's move for one-move mode
     * @param outputFileName path for output file to save game state
     */
    private static void oneMove(String outputFileName) throws CloneNotSupportedException {
        // variables to keep up with the game
        int playColumn = 99; // the players choice of column to play

        System.out.print("\nMaxConnect-4 game:\n");
        System.out.print("Game state before move:\n");

        // print the current game board
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(1) + ", Player-2 = " + currentGame.getScore(2)
            + "\n ");

        if (currentGame.isBoardFull()) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // ****************** this chunk of code makes the computer play

        int current_player = currentGame.getCurrentTurn();

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(currentGame);

        if (playColumn == 99) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        currentGame.playPiece(playColumn);

        // display the current game board
        System.out.println("move " + currentGame.getPieceCount() + ": Player " + current_player + ", column "
            + (playColumn + 1));

        System.out.print("Game state after move:\n");

        // print the current game board
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(1) + ", Player-2 = " + currentGame.getScore(2)
            + "\n ");

        currentGame.printGameBoardToFile(outputFileName);
    }

    // This method handles computer's move for interactive mode
    private static void interactive() throws CloneNotSupportedException {

        printBoardAndScore();

        System.out.println("\n Computer's turn:\n");

        int playColumn = 99; // the players choice of column to play

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(currentGame);

        if (playColumn == 99) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        currentGame.playPiece(playColumn);

        System.out.println("move: " + currentGame.getPieceCount() + " , Player: Computer , Column: " + (playColumn + 1));

        if (currentGame.isBoardFull()) {
            printBoardAndScore();
        } else {
            MakeHumanPlay();
        }
    }


    // This method handles human's move for interactive mode.
    private static void MakeHumanPlay() throws CloneNotSupportedException {
        printBoardAndScore();

        System.out.println("\n Human's turn:\nKindly play your move here(1-7):");

        input_stream = new Scanner(System.in);

        int playColumn = 99;

        do {
            playColumn = input_stream.nextInt();
        } while (!isValidPlay(playColumn));

        // play the piece
        currentGame.playPiece(playColumn - 1);

        System.out.println("move: " + currentGame.getPieceCount() + " , Player: Human , Column: " + playColumn);

        if (currentGame.isBoardFull()) {
            printBoardAndScore();
        } else {
            interactive();
        }
    }

    private static boolean isValidPlay(int playColumn) {
        if (currentGame.isValidPlay(playColumn - 1)) {
            return true;
        }
        System.out.println("Opps!!...Invalid column , Kindly enter column value between 1 to 7.");
        return false;
    }
   
    //This method displays current board state and score of each player.
    public static void printBoardAndScore() {
        System.out.print("Game state :\n");

        // print the current game board
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(1) + ", Player-2 = " + currentGame.getScore(2)
            + "\n ");
    }

    /**
     * This method is used when to exit the program prematurly.
     * @param value an integer that is returned to the system when the program exits.
     */
    private static void exit_function(int value) {
        System.out.println("exiting from MaxConnectFour.java!\n\n");
        System.exit(value);
    }
}
