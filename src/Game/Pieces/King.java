package Game.Pieces;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class King implements GameObject{
  boolean beenMoved = false;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;
  Tile currTile;

  public King(int xPos, int yPos, GameObject.tileColor color, GamePanel game, Tile currTile) {
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
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/black_king.png")));
      }
      else {
        sprite = ImageIO.read(
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/white_king.png")));
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
    return 1000;
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
    return "king";
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

    //add moves and check if tile is null
    if (currTile.left != null && (currTile.left.currPiece == null || !currTile.left.currPiece.getColor().equals(this.color))) {
      output.add(currTile.left);
    }
    if (currTile.above != null && (currTile.above.currPiece == null || !currTile.above.currPiece.getColor().equals(this.color))) {
      output.add(currTile.above);
    }
    if (currTile.below != null && (currTile.below.currPiece == null || !currTile.below.currPiece.getColor().equals(this.color))) {
      output.add(currTile.below);
    }
    if (currTile.right != null && (currTile.right.currPiece == null || !currTile.right.currPiece.getColor().equals(this.color))) {
      output.add(currTile.right);
    }
    if (currTile.above != null && currTile.above.left != null && (currTile.above.left.currPiece == null || !currTile.above.left.currPiece.getColor().equals(this.color))) {
      output.add(currTile.above.left);
    }
    if (currTile.above != null && currTile.above.right != null && (currTile.above.right.currPiece == null || !currTile.above.right.currPiece.getColor().equals(this.color))) {
      output.add(currTile.above.right);
    }
    if (currTile.below != null && currTile.below.left != null && (currTile.below.left.currPiece == null || !currTile.below.left.currPiece.getColor().equals(this.color))) {
      output.add(currTile.below.left);
    }
    if (currTile.below != null && currTile.below.right != null && (currTile.below.right.currPiece == null || !currTile.below.right.currPiece.getColor().equals(this.color))) {
      output.add(currTile.below.right);
    }

    return output;
  }
}
