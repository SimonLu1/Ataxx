package cpsc2150.extendedAtaxx.models;

import java.io.*;
import java.util.HashMap;



public class AtaxxBoard extends AbsAtaxxBoard {

    private char[][] board;
    private static final String BOARD_STATE_DIR = "cpsc2150/extendedAtaxx/boardStates/";

    /**
     * Paramaterized constructor. Initializes the board and piece counts.
     * @param aRows amount of rows that the board is going to be
     * @param aCols amount of columns that the board will be
     * @param aPlayerOne character that player one will be
     * @param aPlayerTwo character that player two will be
     * @pre none
     * @post board = | X|  |  |  |  |  | O|
     * |  |  |  |  |  |  |  |
     * |  | #|  | #|  |  |  |
     * |  |  |  |  |  |  |  |
     * |  | #|  | #|  |  |  |
     * |  |  |  |  |  |  |  |
     * | O|  |  |  |  |  | X|
     */

    public AtaxxBoard(int aRows, int aCols, char aPlayerOne, char aPlayerTwo) {
        super(aRows,aCols, aPlayerOne, aPlayerTwo);
        int MAX_ROWS = getROWS();
        int MAX_COLS = getCOLS();
        char EMPTY = ' ';
        char BLOCKED = '#';
        char PLAYER_ONE = getPLAYER_ONE();
        char PLAYER_TWO = getPLAYER_TWO();

        MAX_ROWS = aRows;
        MAX_COLS = aCols;
        PLAYER_ONE = aPlayerOne;
        PLAYER_TWO = aPlayerTwo;
        board = new char[MAX_ROWS][MAX_COLS];

        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLS; col++) {
                board[row][col] = EMPTY;
            }
        }

        board[0][0] = PLAYER_ONE;
        board[0][MAX_COLS - 1] = PLAYER_TWO;
        board[MAX_ROWS - 1][0] = PLAYER_TWO;
        board[MAX_ROWS - 1][MAX_COLS - 1] = PLAYER_ONE;

        int centerRow = MAX_ROWS / 2;
        int centerCol = MAX_COLS / 2;
        int offset = Math.max(centerRow, centerCol)/Math.min(centerRow, centerCol);

        board[centerRow - offset][centerCol - offset] = BLOCKED;
        board[centerRow - offset][centerCol + offset] = BLOCKED;
        board[centerRow + offset][centerCol - offset] = BLOCKED;
        board[centerRow + offset][centerCol + offset] = BLOCKED;
    }



        /*
         typical constructor behavior. Initializes the board and piece counts. The initial board setup has PLAYER_ONE's pieces
         in the top-left and bottom-right corners, PLAYER_TWO's pieces in the top-right and bottom-left corners, and four
         BLOCKED spaces 3 spaces from each corner. All other spaces are EMPTY.
         */


    /**
     * gives what is at the position on the board
     *
     * @param pos position on the board
     * @return char the position at the pos
     * @pre MAX_ROWS > pos.row >= 0 and MAX_COLS > pos.col >= 0
     * @post whatsAtPos = 'X' IFF 'X' is at BoardPosition AND whatsAtPos = 'O' IFF 'O' is at BoardPosition
     * AND whatsAtPos = board[pos.getRow()][pos.getCol()] AND board = #board
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {

        return board[pos.getRow()][pos.getCol()];
        /*
        effectively an accessor for the board. Gives the character at the given position.
         */
    }

    /**
     * places the character at the position
     *
     * @param pos    the position on the board
     * @param player which players piece to place, a character
     * @pre MAX_ROWS > pos.row >= 0 AND MAX_COLS > pos.col >=0
     * @post board = board[pos.row][pos.column] = player, the rest stays the same
     *
     */

    @Override
    public void placeAtPos(BoardPosition pos, char player) {

        board[pos.getRow()][pos.getCol()] = player;
        /*
        effectively a mutator for the board. Places the player's piece at the given position. Does NOT update piece counts
         */
    }


    /**
     * Getter for pieceCount
     *
     * @return the piece counts for each player
     * @pre none
     * @post getPieceCount = amount of pieces each player has AND board = #board
     */

    @Override
    public HashMap<Character, Integer> getPieceCount() {

        HashMap<Character, Integer> counts = new HashMap<>();
        counts.put(getPLAYER_ONE(), 0);
        counts.put(getPLAYER_TWO(), 0);

        for (int row = 0; row < getROWS(); row++) {
            for (int col = 0; col < getCOLS(); col++) {
                if (board[row][col] == getPLAYER_ONE()) {
                    counts.put(getPLAYER_ONE(), counts.get(getPLAYER_ONE()) + 1);
                } else if (board[row][col] == getPLAYER_TWO()) {
                    counts.put(getPLAYER_TWO(), counts.get(getPLAYER_TWO()) + 1);
                }
            }

        }
        return counts;
    }


    /**
     * saves the current board state to a file
     *
     * @param filename name of file, a string
     * @throws IOException if an error occurs while writing to the file
     * @pre filename != null
     * @post [a file named filename is created or overwitten containing the current board layout and
     * piece counts] AND board = #board
     */

    @Override
    public void saveBoardToFile(String filename) throws IOException {

        File dir = new File(BOARD_STATE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(BOARD_STATE_DIR + filename));

        for (int row = 0; row < getROWS(); row++) {
            for (int col = 0; col < getCOLS(); col++) {
                writer.write(board[row][col]);
            }
            writer.newLine();
        }

        HashMap<Character, Integer> counts = getPieceCount();
        writer.write(getPLAYER_ONE() + ":" + counts.get(getPLAYER_ONE()));
        writer.newLine();
        writer.write(getPLAYER_TWO() + ":" + counts.get(getPLAYER_TWO()));
        writer.newLine();

        writer.close();
        /*
        saves the current board state to a file of the name '<filename>.txt'. The file will contain the board layout
        followed by the piece counts for each player. Should throw an IOException if there is an error writing to the file,
        such as if the directory does not exist.
         */
    }

    /**
     * loads a board state from a file
     *
     * @param filename name of file, a string
     * @throws IOException is an error occurs while reading the file
     * @pre filename != null AND file format is valid
     * @post board = board loaded from file
     */
    @Override
    public void loadBoardFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(BOARD_STATE_DIR + filename));

        for (int row = 0; row < getROWS(); row++) {

            String line = reader.readLine();

            if (line == null || line.length() < getCOLS()) {
                reader.close();
                throw new IOException("Invalid board file format");
            }

            for (int col = 0; col < getCOLS(); col++) {
                board[row][col] = line.charAt(col);
            }
        }
        reader.close();
    }
}

        /*
        loads a board state from a file. The file should contain the board layout followed by the piece counts for each
        player. Should throw an IOException if there is an error reading from the file, such as if the file does not exist
        or if the file format is incorrect.
         */



    /**
     * creates a string representation of the board
     *
     * @return formatted string version of the board
     *
     * @pre none
     *
     * @post toString = |  | 0| 1| 2| 3| 4| 5| 6| AND board = #board
     *                  |0 | X|  |  |  |  |  | O|
     *                  |1 |  |  |  |  |  |  |  |
     *                  |2 |  |  |  |  |  |  |  |
     *                  |3 |  |  |  |  |  |  |  |
     *                  |4 |  |  |  |  |  |  |  |
     *                  |5 |  |  |  |  |  |  |  |
     *                  |6 | O|  |  |  |  |  | X|
     */
/**
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("|  |");

        //Create column headers
        for (int col = 0; col < MAX_COLS; col++) {
            s.append(" ").append(col).append("|");
        }
        s.append("\n");

        for (int row = 0; row < MAX_ROWS; row++) {
            s.append("|").append(row).append(" ");

            for (int col = 0; col < MAX_COLS; col++) {
                s.append("|");
                s.append(" ").append(board[row][col]);
            }
            s.append("|\n");
        }
        return s.toString();
    }
}
*/