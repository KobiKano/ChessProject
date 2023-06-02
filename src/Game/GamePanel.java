package Game;

import Game.Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class GamePanel extends JPanel implements Runnable {
  JFrame window;
  //define screen size
  public final int TILE_SIZE = 16;  //16 x 16 tiles
  public final int SCALE = 5;
  public final int ACTUAL_SIZE = TILE_SIZE * SCALE; //scale up for monitor size

  public final int COLUMNS = 9; //define size of board
  public final int ROWS = 9;

  public final int WIDTH = COLUMNS * ACTUAL_SIZE;  //translate to window dimensions
  public final int HEIGHT = ROWS * ACTUAL_SIZE;

  //create game variables
  LinkedList<Tile> tiles = new LinkedList<>();
  public LinkedList<GameObject> gameObjects = new LinkedList<>();
  BoardInitializer initializer = new BoardInitializer(gameObjects, tiles, this);
  InputChecker inputChecker = new InputChecker();
  Updater updater = new Updater(gameObjects, this);
  Thread gameThread;
  final int FPS = 60;

  int blackScore = 39;
  int whiteScore = 39;
  boolean gameOver = false;
  boolean blackTurn = false;
  ChessGame.PlayerColor playerColor;
  int difficulty;

  public King whiteKing = null;
  public King blackKing = null;

  boolean whiteWin = false;
  boolean blackWin = false;

  //constructor for class
  public GamePanel(JFrame window, ChessGame.Definitions definitions) {
    this.window = window;
    this.playerColor = definitions.color;
    this.difficulty = definitions.difficulty;

    //set game panel settings
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.white);
    this.setDoubleBuffered(true);
    this.addKeyListener(inputChecker);
    this.addMouseListener(updater);
    this.setFocusable(true);

    //initialize board and pieces
    initializer.generateBoard();

    //make sure king was found
    if (whiteKing == null) {
      System.out.println("Error finding White King! Exiting!");
      System.exit(1);
    }
    if (blackKing == null) {
      System.out.println("Error finding Black King! Exiting!");
      System.exit(1);
    }

    //start game
    startGameThread();
  }

  @Override
  public void run() {
    long frameTime = 1000000000 / FPS;
    long currTime = System.nanoTime();
    long nextTime = currTime + frameTime;
    while (gameThread != null) {
      //check if program needs to exit
      if (inputChecker.shouldExit) {
        //erase thread
        gameThread = null;
        System.out.println("Game Closing!");
        //close program
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
      }

      //update timer
      currTime = System.nanoTime();

      //check if objects should be updated
      if (currTime >= nextTime) {
        //update positions
        removePieces();
        //check if game over
        checkIfCheckMate();
        //draw objects
        repaint();
        //reset timers
        nextTime = currTime + frameTime;
      }

      //check if game over
      if(gameOver) {
        //check if black or white win
        if (whiteWin) {
          System.out.println("White Wins!");
        }
        else if (blackWin) {
          System.out.println("Black Wins!");
        }

        //end game loop
        System.out.println("Exit to start again");
        this.removeMouseListener(updater);
        gameThread.stop();
      }

      //System.out.println("Game Running!");
    }
  }

  private void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  private void removePieces() {
    GameObject piece = updater.pieceToRemove;
    //check if removal needed
    if (piece == null) {
      return;
    }
    //else subtract points
    if (piece.getColor() == GameObject.tileColor.BLACK) {
      blackScore -= piece.getCost();
    }
    else {
      whiteScore -= piece.getCost();
    }

    //remove piece
    gameObjects.removeIf(object->object == piece);
    updater.pieceToRemove = null;

    printScore();
  }

  private void checkIfCheckMate() {
    boolean inCheckMate = true;

    //check if white king in check
    if(whiteKing.inCheck) {
      //check if any moves are possible
      for (int i = 0; i < gameObjects.size(); i++) {
        if (!gameObjects.get(i).toString().equals("king") && gameObjects.get(i).getColor().equals(GameObject.tileColor.WHITE)) {
          //check if any piece has the ability to be moved without the king still being in check
          if (testMoves(gameObjects.get(i), whiteKing)) {
            //there is a possible move
            inCheckMate = false;
            break;
          }
        }
      }
      //check if king in checkmate
      if (inCheckMate) {
        gameOver = true;
        blackWin = true;
      }
    }
    //check if black king in check
    if(blackKing.inCheck) {
      printGameObjects();
      //check if any moves are possible
      for (int i = 0; i < gameObjects.size(); i++) {
        if (!gameObjects.get(i).toString().equals("king") && gameObjects.get(i).getColor().equals(GameObject.tileColor.BLACK)) {
          //System.out.println("Testing moves for: " + gameObjects.get(i).getColor().toString() + " " + gameObjects.get(i).toString() + " " + gameObjects.get(i).hashCode());
          //check if any piece has the ability to be moved without the king still being in check
          if (testMoves(gameObjects.get(i), blackKing)) {
            //there is a possible move
            inCheckMate = false;
            break;
          }
        }
      }
      //check if king in checkmate
      if (inCheckMate) {
        gameOver = true;
        whiteWin = true;
      }
    }
  }

  private boolean testMoves(GameObject piece, King king) {
    GameObject oldPiece;
    Tile oldTile;
    for(Tile tile : piece.getPossibleMoves()) {
      //System.out.println("Testing moves for: " + piece.getColor().toString() + " " + piece.toString());
      //put piece in tile
      oldPiece = tile.currPiece;
      oldTile = piece.getCurrTile();
      piece.getCurrTile().currPiece = null;
      tile.currPiece = piece;

      //check if king not in check
      if (!king.checkIfCheck(king.getCurrTile())) {
        //fix piece changes
        tile.currPiece = oldPiece;
        oldTile.currPiece = piece;
        return true;
      }
      //fix piece changes
      tile.currPiece = oldPiece;
      oldTile.currPiece = piece;
    }

    //default return
    return false;
  }

  private void printScore() {
    System.out.println("White Score: " + whiteScore +
                          "\nBlack Score: " + blackScore);
  }

  private void printGameObjects() {
    for (int i = 0; i < gameObjects.size(); i++) {
      System.out.println(gameObjects.get(i).toString());
    }
  }

  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    //draw board
    Graphics2D graphics2D = (Graphics2D)graphics;
    for (Tile object : tiles) {
      object.draw(graphics2D);
    }
    for (GameObject object : gameObjects) {
      object.draw(graphics2D);
    }

    //end graphics process
    graphics2D.dispose();
  }
}
