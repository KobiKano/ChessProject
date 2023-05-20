package Game.Pieces;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Knight implements GameObject{
  boolean beenMoved = false;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;
  Tile currTile;

  public Knight(int xPos, int yPos, GameObject.tileColor color, GamePanel game, Tile currTile) {
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
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/black_knight.png")));
      }
      else {
        sprite = ImageIO.read(
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/white_knight.png")));
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
    return 3;
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
    return "knight";
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

    //check move upward
    try {
      if (currTile.above.above.left.currPiece == null || !currTile.above.above.left.currPiece.getColor().equals(this.color)) {
        output.add(currTile.above.above.left);
      }
    }
    catch (NullPointerException ignored){}
    try {
      if (currTile.above.above.right.currPiece == null || !currTile.above.above.right.currPiece.getColor().equals(this.color)) {
        output.add(currTile.above.above.right);
      }
    }
    catch (NullPointerException ignored) {}

    //check move to the left
    try {
      if (currTile.left.left.above.currPiece == null || !currTile.left.left.above.currPiece.getColor().equals(this.color)) {
        output.add(currTile.left.left.above);
      }
    }
    catch (NullPointerException ignored) {}
    try {
      if (currTile.left.left.below.currPiece == null || !currTile.left.left.below.currPiece.getColor().equals(this.color)) {
        output.add(currTile.left.left.below);
      }
    }
    catch (NullPointerException ignored) {}

    //check move to right
    try {
      if (currTile.right.right.above.currPiece == null || !currTile.right.right.above.currPiece.getColor().equals(this.color)) {
        output.add(currTile.right.right.above);
      }
    }
    catch (NullPointerException ignored) {}
    try {
      if (currTile.right.right.below.currPiece == null || !currTile.right.right.below.currPiece.getColor().equals(this.color)) {
        output.add(currTile.right.right.below);
      }
    }
    catch (NullPointerException ignored) {}

    //check move down
    try {
      if (currTile.below.below.left.currPiece == null || !currTile.below.below.left.currPiece.getColor().equals(this.color)) {
        output.add(currTile.below.below.left);
      }
    }
    catch (NullPointerException ignored){}
    try {
      if (currTile.below.below.right.currPiece == null || !currTile.below.below.right.currPiece.getColor().equals(this.color)) {
        output.add(currTile.below.below.right);
      }
    }
    catch (NullPointerException ignored){}

    //return moves
    return output;
  }
}
