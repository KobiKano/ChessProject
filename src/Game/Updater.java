package Game;

import Game.Pieces.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;


public class Updater implements MouseListener {
  public boolean leftPressed = false;
  public int xPos = 0;
  public int yPos = 0;
  LinkedList<GameObject> gameObjects;
  GamePanel game;
  GameObject currPiece = null;
  GameObject changePiece = null;
  LinkedList<Tile> possibleMoves = null;
  GameObject pieceToRemove = null;

  //constructor for class
  public Updater(LinkedList<GameObject> gameObjects, GamePanel game) {
    this.gameObjects = gameObjects;
    this.game = game;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    xPos = e.getX();
    yPos = e.getY();
    //check if a piece is not selected
    if (currPiece == null) {
      //check if mouse position valid for piece to be selected
      int xMax, xMin;
      int yMax, yMin;

      //iterate through all game objects
      for (GameObject object : gameObjects) {
        xMax = object.getXPos() + object.getSize();
        xMin = object.getXPos();
        yMax = object.getYPos() + object.getSize();
        yMin = object.getYPos();
        //check if mouse is over game object
        if (xPos > xMin && yPos > yMin && xPos < xMax && yPos < yMax) {
          System.out.println("Selected Object: " + object);
          //check if not turn
          if ((object.getColor() == GameObject.tileColor.BLACK && !game.blackTurn) ||
                  (object.getColor() == GameObject.tileColor.WHITE && game.blackTurn)) {
            System.out.println("Not your turn!");
            return;
          }

          currPiece = object;
          object.getCurrTile().isSelected = true;
          possibleMoves = currPiece.getPossibleMoves();
          //set all possible tiles to selected
          for (Tile tile : possibleMoves) {
            tile.isValid = true;
          }
          break;
        }
      }
    }
    //else check if click on valid move
    else {
      int xMax, xMin;
      int yMax, yMin;

      //check if piece is deselected
      xMax = currPiece.getXPos() + currPiece.getSize();
      xMin = currPiece.getXPos();
      yMax = currPiece.getYPos() + currPiece.getSize();
      yMin = currPiece.getYPos();
      if (xPos > xMin && yPos > yMin && xPos < xMax && yPos < yMax) {
        System.out.println("Piece unselected: " + currPiece.toString());
        //deselect all tiles and piece
        for (Tile tile : possibleMoves) {
          tile.isValid = false;
        }
        currPiece.getCurrTile().isSelected = false;
        possibleMoves = null;
        currPiece = null;
        return;
      }

      for (Tile tile : possibleMoves) {
        xMax = tile.xPos + 16*5;
        xMin = tile.xPos;
        yMax = tile.yPos + 16*5;
        yMin = tile.yPos;

        if (xPos > xMin && yPos > yMin && xPos < xMax && yPos < yMax) {
          //deselect current tile
          currPiece.getCurrTile().isSelected = false;
          System.out.println("Tile selected");
          //check if piece taken
          if (tile.currPiece != null) {
            pieceToRemove = tile.currPiece;
          }

          //check if object is pawn king or rook
          if (currPiece.toString().equals("pawn")) {
            ((Pawn)currPiece).firstMove = false;
            //check if pawn on end tile
            if (tile.endTile) {
              changePiece = currPiece;
              Thread changeThread = new Thread(this::changePiece);
              changeThread.start();
            }
          }
          if (currPiece.toString().equals("king")) {
            ((King)currPiece).beenMoved = true;
          }
          if (currPiece.toString().equals("rook")) {
            ((Rook)currPiece).beenMoved = true;
          }

          //check if right castle
          if (currPiece.toString().equals("king") && ((King)currPiece).rightCastle) {
            //check if castle tile chosen
            if(tile.equals(currPiece.getCurrTile().right.right)) {
              //move rook
              GameObject rook = tile.right.currPiece;
              tile.right.currPiece = null;
              rook.setXPos(tile.left.xPos);
              rook.setYPos(tile.left.yPos);
              rook.setCurrTile(tile.left);
              rook.getCurrTile().currPiece = rook;
              ((King)currPiece).rightCastle = false;
            }
          }

          //check if left castle
          if (currPiece.toString().equals("king") && ((King)currPiece).leftCastle) {
            //check if castle tile chosen
            if(tile.equals(currPiece.getCurrTile().left.left)) {
              //move rook
              GameObject rook = tile.left.left.currPiece;
              tile.left.left.currPiece = null;
              rook.setXPos(tile.right.xPos);
              rook.setYPos(tile.right.yPos);
              rook.setCurrTile(tile.right);
              rook.getCurrTile().currPiece = rook;
              ((King)currPiece).leftCastle = false;
            }
          }

          //switch turn boolean
          game.blackTurn = !game.blackTurn;

          //move piece
          currPiece.getCurrTile().currPiece = null;
          currPiece.setXPos(tile.xPos);
          currPiece.setYPos(tile.yPos);
          currPiece.setCurrTile(tile);
          currPiece.getCurrTile().currPiece = currPiece;

          //deselect all tiles and piece
          for (Tile tile2 : possibleMoves) {
            tile2.isValid = false;
          }
          possibleMoves = null;
          currPiece = null;
          return;
        }
      }

      //if no possible match found
      System.out.println("invalid move!");
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    int mouseCode = e.getButton();
    //check for left mouse pressed
    if (mouseCode == MouseEvent.BUTTON1) {
      leftPressed = true;
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    int mouseCode = e.getButton();
    //check for mouse released
    if (mouseCode == MouseEvent.MOUSE_RELEASED) {
      leftPressed = false;
    }

  }
  @Override
  public void mouseEntered(MouseEvent e) {
  }
  @Override
  public void mouseExited(MouseEvent e) {
  }

  private void changePiece() {
    System.out.println("Changing Piece");
    //allow piece change
    PieceChanger pieceChanger = new PieceChanger(game, changePiece);
    game.window.setVisible(false);
    game.window.remove(game);
    game.window.add(pieceChanger);
    game.window.pack();
    game.window.setVisible(true);
    while(!pieceChanger.pieceChosen) {System.out.print("");}  //wait for piece to be chosen
    game.window.setVisible(false);
    game.window.remove(pieceChanger);
    game.window.add(game);
    game.window.pack();
    game.window.setVisible(true);
  }

}
