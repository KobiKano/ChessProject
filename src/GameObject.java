import java.awt.*;

public interface GameObject {
  //specify color
  enum tileColor {
    BLACK, WHITE
  }
  public boolean hasBeenMoved();
  public int getCost();
  public int getXPos();
  public int getYPos();
  public void setYPos(int yPos);
  public void setXPos(int xPos);
  public void draw(Graphics2D graphics);
}
