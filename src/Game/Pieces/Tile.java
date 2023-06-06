package Game.Pieces;

import Game.GamePanel;

import java.awt.*;
import java.util.LinkedList;

public class Tile{
  public enum tileColor {
    WHITE, BLACK
  }

  //fields for this class
  public int xPos;
  public int yPos;
  public boolean isValid = false;
  public boolean isSelected = false;
  public boolean inCheck = false;
  public boolean endTile;
  public tileColor color;
  GamePanel game;
  public int tileNumber;

  public Tile above = null;
  public Tile below = null;
  public Tile left = null;
  public Tile right = null;
  public GameObject currPiece = null;

  //constructor for class
  public Tile(int xPos, int yPos, tileColor color, GamePanel game, boolean endTile, int tileNumber) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = color;
    this.game = game;
    this.endTile = endTile;
    this.tileNumber = tileNumber;
  }

  //copy constructor
  public Tile(Tile tile, LinkedList<Tile> tiles) {
    this.tileNumber = tile.tileNumber;
    //find what object is on the tile
    if (tile.currPiece == null) {
      this.currPiece = null;
    }
    else if (tile.currPiece.toString().equals("pawn")) {
      this.currPiece = new Pawn((Pawn)tile.currPiece, this);
    }
    else if (tile.currPiece.toString().equals("bishop")) {
      this.currPiece = new Bishop((Bishop)tile.currPiece, this);
    }
    else if (tile.currPiece.toString().equals("knight")) {
      this.currPiece = new Knight((Knight)tile.currPiece, this);
    }
    else if (tile.currPiece.toString().equals("rook")) {
      this.currPiece = new Rook((Rook)tile.currPiece, this);
    }
    else if (tile.currPiece.toString().equals("queen")) {
      this.currPiece = new Queen((Queen)tile.currPiece, this);
    }
    else if (tile.currPiece.toString().equals("king")) {
      this.currPiece = new King((King)tile.currPiece, this, tiles);
    }
  }

  public void draw(Graphics2D graphics) {
    //check if tile is selected
    if (inCheck) {
      graphics.setColor(Color.red);
      //draw image
      graphics.fill3DRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, true);
      graphics.setColor(Color.BLACK);
      graphics.draw3DRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, true);
      return;
    }
    if (isSelected) {
      graphics.setColor(Color.green);
      //draw image
      graphics.fill3DRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, true);
      graphics.setColor(Color.BLACK);
      graphics.draw3DRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, true);
      return;
    }
    if (isValid) {
      graphics.setColor(Color.yellow);
      //draw image
      graphics.fill3DRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, true);
      graphics.setColor(Color.BLACK);
      graphics.draw3DRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, true);
      return;
    }
    //set object color
    graphics.setColor(Color.BLACK);

    if (color == tileColor.BLACK) {
      //draw image
      graphics.fillRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE);
    }
    else {
      //draw image
      graphics.drawRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE);
    }
  }
}
