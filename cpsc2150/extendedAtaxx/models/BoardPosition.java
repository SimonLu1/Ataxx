package cpsc2150.extendedAtaxx.models;

import java.util.Objects;

public class BoardPosition
{
    private int row;
    private int col;

    /**
     * parameterized constructor for BoardPosition
     *
     * @param aRow row that is being applied to BoardPosition
     * @param aCol column that is being applied to BoardPosition
     *
     * @pre aRow >= 0 AND aCol >= 0
     *
     * @post row = aRow AND col = aCol
     */
    public BoardPosition(int aRow, int aCol)
    {
        row = aRow;
        col = aCol;
    }

    /**
     * getter for BoardPosition's row
     *
     * @return an int representing BoardPosition's row
     *
     * @pre none
     *
     * @post getRow = row AND row = #row AND col = #col
     */
    public int getRow()
    {
        return row;
    }

    /**
     * getter for BoardPosition's column
     *
     * @return an int representing BoardPosition's column
     *
     * @pre none
     *
     * @post getCol  = col AND col = #col AND row = #row
     */
    public int getCol()
    {
        return col;
    }

    /**
     *
     * Indicates whether some other object is "equal to" this one
     *
     * @param o the object to compare to
     *
     * @return true if the objects are equal, false otherwise
     *
     * @pre none
     *
     * @post equals = true if o == what it is being compared to else false
     */

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BoardPosition)) {
            return false;
        }

        BoardPosition that = (BoardPosition) o;
        return row == that.row && col == that.col;
    }

    /**
     * this return the string with the row and col
     *
     * @return the sting with the row and age
     *
     * @pre none
     *
     *@post tostring = "(" + row + "," + col +")" And row = #row AND col = #col
     */

    @Override
    public String toString()
    {
        return row + "," + col;
    }
}
