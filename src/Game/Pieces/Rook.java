package Game.Pieces;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Rook implements GameObject{
  public boolean beenMoved = false;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;
  Tile currTile;

  public Rook(int xPos, int yPos, GameObject.tileColor color, GamePanel game, Tile currTile) {
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
            Objects.requireNonNull(getClass().getResourceAsStream("../..//sprites/black_rook.png")));
      }
      else {
        sprite = ImageIO.read(
            Objects.requireNonNull(getClass().getResourceAsStream("../..//sprites/white_rook.png")));
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
    return 5;
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
    return "rook";
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
  public LinkedList<Tile> getPossibleMoves() {
    LinkedList<Tile> output = new LinkedList<>();

    //check moves downward
    Tile checkTile = currTile.below;
    while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
      output.add(checkTile);
      //check if check tile has a piece of opposite color
      if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
        //break loop
        break;
      }
      //go to next tile
      checkTile = checkTile.below;
    }

    //check moves upward
    checkTile = currTile.above;
    while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
      output.add(checkTile);
      //check if check tile has a piece of opposite color
      if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
        //break loop
        break;
      }
      checkTile = checkTile.above;
    }

    //check moves to left
    checkTile = currTile.left;
    while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
      output.add(checkTile);
      //check if check tile has a piece of opposite color
      if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
        //break loop
        break;
      }
      checkTile = checkTile.left;
    }

    //check moves to right
    checkTile = currTile.right;
    while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
      output.add(checkTile);
      //check if check tile has a piece of opposite color
      if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
        //break loop
        break;
      }
      checkTile = checkTile.right;
    }

    //add implementation for castle


    return output;
  }
}
