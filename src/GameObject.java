import java.awt.*;
import java.util.ArrayList;

public interface GameObject {
  //specify color
  enum tileColor {
    BLACK, WHITE
  }
  public boolean hasBeenMoved();
  public int getCost();
  public tileColor getColor();
  public int getXPos();
  public int getYPos();
  public int getSize();
  public void setYPos(int yPos);
  public void setXPos(int xPos);
  public void draw(Graphics2D graphics);
  public Tile getCurrTile();
  public void setCurrTile(Tile currTile);
  public ArrayList<Tile> getPossibleMoves();
}
