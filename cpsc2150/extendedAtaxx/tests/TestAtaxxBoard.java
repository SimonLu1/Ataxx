package cpsc2150.extendedAtaxx.tests;

import cpsc2150.extendedAtaxx.models.AtaxxBoardMem;
import cpsc2150.extendedAtaxx.models.IAtaxxBoard;
import cpsc2150.extendedAtaxx.models.AtaxxBoard;
import cpsc2150.extendedAtaxx.models.BoardPosition;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAtaxxBoard {

    private IAtaxxBoard makeBoard() {
        return new AtaxxBoard(7,7,'X','O');
    }
    private String arrayToString(char[][] board) {
        StringBuilder s = new StringBuilder("|  | ");

        for (int col = 0; col < 7; col++) {
            s.append(col).append("| ");
        }
        s.append("\n");

        for (int row = 0; row < 7; row++) {
            s.append("|").append(row).append(" ");

            for (int col = 0; col < 7; col++) {
                s.append("|");
                s.append(" ").append(board[row][col]);

            }
            s.append("| ");

            if (row < 6) {
                s.append("\n");
            }
        }
        return s.toString();
    }


    @Test
    public void TestAtaxxBoard() {
        IAtaxxBoard ob = makeBoard();
        char[][] expBoard = {
                {'X',' ',' ',' ',' ',' ','O'},
                {' ',' ',' ',' ',' ',' ',' '},
                {' ',' ','#',' ','#',' ',' '},
                {' ',' ',' ',' ',' ',' ',' '},
                {' ',' ','#',' ','#',' ',' '},
                {' ',' ',' ',' ',' ',' ',' '},
                {'O',' ',' ',' ',' ',' ','X'}
        };
        String exp = arrayToString(expBoard);
        String obsString = ob.toString();
        assertEquals(exp, obsString);
    }

    @Test
    public void TestwhatsAtPos_0_0_X(){
        IAtaxxBoard ob = makeBoard();
        char obs = ob.whatsAtPos(new BoardPosition( 0, 0));
        char exp = 'X';
        assertEquals(exp, obs);


    }

    @Test
    public void TestwhatsAtpos_6_6_X(){
        IAtaxxBoard ob = makeBoard();
        char obs = ob.whatsAtPos(new BoardPosition(6,6));
        char exp = 'X';
        assertEquals(exp, obs);

    }

    @Test
    public void TestwhatsAtPos_0_6_O() {
        IAtaxxBoard ob = makeBoard();
        char obs = ob.whatsAtPos(new BoardPosition(0,6));
        char exp = 'O';
        assertEquals(exp, obs);

    }

    @Test
    public void TestwhatsAtPos_6_0_O(){
    IAtaxxBoard ob = makeBoard();
    char obs = ob.whatsAtPos(new BoardPosition(6, 0));
    char exp = 'O';

    assertEquals(exp, obs);
    }

    @Test
    public void TestwhatsAtPos_2_2_Block(){
    IAtaxxBoard ob = makeBoard();
    char obs = ob.whatsAtPos(new BoardPosition(2,2));
    char exp = '#';
    assertEquals(exp, obs);
    }

    @Test
    public void TestplaceAtPos_0_0_O() {
        IAtaxxBoard ob = makeBoard();
        ob.placeAtPos(new BoardPosition(0, 0), 'O');
        char obs = ob.whatsAtPos(new BoardPosition(0, 0));
        char exp = 'O';
        assertEquals(exp, obs);
    }

    @Test
    public void TestplaceAtPos_6_6_O() {
        IAtaxxBoard ob = makeBoard();
        ob.placeAtPos(new BoardPosition(6, 6), 'O');
        char obs = ob.whatsAtPos(new BoardPosition(6, 6));
        char exp = 'O';
        assertEquals(exp, obs);
    }

    @Test
    public void TestplaceAtPos_6_0_X() {
        IAtaxxBoard ob = makeBoard();
        ob.placeAtPos(new BoardPosition(6, 0), 'X');
        char obs = ob.whatsAtPos(new BoardPosition(6, 0));
        char exp = 'X';
        assertEquals(exp, obs);
    }

    @Test
    public void TestplaceAtPos_0_6_X() {
        IAtaxxBoard ob = makeBoard();
        ob.placeAtPos(new BoardPosition(0, 6), 'X');
        char obs = ob.whatsAtPos(new BoardPosition(0, 6));
        char exp = 'X';
        assertEquals(exp, obs);
    }

    @Test
    public void TestplaceAtPos_3_3_X() {
        IAtaxxBoard ob = makeBoard();
        ob.placeAtPos(new BoardPosition(3, 3), 'X');
        char obs = ob.whatsAtPos(new BoardPosition(3, 3));
        char exp = 'X';
        assertEquals(exp, obs);
    }

    @Test
    public void TestPieceCount() {
        IAtaxxBoard ob = makeBoard();
        Map<Character, Integer> counts = ob.getPieceCount();
        assertEquals(2,(int) counts.get('X'));
        assertEquals(2,(int) counts.get('O'));
    }

    @Test
    public void TestValidMove_0_1_X() {
        IAtaxxBoard ob = makeBoard();
        boolean obs = ob.isValidMove(new BoardPosition(0,1), 'X');
        boolean exp = true;
        assertEquals(exp,obs);
    }

    @Test
    public void TestSaveFile() throws Exception {
        IAtaxxBoard ab = makeBoard();
        String filename = "testLoad.txt";
        String directory = "cpsc2150\\extendedAtaxx\\boardStates\\";

        ab.saveBoardToFile(filename);

        java.io.File file = new java.io.File(directory + filename);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testLoadBoardFromFile() throws Exception {
        IAtaxxBoard ob = makeBoard();
        ob.placeAtPos(new BoardPosition(3,3),'X');
        ob.saveBoardToFile("testLoad.txt");

        IAtaxxBoard ob2 = makeBoard();
        ob2.loadBoardFromFile("testLoad.txt");

        String obs = ob.toString();
        String exp ="|  | 0| 1| 2| 3| 4| 5| 6| \n" +
                    "|0 | X|  |  |  |  |  | O| \n" +
                    "|1 |  |  |  |  |  |  |  | \n" +
                    "|2 |  |  | #|  | #|  |  | \n" +
                    "|3 |  |  |  | X|  |  |  | \n" +
                    "|4 |  |  | #|  | #|  |  | \n" +
                    "|5 |  |  |  |  |  |  |  | \n" +
                    "|6 | O|  |  |  |  |  | X| ";
        assertEquals(exp, obs);
    }

    @Test
    public void TestInvalidMove_OwnPiece() {
        IAtaxxBoard ob = makeBoard();
        boolean obs = ob.isValidMove(new BoardPosition(0,0), 'X');
        boolean exp = false;
        assertEquals(exp, obs);
    }
    @Test
    public void TestInvalidMove_Block() {
        IAtaxxBoard ob = makeBoard();
        boolean obs = ob.isValidMove(new BoardPosition(2,2), 'X');
        boolean exp = false;
        assertEquals(exp, obs);
    }

    @Test
    public void TestValidJumpMove_X() {
        IAtaxxBoard ob = makeBoard();
        boolean obs = ob.isValidMove(new BoardPosition(2,0), 'X');
        boolean exp = true;
        assertEquals(exp, obs);
    }

    @Test
    public void TestInvalidMove_EmptyNotReachable() {
        IAtaxxBoard ob = makeBoard();
        boolean obs = ob.isValidMove(new BoardPosition(3, 3), 'X');
        boolean exp = false;
        assertEquals(exp, obs);
    }

    @Test
    public void TestInvalidOutOfRange() {
        IAtaxxBoard ob = makeBoard();
        boolean obs = ob.isValidMove(new BoardPosition(7,7), 'O');
        boolean exp = false;
        assertEquals(exp, obs);
    }
    @Test
    public void TestConvertSinglePiece() {
        IAtaxxBoard ob = makeBoard();

        ob.placeAtPos(new BoardPosition(2,2), 'O');
        ob.placeAtPos(new BoardPosition(3,3), 'X');

        ob.convertAdjacentPieces(new BoardPosition(3,3), 'X');

        char obs = ob.whatsAtPos(new BoardPosition(2,2));
        char exp = 'X';
        assertEquals(exp, obs);
    }
    @Test
    public void TestConvertMultiplePieces() {
        IAtaxxBoard ob = makeBoard();

        ob.placeAtPos(new BoardPosition(2,2), 'O');
        ob.placeAtPos(new BoardPosition(2,3), 'O');
        ob.placeAtPos(new BoardPosition(4,2), 'O');
        ob.placeAtPos(new BoardPosition(4,3), 'O');

        ob.placeAtPos(new BoardPosition(3,3), 'X');

        ob.convertAdjacentPieces(new BoardPosition(3,3), 'X');

        char obs = ob.whatsAtPos(new BoardPosition(2,2));
        char exp = 'X';
        assertEquals(exp, obs);
    }

    @Test
    public void TestNoConversion() {
        IAtaxxBoard ob = makeBoard();

        ob.placeAtPos(new BoardPosition(3,3), 'X');

        ob.convertAdjacentPieces(new BoardPosition(3,3), 'X');

        char obs = ob.whatsAtPos(new BoardPosition(3,3));
        char exp = 'X';
        assertEquals(exp, obs);
    }

    @Test
    public void TestEdgeConversion() {
        IAtaxxBoard ob = makeBoard();

        ob.placeAtPos(new BoardPosition(0,1), 'O');

        ob.convertAdjacentPieces(new BoardPosition(0,0), 'X');

        char obs = ob.whatsAtPos(new BoardPosition(0,1));
        char exp = 'X';
        assertEquals(exp, obs);
    }

    @Test
    public void TestCornerConversion() {
        IAtaxxBoard ob = makeBoard();

        ob.placeAtPos(new BoardPosition(1,0), 'O');
        ob.placeAtPos(new BoardPosition(0,0), 'X');

        ob.convertAdjacentPieces(new BoardPosition(1,0), 'O');

        char obs = ob.whatsAtPos(new BoardPosition(0,0));
        char exp = 'O';
        assertEquals(exp, obs);
    }

    @Test
    public void TestMixedAdjacentPieces() {
        IAtaxxBoard ob = makeBoard();

        ob.placeAtPos(new BoardPosition(2, 2), 'O');
        ob.placeAtPos(new BoardPosition(2, 3), 'X');
        ob.placeAtPos(new BoardPosition(4, 2), 'O');
        ob.placeAtPos(new BoardPosition(4, 3), 'X');

        ob.placeAtPos(new BoardPosition(3, 3), 'X');

        ob.convertAdjacentPieces(new BoardPosition(3, 3), 'X');

        char obs = ob.whatsAtPos(new BoardPosition(2, 2));
        char exp = 'X';
        assertEquals(exp, obs);
    }

        @Test
        public void TestNoMorePlayer_O_CheckForWin() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'O','O','O','O',' ',' ',' '},
                    {'O','O','O','O',' ',' ',' '},
                    {'O','O','#','#','O','O',' '},
                    {'O',' ',' ',' ',' ',' ',' '},
                    {'O','#','#','O','O',' ',' '},
                    {'O','O','O',' ',' ',' ',' '},
                    {'O','O','O','O',' ',' ',' '}
            };

            for(int r = 0; r < ob.getROWS(); r++) {
                for(int c = 0; c < ob.getCOLS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.checkForWin('O');
            boolean exp = true;
            assertEquals(exp, obs);
        }

    @Test
        public void TestCheckForWin_False_Tie_X() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'X','X','X','O','O','O','O'},
                    {'X','X','X','O','O','O','O'},
                    {'X','X','#','O','#','O','O'},
                    {'X','X','X','O','O','O','O'},
                    {'X','X','#','O','#','O','O'},
                    {'X','X','X','O','O','O','O'},
                    {'X','X','X','X','O','O','O'}
            };

            for (int r = 0; r < ob.getROWS(); r++) {
                for (int c = 0; c < ob.getCOLS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.checkForWin('X');
            boolean exp = false;
            assertEquals(exp, obs);
        }


    @Test
        public void TestCheckForWin_False_NoWinnerYet_O() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'X','X',' ',' ',' ','O','O'},
                    {' ',' ',' ',' ',' ',' ',' '},
                    {' ',' ','#',' ','#',' ',' '},
                    {' ',' ',' ',' ',' ',' ',' '},
                    {' ',' ','#',' ','#',' ',' '},
                    {' ',' ',' ',' ',' ',' ',' '},
                    {'O','O',' ',' ',' ','X','X'}
            };

            for (int r = 0; r < ob.getROWS(); r++) {
                for (int c = 0; c < ob.getCOLS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.checkForWin('O');
            boolean exp = false;
            assertEquals(exp, obs);
        }


    @Test
        public void TestCheckforWin_Player_O_most() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'X','X','X','X','X','X','X'},
                    {'O','O','O','O','O','O','O'},
                    {'X','O','#','O','#','O','X'},
                    {'O','O','O','O','O','O','O'},
                    {'X','O','#','O','#','O','X'},
                    {'X','O','O','O','O','O','X'},
                    {'X','X','X','X','X','X','X'}
            };

            for(int r = 0; r < ob.getROWS(); r++) {
                for(int c = 0; c < ob.getCOLS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.checkForWin('O');
            boolean exp = true;
            assertEquals(exp, obs);
        }

        @Test
        public void TestIsGameOverBoard_Filled_O() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'O','O','O','O','O','O','O'},
                    {'O','O','O','O','O','O','O'},
                    {'O','O','#','O','#','O','O'},
                    {'O','O','O','O','O','O','O'},
                    {'O','O','#','O','#','O','O'},
                    {'O','O','O','O','O','O','O'},
                    {'O','O','O','O','O','O','O'}
            };

            for(int r = 0; r < ob.getROWS(); r++) {
                for(int c = 0; c < ob.getCOLS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.isGameOver();
            boolean exp = true;
            assertEquals(exp, obs);
        }

    @Test
        public void TestIsGameOver_False_GameNotOverYet() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'X','X',' ',' ',' ','O','O'},
                    {' ',' ',' ',' ',' ',' ',' '},
                    {' ',' ','#',' ','#',' ',' '},
                    {' ',' ',' ',' ',' ',' ',' '},
                    {' ',' ','#',' ','#',' ',' '},
                    {' ',' ',' ',' ',' ',' ',' '},
                    {'O','O',' ',' ',' ','X','X'}
            };

            for (int r = 0; r < ob.getROWS(); r++) {
                for (int c = 0; c < ob.getROWS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.isGameOver();
            boolean exp = false;
            assertEquals(exp, obs);
        }

        @Test
        public void TestIsGameOver_NoMorePlayer_O_GameOver() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'X','X','X','X','X',' ',' '},
                    {'X','X','X','X',' ',' ',' '},
                    {'X','X','#','#',' ',' ',' '},
                    {'#','#',' ',' ',' ',' ',' '},
                    {'X','X','X','X',' ',' ',' '},
                    {'X','X','X','X',' ',' ',' '},
                    {' ',' ',' ',' ',' ',' ',' '}
            };

            for(int r = 0; r < ob.getROWS(); r++) {
                for(int c = 0; c < ob.getCOLS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.isGameOver();
            boolean exp = true;
            assertEquals(exp, obs);
        }

    @Test
        public void TestOnePlayerNoMoves_GameOver() {
            IAtaxxBoard ob = makeBoard();

            char[][] state = {
                    {'X','X',' ','X','X','O','O'},
                    {' ',' ',' ','X','X','X','X'},
                    {' ',' ','#','X','#','X','X'},
                    {' ',' ',' ',' ',' ',' ',' '},
                    {'X','X','#','X','#',' ',' '},
                    {'X','X','X','X',' ',' ',' '},
                    {'O','O','X','X',' ','X','X'}
            };

            for (int r = 0; r < ob.getROWS(); r++) {
                for (int c = 0; c < ob.getCOLS(); c++) {
                    ob.placeAtPos(new BoardPosition(r, c), state[r][c]);
                }
            }

            boolean obs = ob.isGameOver();
            boolean exp = true;
            assertEquals(exp, obs);
        }
    @Test
    public void TesttoString(){
        IAtaxxBoard ob = makeBoard();
        String obs = ob.toString();
        String exp ="|  | 0| 1| 2| 3| 4| 5| 6| \n" +
                    "|0 | X|  |  |  |  |  | O| \n" +
                    "|1 |  |  |  |  |  |  |  | \n" +
                    "|2 |  |  | #|  | #|  |  | \n" +
                    "|3 |  |  |  |  |  |  |  | \n" +
                    "|4 |  |  | #|  | #|  |  | \n" +
                    "|5 |  |  |  |  |  |  |  | \n" +
                    "|6 | O|  |  |  |  |  | X| ";
        assertEquals(exp, obs);
    }

}



