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
          board[j][i] = String.valueOf(tiles.get(index).tileNumber);
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
  }

  public int getValue(boolean isWhite) {
    int output = 0;

    //iterate through board
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        //check piece values
        if (board[i][j].equals("wp")) {
          output += 10;
          //check if pawn has been moved and weigh accordingly
          if (i == 6) {
            output += 10;
          }
        }
        else if (board[i][j].equals("bp")) {
          output -= 10;
          //check if pawn has been moved and weigh accordingly
          if (i == 1) {
            output -= 5;
          }
        }
        else if (board[i][j].equals("wb")) {
          output += 30;
        }
        else if (board[i][j].equals("bb")) {
          output -= 30;
        }
        else if (board[i][j].equals("wk")) {
          output += 30;
          //add weight for knight being closer to center
          if (j > 1 && j < 6) {
            output += 7;
          }
        }
        else if (board[i][j].equals("bk")) {
          output -= 30;
          //add weight for knight being closer to center
          if (j > 1 && j < 6) {
            output -= 7;
          }
        }
        else if (board[i][j].equals("wr")) {
          output += 50;
          //weight AI to move rooks out of corners
          if ((i == 7 && j == 0) || (i == 7 && j == 7)) {
            output -= 2;
          }
        }
        else if (board[i][j].equals("br")) {
          output -= 50;
          //weight AI to move rooks out of corners
          if ((i == 0 && j == 7) || (i == 0 && j == 0)) {
            output += 2;
          }
        }
        else if (board[i][j].equals("wq")) {
          output += 90;
        }
        else if (board[i][j].equals("bq")) {
          output -= 90;
        }
        else if (board[i][j].equals("wK")) {
          output += 1000;
          //weight AI to have king closer to corner
          if (j > 6 || j < 2) {
            output += 15;
          }
        }
        else if (board[i][j].equals("bK")) {
          output -= 1000;
          if (j > 6 || j < 2) {
            output -= 15;
          }
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




  public void printBoard() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println("");
    }
    System.out.println("");
  }
}
