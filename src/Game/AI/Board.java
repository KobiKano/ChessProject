package Game.AI;

import Game.Pieces.*;

import java.util.LinkedList;

public class Board {

  String[][] board;

  public Board(LinkedList<Tile> tiles) {
    board = new String[8][8];
    int index = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        //check what should be added to board
        if (tiles.get(index).currPiece == null) {
          board[j][i] = "0";
        }
        else if (tiles.get(index).currPiece.toString().equals("pawn")) {
          //check color
          if (tiles.get(index).currPiece.getColor().equals(GameObject.tileColor.BLACK)) {
            board[j][i] = "bp";
          }
          else {
            board[j][i] = "wp";
          }
        }
        else if (tiles.get(index).currPiece.toString().equals("rook")) {
          if (tiles.get(index).currPiece.getColor().equals(GameObject.tileColor.BLACK)) {
            board[j][i] = "br";
          }
          else {
            board[j][i] = "wr";
          }
        }
        else if (tiles.get(index).currPiece.toString().equals("knight")) {
          if (tiles.get(index).currPiece.getColor().equals(GameObject.tileColor.BLACK)) {
            board[j][i] = "bk";
          }
          else {
            board[j][i] = "wk";
          }
        }
        else if (tiles.get(index).currPiece.toString().equals("bishop")) {
          if (tiles.get(index).currPiece.getColor().equals(GameObject.tileColor.BLACK)) {
            board[j][i] = "bb";
          }
          else {
            board[j][i] = "wb";
          }
        }
        else if (tiles.get(index).currPiece.toString().equals("queen")) {
          if (tiles.get(index).currPiece.getColor().equals(GameObject.tileColor.BLACK)) {
            board[j][i] = "bq";
          }
          else {
            board[j][i] = "wq";
          }
        }
        else if (tiles.get(index).currPiece.toString().equals("king")) {
          if (tiles.get(index).currPiece.getColor().equals(GameObject.tileColor.BLACK)) {
            board[j][i] = "bK";
          }
          else {
            board[j][i] = "wK";
          }
        }
        //increment index
        index++;
      }
    }

    printBoard();
  }

  public int getValue(boolean isWhite) {
    int output = 0;

    //iterate through board
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        //check piece values
        if (board[i][j].equals("wp")) {
          output += 1;
        }
        else if (board[i][j].equals("bp")) {
          output -= 1;
        }
        else if (board[i][j].equals("wb")) {
          output += 3;
        }
        else if (board[i][j].equals("bb")) {
          output -= 3;
        }
        else if (board[i][j].equals("wk")) {
          output += 3;
        }
        else if (board[i][j].equals("bk")) {
          output -= 3;
        }
        else if (board[i][j].equals("wr")) {
          output += 5;
        }
        else if (board[i][j].equals("br")) {
          output -= 5;
        }
        else if (board[i][j].equals("wq")) {
          output += 9;
        }
        else if (board[i][j].equals("bq")) {
          output -= 9;
        }
        else if (board[i][j].equals("wK")) {
          output += 1000;
        }
        else if (board[i][j].equals("bK")) {
          output -= 1000;
        }
      }
    }

    //depending on who is maximizing change output
    if (!isWhite) {
      output = -output;
    }

    //return output
    return output;
  }




  private void printBoard() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println("");
    }
    System.out.println("");
  }
}
