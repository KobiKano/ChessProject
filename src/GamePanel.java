import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
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
  LinkedList<Tile> tiles = new LinkedList<>();
  LinkedList<GameObject> gameObjects = new LinkedList<>();
  InputChecker inputChecker = new InputChecker();
  MouseChecker mouseChecker = new MouseChecker(gameObjects);
  Thread gameThread;
  final int FPS = 60;

  int blackScore = 39;
  int whiteScore = 39;
  boolean gameOver = false;

  //constructor for class
  public GamePanel(JFrame window) {
    this.window = window;
    //set game panel settings
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.white);
    this.setDoubleBuffered(true);
    this.addKeyListener(inputChecker);
    this.addMouseListener(mouseChecker);

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
          tiles.add(new Tile(x,y, Tile.tileColor.BLACK, this, inputChecker));
        }
        else {
          tiles.add(new Tile(x,y, Tile.tileColor.WHITE, this, inputChecker));
        }
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
  }

  //This method draws all the pieces
  private void drawPieces() {
    //draw black pieces
    //draw pawns
    int pawnTileIndex = 1;
    for (int i = ACTUAL_SIZE/2; i < WIDTH - ACTUAL_SIZE; i += ACTUAL_SIZE) {
      gameObjects.add(new Pawn(i, ACTUAL_SIZE, GameObject.tileColor.BLACK, this, tiles.get(pawnTileIndex)));
      pawnTileIndex += 8;
    }
    //draw rooks
    gameObjects.add(new Rook(ACTUAL_SIZE/2, 0, GameObject.tileColor.BLACK, this, tiles.get(0)));
    gameObjects.add(new Rook( WIDTH -  2 * ACTUAL_SIZE + ACTUAL_SIZE/2, 0, GameObject.tileColor.BLACK, this, tiles.get(56)));
    //draw knights
    gameObjects.add(new Knight(ACTUAL_SIZE/2 + ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this, tiles.get(8)));
    gameObjects.add(new Knight(WIDTH - 3 * ACTUAL_SIZE + ACTUAL_SIZE/2, 0 , GameObject.tileColor.BLACK, this, tiles.get(48)));
    //draw bishops
    gameObjects.add(new Bishop(ACTUAL_SIZE/2 + 2 * ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this, tiles.get(16)));
    gameObjects.add(new Bishop(WIDTH - 4 * ACTUAL_SIZE + ACTUAL_SIZE/2, 0 , GameObject.tileColor.BLACK, this, tiles.get(40)));
    //draw queen
    gameObjects.add(new Queen(ACTUAL_SIZE/2 + 3 * ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this, tiles.get(24)));
    //draw king
    gameObjects.add(new King(ACTUAL_SIZE/2 + 4 * ACTUAL_SIZE, 0 , GameObject.tileColor.BLACK, this, tiles.get(32)));


    //draw white pieces
    //draw pawns
    pawnTileIndex = 6;
    for (int i = ACTUAL_SIZE/2; i < WIDTH - ACTUAL_SIZE; i += ACTUAL_SIZE) {
      gameObjects.add(new Pawn(i, HEIGHT - 3 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this, tiles.get(pawnTileIndex)));
      pawnTileIndex += 8;
    }
    //draw rooks
    gameObjects.add(new Rook(ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this, tiles.get(7)));
    gameObjects.add(new Rook( WIDTH -  2 * ACTUAL_SIZE + ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this, tiles.get(63)));
    //draw knights
    gameObjects.add(new Knight(ACTUAL_SIZE/2 + ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE , GameObject.tileColor.WHITE, this, tiles.get(7 + 8)));
    gameObjects.add(new Knight(WIDTH - 3 * ACTUAL_SIZE + ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this, tiles.get(63 - 8)));
    //draw bishops
    gameObjects.add(new Bishop(ACTUAL_SIZE/2 + 2 * ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE , GameObject.tileColor.WHITE, this, tiles.get(7 + 2*8)));
    gameObjects.add(new Bishop(WIDTH - 4 * ACTUAL_SIZE + ACTUAL_SIZE/2, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this, tiles.get(63 - 2*8)));
    //draw queen
    gameObjects.add(new Queen(ACTUAL_SIZE/2 + 3 * ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this, tiles.get(7 + 3*8)));
    //draw king
    gameObjects.add(new King(ACTUAL_SIZE/2 + 4 * ACTUAL_SIZE, HEIGHT - 2 * ACTUAL_SIZE, GameObject.tileColor.WHITE, this, tiles.get(63 - 2*8)));
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
    GameObject piece = mouseChecker.pieceToRemove;
    //check if removal needed
    if (piece == null) {
      return;
    }

    //check if piece is king
    if (piece.toString().equals("King")) {
      gameOver = true;
    }
    //else subtract points
    else if (piece.getColor() == GameObject.tileColor.BLACK) {
      blackScore -= piece.getCost();
    }
    else {
      whiteScore -= piece.getCost();
    }

    //remove piece
    gameObjects.removeIf(object->object == piece);

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
