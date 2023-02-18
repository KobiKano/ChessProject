package Game;

import Game.Pieces.GameObject;
import Game.Pieces.Pawn;
import Game.Pieces.Tile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class Updater implements MouseListener {
  public boolean leftPressed = false;
  public int xPos = 0;
  public int yPos = 0;
  LinkedList<GameObject> gameObjects;
  GamePanel game;
  GameObject currPiece = null;
  ArrayList<Tile> possibleMoves = null;
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
          possibleMoves = currPiece.getPossibleMoves();
          //set all possible tiles to selected
          for (Tile tile : possibleMoves) {
            tile.isSelected = true;
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
        //check if piece is a pawn
        if (currPiece.toString().equals("pawn")) {
          ((Pawn)currPiece).firstMove = true;
        }
        //deselect all tiles and piece
        for (Tile tile : possibleMoves) {
          tile.isSelected = false;
        }
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
          System.out.println("Tile selected");
          //check if piece taken
          if (tile.currPiece != null) {
            pieceToRemove = tile.currPiece;
          }

          //switch turn boolean
          game.blackTurn = !game.blackTurn;

          currPiece.setXPos(tile.xPos);
          currPiece.setYPos(tile.yPos);
          currPiece.setCurrTile(tile);

          //deselect all tiles and piece
          for (Tile tile2 : possibleMoves) {
            tile2.isSelected = false;
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
}
