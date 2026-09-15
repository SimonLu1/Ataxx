package cpsc2150.extendedAtaxx.models;

public abstract class AbsAtaxxBoard implements IAtaxxBoard {

    private final int ROWS;
    private final int COLS;
    private final char PLAYER_ONE;
    private final char PLAYER_TWO ;
    private final char MAX_ROWS = 100;
    private final char MAX_COLS = 100;
    private final char MIN_ROWS = 7;
    private final char MIN_COLS = 7;

    public AbsAtaxxBoard(int aRows, int aCols, char aPlayerOne, char aPlayerTwo){
        ROWS = aRows;
        COLS = aCols;
        PLAYER_ONE = aPlayerOne;
        PLAYER_TWO = aPlayerTwo;
    }
    /**
     * returns a string version of the board
     *
     * @return a formatted string showing the board
     *
     * @pre none
     *
     * @post toString() = string representation of current board state
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("|  | ");

        for (int col = 0; col < getCOLS(); col++) {
            s.append(col).append("| ");
        }
        s.append("\n");

        for (int row = 0; row < getROWS(); row++) {
            s.append("|").append(row).append(" ");

            for (int col = 0; col < getCOLS(); col++) {
                s.append("| ").append(whatsAtPos(new BoardPosition(row, col)));
            }

            s.append("| ");

            if (row < getROWS() - 1) {
                s.append("\n");
            }
        }

        return s.toString();
    }
    /**
     * getter for player one
     *
     * @return returns the char of player one
     * @pre none
     *
     * @post getPLAYER_ONE() = char PLAYER_ONE
     */


    public char getPLAYER_ONE() {
         return PLAYER_ONE;
        }

    /**
     * getter for player two
     *
     * @return returns the char of player two
     * @pre none
     *
     * @post getPLAYER_TWO() = char PLAYER_TWO
     */
    public char getPLAYER_TWO() {
        return PLAYER_TWO;
    }

    /**
    *getter for rows
    * @return the int of the amount of rows
    * @pre none
    *
    * @post getROWS() = ROWS;
    */
    public int getROWS(){
        return ROWS;
    }
    /**
     *getter for columns
     * @return the int of the amount of columns
     * @pre none
     *
     * @post getCOLS() = COLS;
     */

    public int getCOLS() {
        return COLS;
    }
}
