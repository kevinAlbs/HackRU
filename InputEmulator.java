import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
 
public class InputEmulator
{
  Robot robot = new Robot();
   
  public InputEmulator() throws AWTException
  {
    robot.setAutoDelay(40);
    robot.setAutoWaitForIdle(true);
  }

  private void type(int i)
  {
    robot.delay(40);
    robot.keyPress(i);
    robot.keyRelease(i);
  }
 
  public void type(String s)
  {
    byte[] bytes = s.getBytes();
    for (byte b : bytes)
    {
      int code = b;
      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
      if (code > 96 && code < 123) code = code - 32;
      robot.delay(40);
      robot.keyPress(code);
      robot.keyRelease(code);
    }
  }
  
  public void parseCommand(String s){

  }
}