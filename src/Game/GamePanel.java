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
  LinkedList<GameObject> gameObjects = new LinkedList<>();
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

  //constructor for class
  public GamePanel(JFrame window, ChessGame.MutableDefinitions definitions) {
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
    //start game
    initializer.generateBoard();
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
        //draw objects
        repaint();
        //reset timers
        nextTime = currTime + frameTime;
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

    //check if piece is king
    if (piece.toString().equals("king")) {
      gameOver = true;
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

  private void printScore() {
    System.out.println("White Score: " + whiteScore +
                          "\nBlack Score: " + blackScore);
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
