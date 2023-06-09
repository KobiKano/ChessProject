package Game.Pieces;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Knight implements GameObject, Comparable{
  boolean beenMoved = false;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;
  Tile currTile;
  int pieceNumber;

  public Knight(int xPos, int yPos, GameObject.tileColor color, GamePanel game, Tile currTile, int pieceNumber) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = color;
    this.game = game;
    this.currTile = currTile;
    currTile.currPiece = this;
    this.pieceNumber = pieceNumber;

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

  //copy constructor
  public Knight(Knight knight, Tile currTile) {
    this.beenMoved = knight.beenMoved;
    this.currTile = currTile;
    this.color = knight.color;
    this.pieceNumber = knight.pieceNumber;
  }

  @Override
  public int getPieceNumber() {
    return pieceNumber;
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
    this.currTile = currTile;
  }

  @Override
  public LinkedList<Tile> getPossibleMoves() {
    LinkedList<Tile> output = new LinkedList<>();

    //check move upward
    if (currTile.above != null && currTile.above.above != null && currTile.above.above.left != null
            && (currTile.above.above.left.currPiece == null || !currTile.above.above.left.currPiece.getColor().equals(this.color))) {
      output.add(currTile.above.above.left);
    }
    if (currTile.above != null && currTile.above.above != null && currTile.above.above.right != null
            && (currTile.above.above.right.currPiece == null || !currTile.above.above.right.currPiece.getColor().equals(this.color))) {
      output.add(currTile.above.above.right);
    }

    //check move down
    if (currTile.below != null && currTile.below.below != null && currTile.below.below.left != null
            && (currTile.below.below.left.currPiece == null || !currTile.below.below.left.currPiece.getColor().equals(this.color))) {
      output.add(currTile.below.below.left);
    }
    if (currTile.below != null && currTile.below.below != null && currTile.below.below.right != null
            && (currTile.below.below.right.currPiece == null || !currTile.below.below.right.currPiece.getColor().equals(this.color))) {
      output.add(currTile.below.below.right);
    }

    //check move left
    if (currTile.left != null && currTile.left.left != null && currTile.left.left.above != null
            && (currTile.left.left.above.currPiece == null || !currTile.left.left.above.currPiece.getColor().equals(this.color))) {
      output.add(currTile.left.left.above);
    }
    if (currTile.left != null && currTile.left.left != null && currTile.left.left.below != null
            && (currTile.left.left.below.currPiece == null || !currTile.left.left.below.currPiece.getColor().equals(this.color))) {
      output.add(currTile.left.left.below);
    }

    //check move right
    if (currTile.right != null && currTile.right.right != null && currTile.right.right.above != null
            && (currTile.right.right.above.currPiece == null || !currTile.right.right.above.currPiece.getColor().equals(this.color))) {
      output.add(currTile.right.right.above);
    }
    if (currTile.right != null && currTile.right.right != null && currTile.right.right.below != null
            && (currTile.right.right.below.currPiece == null || !currTile.right.right.below.currPiece.getColor().equals(this.color))) {
      output.add(currTile.right.right.below);
    }

    //return moves
    return output;
  }

  @Override
  public int compareTo(Object o) {
    //object should only be compared to other GameObjects

    //use piece score to determine comparison
    if (((GameObject) o).getCost() < this.getCost()) {
      return 1;
    }
    else if (((GameObject) o).getCost() > this.getCost()) {
      return -1;
    }

    //default return
    return 0;
  }
}
