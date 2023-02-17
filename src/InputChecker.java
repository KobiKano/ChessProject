import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputChecker implements KeyListener {
  public boolean shouldExit = false;

  @Override
  public void keyTyped(KeyEvent e) {

  }
  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    //check for exit operation
    if (keyCode == KeyEvent.VK_ESCAPE) {
      shouldExit = true;
    }
  }
  @Override
  public void keyReleased(KeyEvent e) {
  }
}
