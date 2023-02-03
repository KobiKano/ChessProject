import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Rook implements GameObject{
  boolean beenMoved = false;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;

  public Rook(int xPos, int yPos, GameObject.tileColor color, GamePanel game) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = color;
    this.game = game;

    //assign sprite image
    try {
      if (color == tileColor.BLACK) {
        sprite = ImageIO.read(getClass().getResourceAsStream("./sprites/black_rook.png"));
      }
      else {
        sprite = ImageIO.read(getClass().getResourceAsStream("./sprites/white_rook.png"));
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean hasBeenMoved() {
    return beenMoved;
  }

  @Override
  public int getCost() {
    return 0;
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
  public void setYPos(int yPos) {

  }

  @Override
  public void setXPos(int xPos) {

  }

  @Override
  public void draw(Graphics2D graphics) {
    //draw image
    graphics.drawImage(sprite, xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, null);
  }
}
