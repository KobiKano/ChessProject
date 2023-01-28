import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
  JFrame window;
  //define screen size
  final int TILE_SIZE = 16;  //16 x 16 tiles
  final int SCALE = 5;
  final int ACTUAL_SIZE = TILE_SIZE * SCALE; //scale up for monitor size

  final int COLUMNS = 8; //define size of board
  final int ROWS = 8;

  final int WIDTH = COLUMNS * ACTUAL_SIZE;  //translate to window dimensions
  final int HEIGHT = ROWS * ACTUAL_SIZE;

  //create game variables
  InputChecker inputChecker = new InputChecker();
  Thread gameThread;
  ArrayList<GameObject> gameObjects = new ArrayList<>();
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
    startGameThread();
  }

  //This method draws the board tiles
  private void drawBoard() {
    boolean isBlack = false;
    for (int x = 0; x < WIDTH; x += ACTUAL_SIZE) {
      isBlack = !isBlack;
      for (int y = 0; y < HEIGHT; y += ACTUAL_SIZE) {
        if (isBlack) {
          gameObjects.add(new Tile(x,y, GameObject.tileColor.BLACK, this, inputChecker));
        }
        else {
          gameObjects.add(new Tile(x,y, GameObject.tileColor.WHITE, this, inputChecker));
        }
        isBlack = !isBlack;
      }
    }
  }

  //This method draws all the pieces
  private void drawPieces() {

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
    Graphics2D graphics2D = (Graphics2D)graphics;
    for (GameObject object : gameObjects) {
      object.draw(graphics2D);
    }
    graphics2D.dispose();
  }
}
