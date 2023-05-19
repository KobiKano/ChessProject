package Game;

import javax.swing.*;

public class ChessGame {
  //code to define player color
  protected enum PlayerColor{
    BLACK, WHITE
  }
  protected static class MutableDefinitions {
    protected boolean value;
    protected PlayerColor color;
    protected Integer difficulty;
    protected MutableDefinitions(boolean value, PlayerColor color, Integer difficulty) {
      this.value = value;
      this.color = color;
      this.difficulty = difficulty;
    }
    protected void setValue(boolean value) {
      this.value = value;
    }
    protected void setColor(PlayerColor color) {
      this.color = color;
    }
    protected void setDifficulty(Integer difficulty) {
      this.difficulty = difficulty;
    }
  }

  //main method for game
  public static void main(String[] args) {
    //create window
    JFrame window = new JFrame();
    //set window parameters
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setName("Chess Game");
    window.setLocationRelativeTo(null);

    //start menu screen
    MutableDefinitions defs = new MutableDefinitions(false, PlayerColor.WHITE, 1); //default color white
    MenuPanel menu = new MenuPanel(window, defs);
    window.add(menu);
    window.pack();
    window.setVisible(true);

    //wait until color selected
    System.out.println("Waiting for Player to start!");
    while(!defs.value){System.out.print("");}  //do nothing

    //hide menu
    window.remove(menu);
    window.setVisible(false);

    //set game panel
    GamePanel game = new GamePanel(window, defs.color);
    window.add(game);
    window.pack();
    window.setVisible(true);
  }

}
