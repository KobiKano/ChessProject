package Game.AI;

import Game.Pieces.GameObject;
import Game.Pieces.Tile;

import java.util.LinkedList;

public class MiniMax implements AI {
  //class to store pieceMove and score combinations
  private record Node(PieceMove pieceMove, Board board, int value) {}
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

  //constructor for class
  public MiniMax(LinkedList<Tile> tiles, int difficulty, boolean isWhite) {
    this.tiles = tiles;
    this.difficulty = difficulty;
    if (isWhite) {
      this.color = GameObject.tileColor.WHITE;
    }
    else {
      this.color = GameObject.tileColor.BLACK;
    }
  }


  @Override
  public PieceMove findNextMove() {
    generateTree();
    miniMaxAlgo();
    return null;
  }

  @Override
  public void kill() {

  }

  private void miniMaxAlgo() {
    //AI player is trying to maximize and human player is trying to minimize

  }

  private void generateTree() {
    //copy tiles
    LinkedList<Tile> tiles = copyTiles(this.tiles);
    //find current state of board
    Board board = new Board(tiles);

    //start with current context of the board as root
    root = new TreeNode(new Node(null,board,0), null);

    //find children
    generateBranches(root, true, tiles, difficulty);
  }

  private void generateBranches(TreeNode parent, boolean maximizing, LinkedList<Tile> tiles, int depth) {
    //base case
    System.out.println("\n" + depth + "\n");
    if (depth == 0) {
      return;
    }


    //recursive cases to find all possible moves
    if (maximizing) {
      //find all pieces of same color type
      for (Tile tile : tiles) {
        if (tile.currPiece != null && tile.currPiece.getColor().equals(this.color)) {
          //find all possible valid moves
          LinkedList<Tile> validMoves = tile.currPiece.getPossibleMoves();
          //add each piece move combination to tree
          for (Tile validTile : validMoves) {
            PieceMove pieceMove = new PieceMove(tile.currPiece, validTile);
            LinkedList<Tile> newTiles = movePiece(tiles, pieceMove);
            Board newBoard = new Board(newTiles);
            parent.children.add(new TreeNode(new Node(pieceMove, newBoard, 0), parent));

            //recursively call function to generate branch from new child
            generateBranches(parent.children.getLast(),false, newTiles,depth - 1);
          }
        }
      }
    }
    else {
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
            parent.children.add(new TreeNode(new Node(pieceMove, newBoard, 0), parent));

            //recursively call function to generate branch from new child
            generateBranches(parent.children.getLast(),true, newTiles,depth - 1);
          }
        }
      }
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
