package Game.Pieces;

import Game.GamePanel;
import Game.InputChecker;

import java.awt.*;

public class Tile{
  public enum tileColor {
    WHITE, BLACK
  }

  //fields for this class
  public int xPos;
  public int yPos;
  public boolean isSelected = false;
  public tileColor color;
  GamePanel game;

  public Tile above = null;
  public Tile below = null;
  public Tile left = null;
  public Tile right = null;
  public GameObject currPiece = null;

  //constructor for class
  public Tile(int xPos, int yPos, tileColor color, GamePanel game) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = color;
    this.game = game;
  }

  public void draw(Graphics2D graphics) {
    //check if tile is selected
    if (isSelected) {
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
