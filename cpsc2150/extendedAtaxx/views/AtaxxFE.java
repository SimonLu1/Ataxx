package cpsc2150.extendedAtaxx.views;

import cpsc2150.extendedAtaxx.models.*;

import java.util.HashMap;
import java.io.IOException;
import java.util.Scanner;

public class AtaxxFE
{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            AbsAtaxxBoard board = new AtaxxBoardMem(7,7,'x', 'o');
            char currentPlayer = board.getPLAYER_ONE();

            while (!board.isGameOver()) {
                System.out.println(board);
                System.out.println("Player " + currentPlayer + ", it is your turn.");

                BoardPosition fromPos = getSourcePosition(in, board, currentPlayer);
                BoardPosition toPos = getDestinationPosition(in, board, fromPos, currentPlayer);

                int rowDiff = Math.abs(toPos.getRow() - fromPos.getRow());
                int colDiff = Math.abs(toPos.getCol() - fromPos.getCol());
                boolean isJump = Math.max(rowDiff, colDiff) == 2;

                if (isJump) {
                    board.placeAtPos(fromPos, ' ');
                }

                board.placeAtPos(toPos, currentPlayer);
                board.convertAdjacentPieces(toPos, currentPlayer);

                if (currentPlayer == board.getPLAYER_ONE()) {
                    currentPlayer = board.getPLAYER_TWO();
                } else {
                    currentPlayer = board.getPLAYER_ONE();
                }
            }
            System.out.println(board);
            printWinner(board);
            playAgain = askPlayAgain(in);
        }
    }

    private static BoardPosition getSourcePosition(Scanner in, AbsAtaxxBoard board, char player)
    {
        while (true)
        {
            int row = readInt(in, "Enter the row of the piece you want to move: ");
            int col = readInt(in, "Enter the column of the piece you want to move: ");

            if (!inBounds(board,row , col))
            {
                System.out.println("That position is out of bounds. Try again.");
                continue;
            }

            BoardPosition pos = new BoardPosition(row, col);

            if (board.whatsAtPos(pos) != player)
            {
                System.out.println("That is not one of your pieces. Try again.");
                continue;
            }

            if (!pieceHasMove(board, pos, player))
            {
                System.out.println("That piece has no valid moves. Try again.");
                continue;
            }

            return pos;
        }
    }

    private static BoardPosition getDestinationPosition(Scanner in, AbsAtaxxBoard board,
                                                        BoardPosition fromPos, char player)
    {
        while (true)
        {
            int row = readInt(in, "Enter the destination row: ");
            int col = readInt(in, "Enter the destination column: ");

            if (!inBounds(board ,row, col))
            {
                System.out.println("That position is out of bounds. Try again.");
                continue;
            }

            BoardPosition toPos = new BoardPosition(row, col);

            int rowDiff = Math.abs(toPos.getRow() - fromPos.getRow());
            int colDiff = Math.abs(toPos.getCol() - fromPos.getCol());
            int maxDiff = Math.max(rowDiff, colDiff);

            if (maxDiff == 0 || maxDiff > 2)
            {
                System.out.println("You must move one or two spaces away. Try again.");
                continue;
            }

            if (!board.isValidMove(toPos, player))
            {
                System.out.println("That destination is not valid. Try again.");
                continue;
            }

            return toPos;
        }
    }
    private static int readInt(Scanner in, String prompt)
    {
        while (true)
        {
            System.out.print(prompt);

            if (in.hasNextInt())
            {
                return in.nextInt();
            }

            System.out.println("Invalid input. Please enter an integer.");
            in.next();
        }
    }
    private static boolean inBounds(AbsAtaxxBoard board, int row, int col)
    {
        return row >= 0 && row < board.getROWS() &&
                col >= 0 && col < board.getCOLS();
    }

    private static boolean pieceHasMove(AbsAtaxxBoard board, BoardPosition pos, char player)
    {
        for (int row = pos.getRow() - 2; row <= pos.getRow() + 2; row++)
        {
            for (int col = pos.getCol() - 2; col <= pos.getCol() + 2; col++)
            {
                if (inBounds(board, row, col))
                {
                    if (board.isValidMove(new BoardPosition(row, col), player))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    private static void printWinner(AbsAtaxxBoard board)
    {
        HashMap<Character, Integer> counts = board.getPieceCount();
        int xCount = counts.get(board.getPLAYER_ONE());
        int oCount = counts.get(board.getPLAYER_TWO());

        System.out.println("Final counts: X = " + xCount + ", O = " + oCount);

        if (xCount > oCount)
        {
            System.out.println("Player X wins!");
        }
        else if (oCount > xCount)
        {
            System.out.println("Player O wins!");
        }
        else
        {
            System.out.println("It is a tie!");
        }
    }
    private static boolean askPlayAgain(Scanner in)
    {
        while (true)
        {
            System.out.print("Would you like to play again? (y/n): ");
            String answer = in.next().trim().toLowerCase();

            if (answer.equals("y") || answer.equals("yes"))
            {
                return true;
            }
            else if (answer.equals("n") || answer.equals("no"))
            {
                return false;
            }
            else
            {
                System.out.println("Please enter y or n.");
            }
        }
    }
}

