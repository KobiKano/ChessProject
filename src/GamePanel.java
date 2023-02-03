import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class GamePanel extends JPanel implements Runnable {
  JFrame window;
  //define screen size
  final int TILE_SIZE = 16;  //16 x 16 tiles
  final int SCALE = 5;
  final int ACTUAL_SIZE = TILE_SIZE * SCALE; //scale up for monitor size

  final int COLUMNS = 9; //define size of board
  final int ROWS = 9;

  final int WIDTH = COLUMNS * ACTUAL_SIZE;  //translate to window dimensions
  final int HEIGHT = ROWS * ACTUAL_SIZE;

  //create game variables
  InputChecker inputChecker = new InputChecker();
  Thread gameThread;
  LinkedList<BoardTile> tiles = new LinkedList<>();
  LinkedList<GameObject> gameObjects = new LinkedList<>();
  final int FPS = 60;

  //constructor for class
  public GamePanel(JFrame window) {
    this.window = window;
    //set game panel settings
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.white);
    this.setDoubleBuffered(true);
    this.addKeyListener(inputChecker);
    this.setFocusable(true);
    //start game
    drawBoard();
    drawPieces();
    startGameThread();
  }

  //This method draws the board tiles
  private void drawBoard() {
    boolean isBlack = true;
    for (int x = ACTUAL_SIZE/2; x < WIDTH - ACTUAL_SIZE; x += ACTUAL_SIZE) {
      isBlack = !isBlack;
      for (int y = 0; y < HEIGHT - ACTUAL_SIZE; y += ACTUAL_SIZE) {
        if (isBlack) {
          tiles.add(new Tile(x,y, BoardTile.tileColor.BLACK, this, inputChecker));
        }
        else {
          tiles.add(new Tile(x,y, BoardTile.tileColor.WHITE, this, inputChecker));
        }
        isBlack = !isBlack;
      }
    }
  }

  //This method draws all the pieces
  private void drawPieces() {
    //draw black pieces
    //draw pawns
    for (int i = ACTUAL_SIZE/2; i < WIDTH - ACTUAL_SIZE; i += ACTUAL_SIZE) {
      gameObjects.add(new Pawn(i, ACTUAL_SIZE, GameObject.tileColor.BLACK, this));
    }
    //draw rooks
    gameObjects.add(new Rook(ACTUAL_SIZE/2, 0, GameObject.tileColor.BLACK, this));
    gameObjects.add(new Rook( WIDTH -  2 * ACTUAL_SIZE + ACTUAL_SIZE/2, 0, GameObject.tileColor.BLACK, this));
    //draw knights
    gameObjects.add(new Knight(ACTUAL_SIZE/2 + ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this));
    gameObjects.add(new Knight(WIDTH - 3 * ACTUAL_SIZE + ACTUAL_SIZE/2, 0 , GameObject.tileColor.BLACK, this));
    //draw bishops
    gameObjects.add(new Bishop(ACTUAL_SIZE/2 + 2 * ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this));
    gameObjects.add(new Bishop(WIDTH - 4 * ACTUAL_SIZE + ACTUAL_SIZE/2, 0 , GameObject.tileColor.BLACK, this));
    //draw queen
    gameObjects.add(new Queen(ACTUAL_SIZE/2 + 3 * ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this));
    //draw king
    gameObjects.add(new King(ACTUAL_SIZE/2 + 4 * ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this));


    //draw white pieces
    //draw pawns
    for (int i = ACTUAL_SIZE/2; i < WIDTH - ACTUAL_SIZE; i += ACTUAL_SIZE) {
      gameObjects.add(new Pawn(i, HEIGHT - 3 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this));
    }
    //draw rooks
    gameObjects.add(new Rook(ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this));
    gameObjects.add(new Rook( WIDTH -  2 * ACTUAL_SIZE + ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this));
    //draw knights
    gameObjects.add(new Knight(ACTUAL_SIZE/2 + ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE , GameObject.tileColor.WHITE, this));
    gameObjects.add(new Knight(WIDTH - 3 * ACTUAL_SIZE + ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this));
    //draw bishops
    gameObjects.add(new Bishop(ACTUAL_SIZE/2 + 2 * ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE , GameObject.tileColor.WHITE, this));
    gameObjects.add(new Bishop(WIDTH - 4 * ACTUAL_SIZE + ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this));
    //draw queen
    gameObjects.add(new Queen(ACTUAL_SIZE/2 + 3 * ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this));
    //draw king
    gameObjects.add(new King(ACTUAL_SIZE/2 + 4 * ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this));
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
        updatePositions();
        //draw objects
        repaint();
        //reset timers
        nextTime = currTime + frameTime;
      }

      System.out.println("Game Running!");
    }
  }

  private void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  private void updatePositions() {
    for (GameObject object : gameObjects) {
      if (object.hasBeenMoved()) {
      }
    }
  }

  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    //draw board
    Graphics2D graphics2D = (Graphics2D)graphics;
    for (BoardTile object : tiles) {
      object.draw(graphics2D);
    }
    for (GameObject object : gameObjects) {
      object.draw(graphics2D);
    }

    //end graphics process
    graphics2D.dispose();
  }
}
