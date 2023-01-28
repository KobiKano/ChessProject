import java.awt.*;

public class Tile implements GameObject{
  //fields for this class
  int xPos;
  int yPos;
  tileColor color;
  boolean beenMoved = false;
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
  public boolean hasBeenMoved() {
  return beenMoved;
  }
  @Override
  public int getXPos() {
  return 0;
  }
  @Override
  public int getYPos() {
  return 0;
  }
  @Override
  public void setYPos() {

  }
  @Override
  public void setXPos() {

  }
  @Override
  public void update() {

  }
  @Override
  public void draw(Graphics2D graphics) {
    //set object color
    if (color == tileColor.BLACK) {
      graphics.setColor(Color.BLACK);
    }
    else {
      graphics.setColor(Color.WHITE);
    }

    //draw image
    graphics.fillRect(xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE);
  }
}
