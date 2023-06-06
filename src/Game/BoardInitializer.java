package Game;

import Game.Pieces.*;

import java.util.LinkedList;

public class BoardInitializer {
  //fields for class
  LinkedList<GameObject> gameObjects;
  LinkedList<Tile> tiles;
  GamePanel game;

  int actualSize;
  int width;
  int height;

  //constructor for class
  public BoardInitializer(LinkedList<GameObject> gameObjects, LinkedList<Tile> tiles, GamePanel game) {
    this.gameObjects = gameObjects;
    this.tiles = tiles;
    this.game = game;

    actualSize = game.ACTUAL_SIZE;
    width = game.WIDTH;
    height = game.HEIGHT;
  }

  public void generateBoard() {
    int tileNumber = 0;
    boolean isBlack = true;
    for (int x = actualSize/2; x < width - actualSize; x += actualSize) {
      isBlack = !isBlack;
      for (int y = 0; y < height - actualSize; y += actualSize) {
        //check if on edge
        if (y == 0 || y == 7 * actualSize) {
          if (isBlack) {
            tiles.add(new Tile(x, y, Tile.tileColor.BLACK, game, true, tileNumber));
          } else {
            tiles.add(new Tile(x, y, Tile.tileColor.WHITE, game, true, tileNumber));
          }
        }
        else {
          if (isBlack) {
            tiles.add(new Tile(x, y, Tile.tileColor.BLACK, game, false, tileNumber));
          } else {
            tiles.add(new Tile(x, y, Tile.tileColor.WHITE, game, false, tileNumber));
          }
        }
        tileNumber++;
        isBlack = !isBlack;
      }
    }

    //link all tiles
    int column = 0;
    int row = 0;
    for (int i = 0; i < tiles.size(); i++) {
      //add top
      if (row != 0) {
        tiles.get(i).above = tiles.get(i - 1);
      }
      //add bottom
      if (row != 7) {
        tiles.get(i).below = tiles.get(i + 1);
      }
      //add left
      if (column != 0) {
        tiles.get(i).left = tiles.get(i - 8);
      }
      //add right
      if (column != 7) {
        tiles.get(i).right = tiles.get(i + 8);
      }

      //increment row and column
      row++;
      if (row == 8) {
        row = 0;
        column++;
      }
    }

    //pieceNumber index to label piece
    int pieceNumber = 0;

    //draw black pieces
    //draw king
    King blackKing = new King(actualSize/2 + 4 * actualSize, 0 , GameObject.tileColor.BLACK, game, tiles.get(32), pieceNumber++);
    gameObjects.add(blackKing);
    game.blackKing = blackKing;
    //draw pawns
    int pawnTileIndex = 1;
    for (int i = actualSize/2; i < width - actualSize; i += actualSize) {
      gameObjects.add(new Pawn(i, actualSize, GameObject.tileColor.BLACK, game, tiles.get(pawnTileIndex), pieceNumber++));
      pawnTileIndex += 8;
    }
    //draw rooks
    gameObjects.add(new Rook(actualSize/2, 0, GameObject.tileColor.BLACK, game, tiles.get(0), pieceNumber++));
    gameObjects.add(new Rook( width -  2 * actualSize + actualSize/2, 0, GameObject.tileColor.BLACK, game, tiles.get(56), pieceNumber++));
    //draw knights
    gameObjects.add(new Knight(actualSize/2 + actualSize, 0 , GameObject.tileColor.BLACK, game, tiles.get(8), pieceNumber++));
    gameObjects.add(new Knight(width - 3 * actualSize + actualSize/2, 0 , GameObject.tileColor.BLACK, game, tiles.get(48), pieceNumber++));
    //draw bishops
    gameObjects.add(new Bishop(actualSize/2 + 2 * actualSize, 0 , GameObject.tileColor.BLACK, game, tiles.get(16), pieceNumber++));
    gameObjects.add(new Bishop(width - 4 * actualSize + actualSize/2, 0 , GameObject.tileColor.BLACK, game, tiles.get(40), pieceNumber++));
    //draw queen
    gameObjects.add(new Queen(actualSize/2 + 3 * actualSize, 0 , GameObject.tileColor.BLACK, game, tiles.get(24), pieceNumber++));


    //draw white pieces
    //draw king
    King whiteKing = new King(actualSize/2 + 4 * actualSize, height - 2 * actualSize, GameObject.tileColor.WHITE, game, tiles.get(63 - 3*8), pieceNumber++);
    gameObjects.add(whiteKing);
    game.whiteKing = whiteKing;
    //draw pawns
    pawnTileIndex = 6;
    for (int i = actualSize/2; i < width - actualSize; i += actualSize) {
      gameObjects.add(new Pawn(i, height - 3 * actualSize, GameObject.tileColor.WHITE, game, tiles.get(pawnTileIndex), pieceNumber++));
      pawnTileIndex += 8;
    }
    //draw rooks
    gameObjects.add(new Rook(actualSize/2, height - 2 * actualSize, GameObject.tileColor.WHITE, game, tiles.get(7), pieceNumber++));
    gameObjects.add(new Rook( width -  2 * actualSize + actualSize/2, height - 2 * actualSize, GameObject.tileColor.WHITE, game, tiles.get(63), pieceNumber++));
    //draw knights
    gameObjects.add(new Knight(actualSize/2 + actualSize, height - 2 * actualSize , GameObject.tileColor.WHITE, game, tiles.get(7 + 8), pieceNumber++));
    gameObjects.add(new Knight(width - 3 * actualSize + actualSize/2, height - 2 * actualSize, GameObject.tileColor.WHITE, game, tiles.get(63 - 8), pieceNumber++));
    //draw bishops
    gameObjects.add(new Bishop(actualSize/2 + 2 * actualSize, height - 2 * actualSize , GameObject.tileColor.WHITE, game, tiles.get(7 + 2*8), pieceNumber++));
    gameObjects.add(new Bishop(width - 4 * actualSize + actualSize/2, height - 2 * actualSize, GameObject.tileColor.WHITE, game, tiles.get(63 - 2*8), pieceNumber++));
    //draw queen
    gameObjects.add(new Queen(actualSize/2 + 3 * actualSize, height - 2 * actualSize, GameObject.tileColor.WHITE, game, tiles.get(7 + 3*8), pieceNumber));
  }
}
