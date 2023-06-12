package Game;

import javax.swing.*;

public class ChessGame {
  //code to define player color
  protected enum PlayerColor{
    BLACK, WHITE
  }
  protected static class Definitions {
    protected boolean value;
    protected PlayerColor color;
    protected Integer difficulty;
    protected boolean useOpenings;
    protected Definitions(boolean value, PlayerColor color, Integer difficulty, boolean useOpenings) {
      this.value = value;
      this.color = color;
      this.difficulty = difficulty;
      this.useOpenings = useOpenings;
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
    protected void setUseOpenings(boolean useOpenings) {this.useOpenings = useOpenings;}
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
    Definitions definitions = new Definitions(false, PlayerColor.WHITE, 0, false); //default color white
    MenuPanel menu = new MenuPanel(window, definitions);
    window.add(menu);
    window.pack();
    window.setVisible(true);

    //wait until color selected
    System.out.println("Waiting for Player to start!");
    while(!definitions.value){System.out.print("");}  //do nothing

    //hide menu
    window.remove(menu);
    window.setVisible(false);

    //set game panel
    GamePanel game = new GamePanel(window, definitions);
    window.add(game);
    window.pack();
    window.setVisible(true);
  }

}
