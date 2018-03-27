/**
 * This class generates a "Queen" like in the boardgame Chess which can attack
 * other pieces vertically, horizontally, and diagonally in any direction.
 *
 * Nevan Samadhana
 * Student ID:1539153
 */

public class Queen{
    private int row; //Stores the position of the queen
    private int col;
    //Array containing queens that contain their individual positions

    public Queen(int c, int r)//Constructor for the queen clas
    {
        col= c;
        row = r;
    }

    public int returnCol(){ //Returns column of the queen instance
        return col;
    }

    public int returnRow(){ //Returns row of the queen instance
        return row;
    }

    public Boolean isAttacking(Queen q)//Checks if queen is a threat to others queens on the board
    {
        if(this.col == q.col || this.row == q.row||Math.abs(this.col-q.col)==Math.abs(this.row-q.row)) return true; //This checks whether the queen attacks on its respective row or column
        return false;
    }

}
