package Game;

import Game.Pieces.*;

import javax.swing.*;
import java.awt.*;

public class PieceChanger extends JPanel{
  private GamePanel game;
  final int WIDTH = 300;
  final int HEIGHT = 500;
  public static final int BUTTON_WIDTH = 100;
  public static final int BUTTON_HEIGHT = 50;
  private JTextField instructions;
  private JButton bishop;
  private JButton knight;
  private JButton queen;
  private JButton rook;
  public Boolean pieceChosen = false;
  GameObject newPiece;
  GameObject oldPiece;


  //constructor for class
  public PieceChanger(GamePanel game, GameObject oldPiece) {
    this.game = game;
    this.oldPiece = oldPiece;

    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.white);
    this.setDoubleBuffered(true);
    drawButtons();

    this.setFocusable(true);
  }

  //method to define button functionality
  private void drawButtons() {
    System.out.println("draw buttons");
    instructions = new JTextField("Pick new piece!");
    bishop = new JButton("Bishop");
    knight = new JButton("Knight");
    queen = new JButton("Queen");
    rook = new JButton("Rook");

    this.add(instructions);
    this.add(bishop);
    this.add(knight);
    this.add(queen);
    this.add(rook);

    this.setLayout(null);

    //set bounds for instructions
    instructions.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50, BUTTON_WIDTH, BUTTON_HEIGHT);

    //set bounds for bishop
    bishop.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50 + 20 + BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    bishop.addActionListener(ActionListener->{
      //add new piece
      newPiece = new Bishop(oldPiece.getXPos(), oldPiece.getYPos(), oldPiece.getColor(), this.game, oldPiece.getCurrTile());
      //remove old piece and add new
      game.gameObjects.remove(oldPiece);
      game.gameObjects.add(newPiece);
      if (newPiece.getColor().equals(GameObject.tileColor.BLACK)) {
        game.blackScore -= oldPiece.getCost();
        game.blackScore += newPiece.getCost();
      }
      else {
        game.whiteScore -= oldPiece.getCost();
        game.whiteScore += newPiece.getCost();
      }
      System.out.println("Bishop chosen!");
      System.out.println("White Score: " + game.whiteScore +
                             "\nBlack Score: " + game.blackScore);
      pieceChosen = true;
    });

    //set bounds for knight
    knight.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50 + 40 + 2*BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    knight.addActionListener(ActionListener->{
      //add new piece
      newPiece = new Knight(oldPiece.getXPos(), oldPiece.getYPos(), oldPiece.getColor(), this.game, oldPiece.getCurrTile());
      //remove old piece and add new
      game.gameObjects.remove(oldPiece);
      game.gameObjects.add(newPiece);
      if (newPiece.getColor().equals(GameObject.tileColor.BLACK)) {
        game.blackScore -= oldPiece.getCost();
        game.blackScore += newPiece.getCost();
      }
      else {
        game.whiteScore -= oldPiece.getCost();
        game.whiteScore += newPiece.getCost();
      }
      System.out.println("Bishop chosen!");
      System.out.println("White Score: " + game.whiteScore +
                             "\nBlack Score: " + game.blackScore);
      pieceChosen = true;
    });

    //set bounds for queen
    queen.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50 + 60 + 3*BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    queen.addActionListener(ActionListener->{
      //add new piece
      newPiece = new Queen(oldPiece.getXPos(), oldPiece.getYPos(), oldPiece.getColor(), this.game, oldPiece.getCurrTile());
      //remove old piece and add new
      game.gameObjects.remove(oldPiece);
      game.gameObjects.add(newPiece);
      if (newPiece.getColor().equals(GameObject.tileColor.BLACK)) {
        game.blackScore -= oldPiece.getCost();
        game.blackScore += newPiece.getCost();
      }
      else {
        game.whiteScore -= oldPiece.getCost();
        game.whiteScore += newPiece.getCost();
      }
      System.out.println("Bishop chosen!");
      System.out.println("White Score: " + game.whiteScore +
                             "\nBlack Score: " + game.blackScore);
      pieceChosen = true;
    });

    //set bounds for rook
    rook.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50 + 80 + 4*BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    rook.addActionListener(ActionListener->{
      //add new piece
      newPiece = new Rook(oldPiece.getXPos(), oldPiece.getYPos(), oldPiece.getColor(), this.game, oldPiece.getCurrTile());
      //remove old piece and add new
      game.gameObjects.remove(oldPiece);
      game.gameObjects.add(newPiece);
      if (newPiece.getColor().equals(GameObject.tileColor.BLACK)) {
        game.blackScore -= oldPiece.getCost();
        game.blackScore += newPiece.getCost();
      }
      else {
        game.whiteScore -= oldPiece.getCost();
        game.whiteScore += newPiece.getCost();
      }
      System.out.println("Bishop chosen!");
      System.out.println("White Score: " + game.whiteScore +
                             "\nBlack Score: " + game.blackScore);
      pieceChosen = true;
    });

  }
}
