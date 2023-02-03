import java.awt.*;

public interface BoardTile {
  //specify color
  enum tileColor {
    BLACK, WHITE
  }
  public void draw(Graphics2D graphics);
}
