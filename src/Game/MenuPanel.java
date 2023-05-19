package Game;


import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MenuPanel extends JPanel {
  JFrame window;
  public ChessGame.MutableDefinitions defs;
  final int WIDTH = 300;
  final int HEIGHT = 500;
  public static final int BUTTON_WIDTH = 100;
  public static final int BUTTON_HEIGHT = 50;
  private JButton start;
  private JButton exit;
  private JButton black;
  private JButton white;
  private JSlider difficulty;
  private JTextField difficultyText;


  //constructor for class
  public MenuPanel(JFrame window, ChessGame.MutableDefinitions defs) {
    this.window = window;
    this.defs = defs;

    //set up menu panel
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.white);
    this.setDoubleBuffered(true);
    drawButtons();

    this.setFocusable(true);
  }

  private void drawButtons() {
    //define buttons
    start = new JButton("Start");
    exit = new JButton("Exit");
    black = new JButton("Black");
    white = new JButton("White");
    difficulty = new JSlider(0,100,1);
    difficultyText = new JTextField("Difficulty: 1");

    //add to menuPanel
    this.add(start);
    this.add(exit);
    this.add(black);
    this.add(white);
    this.add(difficulty);
    this.add(difficultyText);
    this.setLayout(null);

    //set parameters for start button
    start.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50, BUTTON_WIDTH, BUTTON_HEIGHT);
    start.addActionListener(ActionListener->{
      System.out.println("Starting game!");
      defs.setValue(true);
    });

    //set parameters for exit button
    exit.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 100 + 5*BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    exit.addActionListener(ActionListener->System.exit(0));

    //set parameters for black selection
    black.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50 + 20 + BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    black.setBackground(Color.GRAY);
    black.addActionListener(ActionListener->{
      white.setBackground(Color.GRAY);
      black.setBackground(Color.GREEN);
      defs.setColor(ChessGame.PlayerColor.BLACK);
      System.out.println("Color chosen: Black!");
    });

    //set parameters for white selection
    white.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 50 + 40 + 2*BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
    white.setBackground(Color.GREEN);
    white.addActionListener(ActionListener->{
      black.setBackground(Color.GRAY);
      white.setBackground(Color.GREEN);
      defs.setColor(ChessGame.PlayerColor.WHITE);
      System.out.println("Color chosen: White!");
    });

    //set difficulty slider parameters
    difficulty.setBounds((int)(WIDTH*0.1)/2, 50 + 60 + 3*BUTTON_HEIGHT, (int)(WIDTH*0.9), BUTTON_HEIGHT);
    difficulty.setMajorTickSpacing(10);
    difficulty.setPaintTicks(true);
    difficulty.setPaintLabels(true);
    difficulty.addChangeListener(ChangeListener->{
      difficultyText.setText("Difficulty: " + difficulty.getValue());
      defs.setDifficulty(difficulty.getValue());
    });

    //set difficulty text parameters
    difficultyText.setBounds(WIDTH/2 - BUTTON_WIDTH/2, 30 + 80 + 4*BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT/2);
  }
}
