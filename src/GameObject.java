import java.awt.*;

public interface GameObject {
  //specify color
  enum tileColor {
    BLACK, WHITE
  }
  public boolean hasBeenMoved();
  public int getXPos();
  public int getYPos();
  public void setYPos();
  public void setXPos();
  public void update();
  public void draw(Graphics2D graphics);
}
