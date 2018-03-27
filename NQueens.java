import java.io.*;
import java.util.*;

class NQueens{
  public static void main(String[] args) throws IOException{
    //Copied this if statement from Lab2.
    if(args.length < 2){
     System.out.println("Usage: java –jar nQueens.jar <input file> <output file>");
     System.exit(1);
    }

    Scanner in = new Scanner(new File(args[0]));
    PrintWriter out = new PrintWriter(new FileWriter(args[1]));

    //Copied while loop code from Lab2
    while(in.hasNextLine()){
      Boolean flag = true;
      String line = in.nextLine(); //String line is every consecutive line
      String[] tokens = line.split("\\s+"); //Splits white space
      int boardSize = Integer.parseInt(tokens[0]); //The very first token value is the size of the board
      ArrayList<Integer> freeColumn = new ArrayList<Integer>(boardSize);
      Stack<Queen> stack = new Stack<Queen>(); //Create a stack which represents the Queens
      for(int i=1; i<tokens.length;i=i+2){ //
          int qCol = Integer.parseInt(tokens[i]); //
          int qRow = Integer.parseInt(tokens[i+1]);
          stack.push(new Queen(qCol,qRow)); //Push the initial queens into the stack
      }

      if(stack.size()!=1){ //If given more than one queen, check if the queens are attacking one another
        if(stackIsAttacking(stack)){
          flag = false;
          out.println("No solution");
        }
      } //End of check for invalid queens given board;

      freeColumn.add(0);
      for(int i =1; i<boardSize+1; i++){ //This adds a free column into an array list
        freeColumn.add(i);
      }
      freeColumn(freeColumn, stack); //Deletes the columns that are used in the arraylist
      if(freeColumn.size()==1){
        flag = false;
        printStack(out, stack);
      }

      outerloop: //Looked up how to use this online
      while(flag==true){
        for(int i = 1; i<freeColumn.size();){ //Simulates columns of the board
          for(int j = 1; j<boardSize+1; j++){ //Simulates Rows of the board
            if(stack.size()==boardSize){ //Base case with a solution (DONE)
              printStack(out, stack);
              break outerloop;
            }

            stack.push(new Queen(freeColumn.get(i),j)); //Add the queen
            if(stackIsAttacking(stack)){
              if(stack.peek().returnRow()==boardSize){//Case where you have exhausted all rows
                stack.pop();
                if(stack.peek().returnRow() == boardSize && stack.size()!=1){//check if the last queen is at the top of the row
                  stack.pop();
                  j = stack.peek().returnRow(); //Move on from where you left off in the row b/c j will iterate +1 when the for loop starts up again
                  i=i-2; //Subtract by two because you now want to go two columns back due to the two most recent queens being in the top row
                  stack.pop();
                }else{
                  i--;
                  j=stack.peek().returnRow();
                  stack.pop();
                }

                if(i<=0){ //Base case when the array list containing free columns gets to zero
                  flag = false;
                  out.println("No solution");
                  break outerloop;
                }

              }else{
                  stack.pop(); //pop the queen and move on to the next row
               }
            }else{ //If not attacking move on to the next column
              i++;
              break;
            }
        } //End of first for loop
      }//End of second for loop
    }

    } //End of while loop loop
      in.close();
      out.close();
    }


    public static void printStack(PrintWriter out, Stack<Queen> stack) {
      for(int i = 1; i< stack.size()+1; i++){ //Outer loop is for iterating over column numbers in the stack
        for(int j = 0; j< stack.size(); j++){ //Inner loop is for comparing the column number to the column values of everything in the stack
          if(stack.get(j).returnCol()==i){ //Searches for ascending column order to print the queen with that column value
            out.print(stack.get(j).returnCol()+" "+stack.get(j).returnRow()+" ");
          }
        }
      }
      out.println();
    }


    public static void freeColumn(ArrayList<Integer> freeColumn, Stack<Queen> stack){
      for(int i=stack.size()-1; i >= 0; i--){
        freeColumn.remove(new Integer(stack.get(i).returnCol()));
      }
    }

    public static boolean stackIsAttacking(Stack<Queen> stack){ //Returns true if the queens in the stack are attacking each other
      for(int i = stack.size()-2; i>=0; i--){
        if(stack.peek().isAttacking(stack.get(i))){
          return true;
        }
      }
      return false;
    }


}
