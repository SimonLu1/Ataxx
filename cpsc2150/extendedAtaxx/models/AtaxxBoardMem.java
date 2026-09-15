package cpsc2150.extendedAtaxx.models;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AtaxxBoardMem extends AbsAtaxxBoard{

    Map<Character, ArrayList<BoardPosition>> board;
    private static final String BOARD_STATE_DIR = "cpsc2150/extendedAtaxx/boardStates/";

    /**
     * parameterized constructure that makes the maps with players
     * @param aRows an int with the amount of rows for the board
     * @param aCols an in with the amount of columns for the board
     * @param aPlayerOne a char that will be player one
     * @param aPlayerTwo a char that will be player two
     * @pre none
     *
     * @post board[PlAYER_ONE] = (0,0), (COL-1, ROW-1) AND board[PLAYER_TWO] = (ROWS-1 , 0), (0, COLS-1)
     */
    public AtaxxBoardMem(int aRows, int aCols, char aPlayerOne, char aPlayerTwo) {
        super(aRows, aCols, aPlayerOne, aPlayerTwo);



        board = new HashMap<>();
        board.put(getPLAYER_ONE(), new ArrayList<>());
        board.put(getPLAYER_TWO(), new ArrayList<>());

        // starting positions
        board.get(getPLAYER_ONE()).add(new BoardPosition(0, 0));
        board.get(getPLAYER_ONE()).add(new BoardPosition(getROWS() - 1, getCOLS() - 1));

        board.get(getPLAYER_TWO()).add(new BoardPosition(0, getCOLS() - 1));
        board.get(getPLAYER_TWO()).add(new BoardPosition(getROWS() - 1, 0));
    }

    /**
     * returns character that is at the pos
     * @param pos position on the board
     * @return character that is at the pos
     * @pre ROWS > pos.row >= 0 and COLS > pos.col >= 0
     * @post whatsAtPos = 'X' IFF 'X' is at BoardPosition AND whatsAtPos = 'O' IFF 'O' is at BoardPosition
     * AND whatsAtPos =  ' ' if nothing is there AND whatsAtPos = '#' if in blocked pos AND board = #board
     */
    @Override
    public char whatsAtPos(BoardPosition pos) {

        // check player1 positions
        for (BoardPosition p : board.get(getPLAYER_ONE())) {
            if (p.equals(pos)) {
                return getPLAYER_ONE();
            }
        }

        // check player2 positions
        for (BoardPosition p : board.get(getPLAYER_TWO())) {
            if (p.equals(pos)) {
                return getPLAYER_TWO();
            }
        }

        // check blocked positions
        int centerRow = getROWS() / 2;
        int centerCol = getCOLS() / 2;
        int offset = Math.max(1, Math.min(getROWS(), getCOLS()) / 6);

        if ((pos.getRow() == centerRow - offset || pos.getRow() == centerRow + offset) &&
                (pos.getCol() == centerCol - offset || pos.getCol() == centerCol + offset)) {
            return '#';
        }

        // otherwise empty
        return ' ';
    }

    /**
     * places the character at the position
     *
     * @param pos    the position on the board
     * @param player which players piece to place, a character
     * @pre ROWS > pos.row >= 0 AND COLS > pos.col >=0
     * @post board = board[key].add(pos), the rest stays the same
     *
     */
    @Override
    public void placeAtPos(BoardPosition pos, char player) {
        board.get(getPLAYER_ONE()).remove(pos);
        board.get(getPLAYER_TWO()).remove(pos);
        //makes sure that if jump, then does not keep piece
        if (player == ' ' || player == '#'){
            return;
        }
        board.get(player).add(pos);
    }

    /**
     * Getter for pieceCount
     *
     * @return the piece counts for each player
     * @pre none
     * @post getPieceCount = the amount of pieces each player has AND board = #board
     */
    @Override
    public HashMap<Character, Integer> getPieceCount() {

        HashMap<Character, Integer> counts = new HashMap<>();

        counts.put(getPLAYER_ONE(), board.get(getPLAYER_ONE()).size());
        counts.put(getPLAYER_TWO(), board.get(getPLAYER_TWO()).size());

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
                writer.write(whatsAtPos(new BoardPosition(row,col)));
            }
            writer.newLine();
        }

        HashMap<Character, Integer> counts = getPieceCount();
        writer.write(getPLAYER_ONE() + ":" + counts.get(getPLAYER_ONE()));
        writer.newLine();
        writer.write(getPLAYER_TWO() + ":" + counts.get(getPLAYER_TWO()));
        writer.newLine();

        writer.close();


    }

    /**
     * loads a board state from a file
     *
     * @param filename name of file, a string
     * @throws IOException is an error occurs while reading the file
     * @pre filename != null AND file format is valid
     * @post board = positions loaded from file
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
                if(line.charAt(col) == getPLAYER_ONE()){
                    board.get(getPLAYER_ONE()).add(new BoardPosition(col, row));
                }else if(line.charAt(col) == getPLAYER_TWO()){
                    board.get(getPLAYER_TWO()).add(new BoardPosition(col,row));
                }
            }
        }
        reader.close();
    }
}
