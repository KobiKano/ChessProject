package Game.Pieces;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class King implements GameObject, Comparable{
  public boolean beenMoved = false;
  public boolean inCheck = false;
  public boolean leftCastle = false;
  public boolean rightCastle = false;
  int xPos;
  int yPos;
  GameObject.tileColor color;
  BufferedImage sprite;
  GamePanel game;
  Tile currTile;
  LinkedList<GameObject> gameObjects;
  int pieceNumber;

  public King(int xPos, int yPos, GameObject.tileColor color, GamePanel game, Tile currTile, int pieceNumber) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = color;
    this.game = game;
    this.currTile = currTile;
    currTile.currPiece = this;
    gameObjects = game.gameObjects;
    this.pieceNumber = pieceNumber;

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

  //copy constructor
  public King(King king, Tile currTile) {
    this.inCheck = king.inCheck;
    this.rightCastle = king.rightCastle;
    this.leftCastle = king.leftCastle;
    this.beenMoved = king.beenMoved;
    this.currTile = currTile;
    this.color = king.color;
    this.pieceNumber = king.pieceNumber;
  }

  public void setGameObjects(LinkedList<Tile> tiles) {
    //find all gameObjects in tiles
    gameObjects = new LinkedList<>();
    for (Tile tile : tiles) {
      if (tile.currPiece != null) {
        gameObjects.add(tile.currPiece);
      }
    }
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
    this.currTile = currTile;
  }

  @Override
  public LinkedList<Tile> getPossibleMoves() {
    LinkedList<Tile> output = new LinkedList<>();

    //add moves and check if tile is null
    if (currTile.left != null && (currTile.left.currPiece == null || !currTile.left.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.left)) {
      output.add(currTile.left);
    }
    if (currTile.above != null && (currTile.above.currPiece == null || !currTile.above.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.above)) {
      output.add(currTile.above);
    }
    if (currTile.below != null && (currTile.below.currPiece == null || !currTile.below.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.below)) {
      output.add(currTile.below);
    }
    if (currTile.right != null && (currTile.right.currPiece == null || !currTile.right.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.right)) {
      output.add(currTile.right);
    }
    if (currTile.above != null && currTile.above.left != null && (currTile.above.left.currPiece == null || !currTile.above.left.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.above.left)) {
      output.add(currTile.above.left);
    }
    if (currTile.above != null && currTile.above.right != null && (currTile.above.right.currPiece == null || !currTile.above.right.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.above.right)) {
      output.add(currTile.above.right);
    }
    if (currTile.below != null && currTile.below.left != null && (currTile.below.left.currPiece == null || !currTile.below.left.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.below.left)) {
      output.add(currTile.below.left);
    }
    if (currTile.below != null && currTile.below.right != null && (currTile.below.right.currPiece == null || !currTile.below.right.currPiece.getColor().equals(this.color)) && !checkIfCheck(currTile.below.right)) {
      output.add(currTile.below.right);
    }

    //castling logic for left castle
    if (checkCastleLeft()) {
      output.add(currTile.left.left);
      leftCastle = true;
    }
    //castling logic for right castle
    if (checkCastleRight()) {
      output.add(currTile.right.right);
      rightCastle = true;
    }

    return output;
  }

  private boolean checkCastleLeft() {
    return !beenMoved && !inCheck && currTile.left.currPiece == null && currTile.left.left.currPiece == null && currTile.left.left.left.currPiece == null && currTile.left.left.left.left.currPiece != null
          && currTile.left.left.left.left.currPiece.toString().equals("rook") && currTile.left.left.left.left.currPiece.getColor().equals(this.color)
               && !((Rook)currTile.left.left.left.left.currPiece).beenMoved && checkPath(true);
}

  private boolean checkCastleRight() {
    return !beenMoved && !inCheck && currTile.right.currPiece == null && currTile.right.right.currPiece == null && currTile.right.right.right.currPiece != null
          && currTile.right.right.right.currPiece.toString().equals("rook") && currTile.right.right.right.currPiece.getColor().equals(this.color)
               && !((Rook)currTile.right.right.right.currPiece).beenMoved && checkPath(false);
}

  private boolean checkPath(boolean left) {
    //check if left path
    if (left && !checkIfCheck(currTile) && !checkIfCheck(currTile.left) && !checkIfCheck(currTile.left.left)) {
      //valid path
      return true;
    }
    //if right path
    else return !checkIfCheck(currTile) && !checkIfCheck(currTile.right) && !checkIfCheck(currTile.right.right) && !checkIfCheck(currTile.right.right.right);
}

  public boolean checkIfCheck(Tile thisTile) {
    //System.out.println("checkIfCheck");
    LinkedList<Tile> tiles;
    LinkedList<Thread> threads = new LinkedList<>();
    AtomicBoolean check = new AtomicBoolean(false);

    for (int i = 0; i < gameObjects.size(); i++) {
      //check if the king is in check
      if(check.get()) {
        //stop all threads and return
        for (int j = 0; j < threads.size(); j++) {
          //System.out.println("Stopping threads");
          threads.get(j).stop();
        }

        return true;
      }

      //check if piece is of opposite color
      if (!gameObjects.get(i).getColor().equals(this.color) && !gameObjects.get(i).toString().equals("king")) {
        if (!gameObjects.get(i).toString().equals("pawn")) {
          tiles = gameObjects.get(i).getPossibleMoves();
          LinkedList<Tile> finalTiles = tiles;
          //start new thread
          Thread thread = new Thread(()->{
            //check if thisTile is one of the possible moves
            for (Tile tile : finalTiles) {
              if (tile.equals(thisTile)) {
                //king is in check return true
                check.set(true);
              }
            }});
          thread.start();
          threads.add(thread);
        }

        //special logic for pawn
        else {
          //make sure tile check is not for tiles above or below pawn
          tiles = ((Pawn)gameObjects.get(i)).getCheckMoves();
          //start new thread
          LinkedList<Tile> finalTiles = tiles;
          Thread thread = new Thread(()->{
            //check if thisTile is one of the possible moves
            for (Tile tile : finalTiles) {
              if (tile.equals(thisTile)) {
                //king is in check return true
                check.set(true);
              }
            }});
          thread.start();
          threads.add(thread);
        }
      }
    }

    //wait for all threads to finish
    for(Thread thread: threads){
      //System.out.println("joining threads");
      try {
        thread.join();}
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    //return
    return check.get();
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
