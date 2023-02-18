package Game.Pieces;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Pawn implements GameObject{
  boolean beenMoved = false;
  boolean firstMove = true;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;
  Tile currTile;

  public Pawn(int xPos, int yPos, GameObject.tileColor color, GamePanel game, Tile currTile) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = color;
    this.game = game;
    this.currTile = currTile;
    currTile.currPiece = this;

    //assign sprite image
    try {
      if (color == tileColor.BLACK) {
        sprite = ImageIO.read(
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/black_pawn.png")));
      }
      else {
        sprite = ImageIO.read(
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/white_pawn.png")));
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
    return 1;
  }

  @Override
  public tileColor getColor() {
    return color;
  }

  @Override
  public int getSize() {
    return game.ACTUAL_SIZE;
  }

  @Override
  public int getXPos() {
    return xPos;
  }

  @Override
  public int getYPos() {
    return yPos;
  }

  @Override
  public void setYPos(int yPos) {
    this.yPos = yPos;
  }

  @Override
  public void setXPos(int xPos) {
    this.xPos = xPos;
  }

  @Override
  public void draw(Graphics2D graphics) {
    //draw image
    graphics.drawImage(sprite, xPos, yPos, game.ACTUAL_SIZE, game.ACTUAL_SIZE, null);
  }

  @Override
  public String toString() {
    return "Game.Pieces.Pawn";
  }

  @Override
  public Tile getCurrTile() {
    return currTile;
  }

  @Override
  public void setCurrTile(Tile currTile) {
    currTile.currPiece = this;
    this.currTile = currTile;
  }

  @Override
  public ArrayList<Tile> getPossibleMoves() {
    ArrayList<Tile> output = new ArrayList<>();
    //check if first move and color
    if (firstMove && color == tileColor.WHITE) {
      output.add(currTile.above);
      output.add(currTile.above.above);
      firstMove = false;
    }
    else if (firstMove && color == tileColor.BLACK) {
      output.add(currTile.below);
      output.add(currTile.below.below);
      firstMove = false;
    }

    //if not first move
    else if (!firstMove && color == tileColor.WHITE) {
      output.add(currTile.above);
    }
    else if (!firstMove && color == tileColor.BLACK) {
      output.add(currTile.below);
    }

    //return possible moves
    return output;
  }
}
