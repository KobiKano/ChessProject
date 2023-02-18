package Game;

import javax.swing.*;

public class ChessGame {
  //main method for game
  public static void main(String args[]) {
    //create window
    JFrame window = new JFrame();
    //set window parameters
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setName("Chess Game");
    window.setLocationRelativeTo(null);

    //set game panel
    GamePanel game = new GamePanel(window);
    window.add(game);
    window.pack();

    //show window
    window.setVisible(true);
  }

}
