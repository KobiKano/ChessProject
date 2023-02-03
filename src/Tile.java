import java.awt.*;

public class Tile implements BoardTile {
  //fields for this class
  int xPos;
  int yPos;
  tileColor color;
  GamePanel game;
  InputChecker inputChecker;

  //constructor for class
  public Tile(int xPos, int yPos, tileColor color, GamePanel game, InputChecker inputChecker) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = color;
    this.game = game;
    this.inputChecker = inputChecker;
  }
  @Override
  public void draw(Graphics2D graphics) {
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
