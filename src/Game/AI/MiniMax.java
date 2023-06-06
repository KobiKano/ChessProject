package Game.AI;

import Game.Pieces.GameObject;
import Game.Pieces.Tile;

import java.util.LinkedList;

public class MiniMax implements AI {
  //class to store pieceMove and score combinations
  private class Node {
    PieceMove pieceMove;
    Board board;
    int value;
    private Node (PieceMove pieceMove, Board board, int value) {
      this.pieceMove = pieceMove;
      this.board = board;
      this.value = value;
    }
  }
  //private tree class
  private class TreeNode {
    Node data;
    TreeNode parent;
    LinkedList<TreeNode> children;

    private TreeNode(Node data, TreeNode parent) {
      this.data = data;
      this.parent = parent;
      children = new LinkedList<>();
    }
  }

  //private fields
  private LinkedList<Tile> tiles;
  private int difficulty;
  private TreeNode root;
  private GameObject.tileColor color;
  boolean isWhite;

  //constructor for class
  public MiniMax(LinkedList<Tile> tiles, int difficulty, boolean isWhite) {
    this.tiles = tiles;
    this.difficulty = difficulty;
    this.isWhite = isWhite;
    if (isWhite) {
      this.color = GameObject.tileColor.WHITE;
    }
    else {
      this.color = GameObject.tileColor.BLACK;
    }
  }


  @Override
  public PieceMove findNextMove() {
    PieceMove pieceMove = miniMax();

    //find pieceMove in original board that matches calculated pieceMove
    Tile outputTile = null;
    GameObject outputObject = null;

    for (int i = 0; i < this.tiles.size(); i++) {
      //check if tile is same
      if (tiles.get(i).tileNumber == pieceMove.tile().tileNumber) {
        outputTile = tiles.get(i);
      }
      //check if gameObject the same
      if (tiles.get(i).currPiece != null && tiles.get(i).currPiece.getPieceNumber() == pieceMove.piece().getPieceNumber()) {
        outputObject = tiles.get(i).currPiece;
      }
      //check if loop can be exited
      if (outputObject != null && outputTile != null) {
        break;
      }
    }

    //make sure pair is found
    if (outputObject == null || outputTile == null) {
      System.out.println("Error finding pieceMove pair!");
    }

    return new PieceMove(outputObject, outputTile);
  }

  @Override
  public void kill() {

  }

  private PieceMove miniMax() {
    //AI player is trying to maximize and human player is trying to minimize
    //copy tiles
    LinkedList<Tile> tiles = copyTiles(this.tiles);
    //find current state of board
    Board board = new Board(tiles);
    board.printBoard();

    //start with current context of the board as root
    root = new TreeNode(new Node(null,board,board.getValue(isWhite)), null);

    int value = miniMaxHelper(root, true, tiles, difficulty);

    //locals
    PieceMove output = null;

    //find pieceMove that matches value in root
    for (TreeNode node : root.children) {
      if (node.data.value == value) {
        output = node.data.pieceMove;
        break;
      }
    }

    //check if move found
    if (output == null) {
      System.out.println("Error finding move!");
    }

    return output;
  }

  private int miniMaxHelper(TreeNode parent, boolean maximizing, LinkedList<Tile> tiles, int depth) {
    //base case
    if (depth == 0) {
      return parent.data.value;
    }

    //recursive cases to find all possible moves
    if (maximizing) {
      int maxEval = -10000;
      //find all pieces of same color type
      for (Tile tile : tiles) {
        if (tile.currPiece != null && tile.currPiece.getColor().equals(this.color)) {
          //find all possible valid moves
          LinkedList<Tile> validMoves = tile.currPiece.getPossibleMoves();
          //add each piece move combination to tree
          for (Tile validTile : validMoves) {
            PieceMove pieceMove = new PieceMove(tile.currPiece, validTile);
            LinkedList<Tile> newTiles = movePiece(tiles, pieceMove);
            //find board with new move
            Board newBoard = new Board(newTiles);
            parent.children.add(new TreeNode(new Node(pieceMove, newBoard, newBoard.getValue(isWhite)), parent));

            //recursively call function to generate branch from new child
            int eval = miniMaxHelper(parent.children.getLast(),false, newTiles,depth - 1);
            maxEval = Math.max(eval, maxEval);
          }
        }
      }
      parent.data.value = maxEval;
      return maxEval;
    }
    else {
      int minEval = 10000;
      //find all pieces of other color type
      for (Tile tile : tiles) {
        if (tile.currPiece != null && !tile.currPiece.getColor().equals(this.color)) {
          //find all possible valid moves
          LinkedList<Tile> validMoves = tile.currPiece.getPossibleMoves();
          //add each piece move combination to tree
          for (Tile validTile : validMoves) {
            PieceMove pieceMove = new PieceMove(tile.currPiece, validTile);
            LinkedList<Tile> newTiles = movePiece(tiles, pieceMove);
            Board newBoard = new Board(newTiles);
            parent.children.add(new TreeNode(new Node(pieceMove, newBoard, newBoard.getValue(isWhite)), parent));

            //recursively call function to generate branch from new child
            int eval = miniMaxHelper(parent.children.getLast(),true, newTiles,depth - 1);
            minEval = Math.min(eval, minEval);
          }
        }
      }
      parent.data.value = minEval;
      return minEval;
    }
  }

  private LinkedList<Tile> copyTiles(LinkedList<Tile> tiles) {
    LinkedList<Tile> output = new LinkedList<>();

    //iterate through output and copy all tiles
    for (int i = 0; i < tiles.size(); i++) {
      output.add(i, new Tile(tiles.get(i), output));
    }

    //link all tiles
    int column = 0;
    int row = 0;
    for (int i = 0; i < output.size(); i++) {
      //add top
      if (row != 0) {
        output.get(i).above = output.get(i - 1);
      }
      //add bottom
      if (row != 7) {
        output.get(i).below = output.get(i + 1);
      }
      //add left
      if (column != 0) {
        output.get(i).left = output.get(i - 8);
      }
      //add right
      if (column != 7) {
        output.get(i).right = output.get(i + 8);
      }

      //increment row and column
      row++;
      if (row == 8) {
        row = 0;
        column++;
      }
    }

    return output;
  }

  private LinkedList<Tile> movePiece(LinkedList<Tile> tiles, PieceMove pieceMove) {
    LinkedList<Tile> output;
    Tile oldTile;
    GameObject oldPiece;

    //move the piece
    oldTile = pieceMove.piece().getCurrTile();
    pieceMove.piece().setCurrTile(pieceMove.tile());
    oldPiece = pieceMove.tile().currPiece;
    pieceMove.tile().currPiece = pieceMove.piece();
    oldTile.currPiece = null;

    //copy tiles
    output = copyTiles(tiles);

    //revert changes to original
    pieceMove.piece().setCurrTile(oldTile);
    pieceMove.tile().currPiece = oldPiece;
    oldTile.currPiece = pieceMove.piece();

    return output;
  }
}
