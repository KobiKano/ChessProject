package Game.Pieces;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Queen implements GameObject, Comparable{
  boolean beenMoved = false;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;
  Tile currTile;
  int pieceNumber;

  public Queen(int xPos, int yPos, GameObject.tileColor color, GamePanel game, Tile currTile, int pieceNumber) {
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
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/black_queen.png")));
      }
      else {
        sprite = ImageIO.read(
            Objects.requireNonNull(getClass().getResourceAsStream("../../sprites/white_queen.png")));
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  //copy constructor
  public Queen(Queen queen, Tile currTile) {
    this.beenMoved = queen.beenMoved;
    this.currTile = currTile;
    this.color = queen.color;
    this.pieceNumber = queen.pieceNumber;
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
    return 9;
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
    return "queen";
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
    Tile checkTile;

    //check moves downward
    checkTile = currTile.below;
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

    //check moves downward to left
    //make sure currTile.below is not null
    if (currTile.below != null) {
      checkTile = currTile.below.left;
      while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
        output.add(checkTile);
        //check if check tile has a piece of opposite color
        if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
          //break loop
          break;
        }
        //check if checkTile.below is null
        if (checkTile.below == null) {
          //break loop
          break;
        }
        //go to next tile
        checkTile = checkTile.below.left;
      }
    }


    //check moves downward to right
    //make sure currTile.below is not null
    if (currTile.below != null) {
      checkTile = currTile.below.right;
      while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
        output.add(checkTile);
        //check if check tile has a piece of opposite color
        if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
          //break loop
          break;
        }
        //check if checkTile.below is null
        if (checkTile.below == null) {
          //break loop
          break;
        }
        //go to next tile
        checkTile = checkTile.below.right;
      }
    }

    //check moves upward to left
    //make sure currTile.above is not null
    if (currTile.above != null) {
      checkTile = currTile.above.left;
      while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
        output.add(checkTile);
        //check if check tile has a piece of opposite color
        if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
          //break loop
          break;
        }
        //check if checkTile.below is null
        if (checkTile.above == null) {
          //break loop
          break;
        }
        //go to next tile
        checkTile = checkTile.above.left;
      }
    }

    //check moves upward to right
    //make sure currTile.above is not null
    if (currTile.above != null) {
      checkTile = currTile.above.right;
      while(checkTile != null && (checkTile.currPiece == null || !checkTile.currPiece.getColor().equals(this.color))) {
        output.add(checkTile);
        //check if check tile has a piece of opposite color
        if (checkTile.currPiece != null && !checkTile.currPiece.getColor().equals(this.color)) {
          //break loop
          break;
        }
        //check if checkTile.below is null
        if (checkTile.above == null) {
          //break loop
          break;
        }
        //go to next tile
        checkTile = checkTile.above.right;
      }
    }

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
