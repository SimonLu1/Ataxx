package cpsc2150.extendedAtaxx.models;

import java.io.IOException;
import java.util.HashMap;

public interface IAtaxxBoard {

    /**
     * getter for player one
     *
     * @return returns the char of player one
     * @pre none
     *
     * @post getPLAYER_ONE() = char PLAYER_ONE
     */


    public char getPLAYER_ONE();

    /**
     * getter for player two
     *
     * @return returns the char of player two
     * @pre none
     *
     * @post getPLAYER_TWO() = char PLAYER_TWO
     */
    public char getPLAYER_TWO();

    /**
     *getter for rows
     * @return the int of the amount of rows
     * @pre none
     *
     * @post getROWS() = ROWS;
     */
    public int getROWS();
    /**
     *getter for columns
     * @return the int of the amount of columns
     * @pre none
     *
     * @post getCOLS() = COLS;
     */

    public int getCOLS();


    /**
     * gives what is at the position on the board
     * @param pos position on the board
     * @return char the position at the pos
     *
     * @pre MAX_ROWS > pos.row >= 0 and MAX_COLS > pos.col >= 0
     *
     * @post whatsAtPos = 'X' IFF 'X' is at BoardPosition AND whatsAtPos = 'O' IFF 'O' is at BoardPosition
     * AND whatsAtPos = what is at position at pos AND board = #board
     */
    public char whatsAtPos(BoardPosition pos);


    /**
     * places the character at the position
     *
     * @param pos the position on the board
     * @param player which players piece to place, a character
     *
     * @pre MAX_ROWS > pos.row >= 0 AND MAX_COLS > pos.col >=0
     *
     * @post board = board is unchanged other than (pos = player), the rest stays the same
     *
     */

    public void placeAtPos(BoardPosition pos, char player);

    /**
     * Getter for pieceCount
     *
     * @return the piece counts for each player
     *
     * @pre none
     *
     * @post getPieceCount = the amount of pieces each player has AND board = #board
     */

    public HashMap<Character, Integer> getPieceCount();


    /**
     * saves the current board state to a file
     *
     * @param filename name of file, a string
     *
     * @pre filename != null
     *
     * @post [a file named filename is created or overwitten containing the current board layout and
     * piece counts] AND board = #board
     *
     * @throws IOException if an error occurs while writing to the file
     */

    public void saveBoardToFile(String filename) throws IOException;


    /**
     * loads a board state from a file
     *
     * @param filename name of file, a string
     * @pre filename != null AND file format is valid
     *
     * @post board = board loaded from file
     *
     * @throws IOException is an error occurs while reading the file
     */

    public void loadBoardFromFile(String filename) throws IOException;

    /**
     * check is the position is a valid move for the given player
     *
     * @param pos position being checked
     * @param player player attempting move
     * @return true if valid, false otherwise
     *
     * @pre none
     *
     * @post isValidMove = [true IFF position is within bounds AND space is Empty AND there exists a player piece within
     * one or two space of position, false otherwise] AND board = #board
     *
     */

    default boolean isValidMove(BoardPosition pos, char player) {

         /*
        Check if the position is a valid move for the given player. A valid move is defined as moving to an empty space
        that is either adjacent to or two spaces away from one of the player's current pieces. The move must also be within
        the bounds of the board.
         */
        if (pos.getRow() < 0 || pos.getRow() >= getROWS() ||
        pos.getCol() < 0 || pos.getCol() >= getCOLS()) {
            return false;
        }

        if (whatsAtPos(pos) == '#') {
            return false;
        }
        if (whatsAtPos(pos) != ' '){return false;}

        for (int r = pos.getRow() - 2; r <= pos.getRow() + 2; r++) {
            for (int c = pos.getCol() -2; c <= pos.getCol() + 2; c++) {
                if (r >= 0 && r < getROWS() && c >= 0 && c < getCOLS()) {
                    if (whatsAtPos(new BoardPosition(r, c)) == player) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checks if the given player has won the game
     *
     * @param player player being checked if they have won
     * @return true if player won, false otherwise
     *
     * @pre None
     *
     * @post checkForWin = [true IF player greater than opponent OR IF opponent pieces equal 0, false otherwise]
     *       AND board = #board
     */

    default boolean checkForWin(char player) {

          /*
        checks if the given player has won the game. A player wins if they have more pieces on the board than their
        opponent and the opponent has no valid moves left. A player could also win by eliminating all the opponent's pieces.
         */
        char opponent;

        if (player == getPLAYER_ONE()) {
            opponent = getPLAYER_TWO();
        } else {
            opponent = getPLAYER_ONE();
        }

        int playerCount = 0;
        int opponentCount = 0;
        boolean boardFull = true;
        boolean playerHasMove = false;
        boolean opponentHasMove = false;

        for (int row = 0; row < getROWS(); row++) {
            for (int col = 0; col < getCOLS(); col++) {
                BoardPosition pos = new BoardPosition(row, col);
                char current = whatsAtPos(pos);

                if (current == player) {
                    playerCount++;
                } else if (current == opponent) {
                    opponentCount++;
                } else if (current == '#') {
                    boardFull = false;
                }

                if (!playerHasMove && isValidMove(pos, player)) {
                    playerHasMove = true;
                }

                if (!opponentHasMove && isValidMove(pos, opponent)) {
                    opponentHasMove = true;
                }
            }
        }

        if (opponentCount == 0) {
            return true;
        }

        if (!playerHasMove || !opponentHasMove) {
            return playerCount > opponentCount;
        }

        if (boardFull) {
            return playerCount > opponentCount;
        }

        return false;
    }

    /**
     * converts all adjacent opponent pieces, from pos, to the player's pieces
     *
     * @param player the current player, a char
     * @param pos the space that the piece was placed
     *
     * @pre pos must be in the bounds of the board, and pos must contain the same piece as 'player'
     *
     * @post convertAdjacentPieces = [all adjacent opponent pieces to the placed piece are converted to the player's pieces]
     * AND [board update such that adjacent opponent pieces become player]
     */

    default void convertAdjacentPieces(BoardPosition pos, char player)
    {
        /*
        converts all adjacent opponent pieces, from pos, to the player's pieces. Updates piece counts accordingly.
         */
        char opponent;

        if (player == getPLAYER_ONE())
        {
            opponent = getPLAYER_TWO();

        }
        else
        {
            opponent = getPLAYER_ONE();
        }

        for (int row = pos.getRow() - 1; row <= pos.getRow() + 1; row++)
        {
            for (int col = pos.getCol() -1; col <= pos.getCol() + 1; col++)
            {
                if (row >= 0 && row< getROWS() && col >= 0 && col < getCOLS())
                {
                    BoardPosition checkPos = new BoardPosition(row, col);
                    if (whatsAtPos(checkPos) == opponent)
                    {
                        placeAtPos(checkPos, player);
                    }
                }
            }
        }
    }



    /**
     * checks if the game is over
     *
     * @return true if the game is over, false otherwise
     *
     * @pre none
     *
     * @post isGameOver = [true IF board is full OR one player has 0 pieces OR neither player has valid moves,
     * false OtherWise] AND board = #board
     */

    default boolean isGameOver() {

         /*
        checks if the game is over. The game ends if either the board is full or one player has no pieces left or
        neither player has any valid moves left.
         */

        int xCount = 0;
        int oCount = 0;
        boolean boardFull = true;
        boolean xHasMove = false;
        boolean oHasMove = false;

        for (int row = 0; row < getROWS(); row++) {
            for (int col = 0; col < getCOLS(); col++) {
                BoardPosition pos = new BoardPosition(row, col);
                char current = whatsAtPos(pos);

                if (current == getPLAYER_ONE()) {
                    xCount++;
                } else if (current == getPLAYER_TWO()) {
                    oCount++;
                } else if (current == '#') {
                    boardFull = false;
                }

                if (!xHasMove && isValidMove(pos, getPLAYER_ONE())) {
                    xHasMove = true;
                }

                if (!oHasMove && isValidMove(pos, getPLAYER_TWO())) {
                    oHasMove = true;
                }
            }
        }

        if (boardFull) {
            return true;
        }

        if (xCount == 0 || oCount == 0) {
            return true;
        }

        return !xHasMove || !oHasMove;
    }
}
