package Game.AI;

import Game.GamePanel;
import Game.Pieces.*;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class AI {
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
  private Openers openers;
  boolean isAIWhite;
  GamePanel game;
  boolean thinking = false;
  boolean useOpenings;

  //constructor for class
  public AI(LinkedList<Tile> tiles, int difficulty, boolean isAIWhite, GamePanel game, boolean useOpenings) {
    this.tiles = tiles;
    this.difficulty = difficulty;
    this.isAIWhite = isAIWhite;
    this.game = game;
    this.useOpenings = useOpenings;
    if (isAIWhite) {
      this.color = GameObject.tileColor.WHITE;
    }
    else {
      this.color = GameObject.tileColor.BLACK;
    }
  }

  //there is no reason for this other than I think it is kinda cool
  private void thinking() {
    while (thinking) {
      System.out.println("Thinking!");
      try {
        Thread.sleep(1000);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void setUpOpeners() {
    this.openers = new Openers(new Random().nextInt(4) + 1, isAIWhite, tiles, useOpenings);
  }



  public PieceMove findNextMove() {
    //check if opening is being played
    if (openers.moreMoves()) {
      return openers.getNextMove();
    }


    //start a thread to print thinking
    thinking = true;
    new Thread(this::thinking).start();

    //use minimax algorithm to find next move
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
      System.exit(1);
    }

    thinking = false;

    return new PieceMove(outputObject, outputTile);
  }

  private PieceMove miniMax() {
    //AI player is trying to maximize and human player is trying to minimize
    //copy tiles
    LinkedList<Tile> tiles = copyTiles(this.tiles);
    //find current state of board
    Board board = new Board(tiles);
    board.printBoard();

    //start with current context of the board as root
    root = new TreeNode(new Node(null,board,board.getValue(isAIWhite)), null);

    int value = miniMaxHelper(root, true, tiles, difficulty, -10000, 10000);

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
      System.exit(1);
    }

    return output;
  }

  private int miniMaxHelper(TreeNode parent, boolean maximizing, LinkedList<Tile> tiles, int depth, int alpha, int beta) {
    //base case
    if (depth == 0) {
      return parent.data.value;
    }

    //recursive cases to find all possible moves
    if (maximizing) {
      int maxEval = -10000;
      //find all pieces of same color type
      PriorityQueue<GameObject> gameObjects = getGameObjects(tiles);
      GameObject currPiece;
      while (!gameObjects.isEmpty()) {
        currPiece = gameObjects.poll();
        if (currPiece != null && currPiece.getColor().equals(this.color)) {
          //find all possible valid moves
          LinkedList<Tile> validMoves = currPiece.getPossibleMoves();
          //add each piece move combination to tree
          for (Tile validTile : validMoves) {
            PieceMove pieceMove = new PieceMove(currPiece, validTile);
            LinkedList<Tile> newTiles = movePiece(tiles, pieceMove);
            //find board with new move
            Board newBoard = new Board(newTiles);
            parent.children.add(new TreeNode(new Node(pieceMove, newBoard, newBoard.getValue(isAIWhite)), parent));

            //recursively call function to generate branch from new child
            int eval = miniMaxHelper(parent.children.getLast(),false, newTiles,depth - 1, alpha, beta);
            maxEval = Math.max(eval, maxEval);
            alpha = Math.max(eval, alpha);
            if (beta <= alpha) {
              break;
            }
          }
        }
        if (beta <= alpha) {
          break;
        }
      }
      parent.data.value = maxEval;
      return maxEval;
    }
    else {
      int minEval = 10000;
      //find all pieces of other color type
      PriorityQueue<GameObject> gameObjects = getGameObjects(tiles);
      GameObject currPiece;
      while (!gameObjects.isEmpty()) {
        currPiece = gameObjects.poll();
        if (currPiece != null && !currPiece.getColor().equals(this.color)) {
          //find all possible valid moves
          LinkedList<Tile> validMoves = currPiece.getPossibleMoves();
          //add each piece move combination to tree
          for (Tile validTile : validMoves) {
            PieceMove pieceMove = new PieceMove(currPiece, validTile);
            LinkedList<Tile> newTiles = movePiece(tiles, pieceMove);
            Board newBoard = new Board(newTiles);
            parent.children.add(new TreeNode(new Node(pieceMove, newBoard, newBoard.getValue(isAIWhite)), parent));

            //recursively call function to generate branch from new child
            int eval = miniMaxHelper(parent.children.getLast(),true, newTiles,depth - 1, alpha, beta);
            minEval = Math.min(eval, minEval);
            beta = Math.min(eval, beta);
            if (beta <= alpha) {
              break;
            }
          }
        }
        if (beta <= alpha) {
          break;
        }
      }
      parent.data.value = minEval;
      return minEval;
    }
  }

  private LinkedList<Tile> copyTiles(LinkedList<Tile> tiles) {
    LinkedList<Tile> output = new LinkedList<>();

    //iterate through output and copy all tiles
    for (Tile tile : tiles) {
      output.add(tile.tileNumber, new Tile(tile));
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

    //iterate through output and find each king
    for (Tile tile : output) {
      if (tile.currPiece != null && tile.currPiece.toString().equals("king")) {
        ((King)tile.currPiece).setGameObjects(output);
      }
    }

    return output;
  }

  private PriorityQueue<GameObject> getGameObjects(LinkedList<Tile> tiles) {
    PriorityQueue<GameObject> output = new PriorityQueue<>();

    for (Tile tile : tiles) {
      if (tile.currPiece != null) {
        output.add(tile.currPiece);
      }
    }

    return output;
  }

  private LinkedList<Tile> movePiece(LinkedList<Tile> tiles, PieceMove pieceMove) {
    LinkedList<Tile> output;
    Tile oldTile;
    GameObject oldPiece;
    Rook rook = null;
    boolean oldMoved = true;
    boolean pawnPromote = false;
    boolean castleLeft = false;
    boolean castleRight = false;

    //move the piece
    oldTile = pieceMove.piece().getCurrTile();
    pieceMove.piece().setCurrTile(pieceMove.tile());
    oldPiece = pieceMove.tile().currPiece;
    pieceMove.tile().currPiece = pieceMove.piece();
    oldTile.currPiece = null;

    //make sure movement booleans in the moved piece are changed
    if (pieceMove.piece().toString().equals("pawn")) {
      oldMoved = ((Pawn)pieceMove.piece()).firstMove;
      ((Pawn)pieceMove.piece()).firstMove = false;

      //check if the tile being moved into is endTile
      if (pieceMove.tile().endTile) {
        pawnPromote = true;
        pieceMove.setPiece(new Queen(0, 0, pieceMove.piece().getColor(), this.game, pieceMove.piece().getCurrTile(), pieceMove.piece().getPieceNumber()));
      }
    }
    else if (pieceMove.piece().toString().equals("king")) {
      oldMoved = ((King)pieceMove.piece()).beenMoved;
      ((King)pieceMove.piece()).beenMoved = true;

      //check if castle performed
      if (!oldMoved && oldTile.left != null && oldTile.left.left != null && oldTile.left.left.tileNumber == pieceMove.tile().tileNumber) {
        castleLeft = true;
        //move rook
        rook = (Rook)oldTile.left.left.left.left.currPiece;
        rook.getCurrTile().currPiece = null;
        rook.setCurrTile(oldTile.left);
        oldTile.left.currPiece = rook;
      }
      if (!oldMoved && oldTile.right != null && oldTile.right.right != null && oldTile.right.right.tileNumber == pieceMove.tile().tileNumber) {
        castleRight = true;
        //move rook
        rook = (Rook)oldTile.right.right.right.currPiece;
        rook.getCurrTile().currPiece = null;
        rook.setCurrTile(oldTile.right);
        oldTile.right.currPiece = rook;
      }
    }
    else if (pieceMove.piece().toString().equals("rook")) {
      oldMoved = ((Rook)pieceMove.piece()).beenMoved;
      ((Rook)pieceMove.piece()).beenMoved = true;
    }

    //copy tiles
    output = copyTiles(tiles);

    //check if pawn promotion needs to be reversed
    if (pawnPromote) {
      pieceMove.setPiece(new Pawn(0, 0, pieceMove.piece().getColor(), this.game, pieceMove.piece().getCurrTile(), pieceMove.piece().getPieceNumber()));
    }

    //check if castle needs to be reversed
    if (castleLeft) {
      rook.getCurrTile().currPiece = null;
      rook.setCurrTile(oldTile.left.left.left.left);
      oldTile.left.left.left.left.currPiece = rook;
    }
    if (castleRight) {
      rook.getCurrTile().currPiece = null;
      rook.setCurrTile(oldTile.right.right.right);
      oldTile.right.right.right.currPiece = rook;
    }

    //revert changes to original
    pieceMove.piece().setCurrTile(oldTile);
    pieceMove.tile().currPiece = oldPiece;
    oldTile.currPiece = pieceMove.piece();

    //revert been moved
    if (pieceMove.piece().toString().equals("pawn")) {
      ((Pawn)pieceMove.piece()).firstMove = oldMoved;
    }
    else if (pieceMove.piece().toString().equals("king")) {
      ((King)pieceMove.piece()).beenMoved = oldMoved;
    }
    else if (pieceMove.piece().toString().equals("rook")) {
      ((Rook)pieceMove.piece()).beenMoved = oldMoved;
    }

    return output;
  }
}
