package Game.Pieces;

import java.awt.*;
import java.util.LinkedList;

public interface GameObject {
  //specify color
  enum tileColor {
    BLACK, WHITE
  }
  boolean hasBeenMoved();
  int getCost();
  tileColor getColor();
  int getXPos();
  int getYPos();
  int getSize();
  int getPieceNumber();
  void setYPos(int yPos);
  void setXPos(int xPos);
  void draw(Graphics2D graphics);
  Tile getCurrTile();
  void setCurrTile(Tile currTile);
  LinkedList<Tile> getPossibleMoves();
}
