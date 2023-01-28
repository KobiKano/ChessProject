import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputChecker implements KeyListener {
  public boolean shouldExit = false;


  @Override
  public void keyTyped(KeyEvent e) {

  }
  @Override
  public void keyPressed(KeyEvent e) {
    //check for exit operation
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      shouldExit = true;
    }
  }
  @Override
  public void keyReleased(KeyEvent e) {

  }
}
