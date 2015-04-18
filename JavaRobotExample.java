import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
 
public class JavaRobotExample
{
  Robot robot = new Robot();
 
  public static void main(String[] args) throws AWTException
  {
    new JavaRobotExample();
  }
   
  public JavaRobotExample() throws AWTException
  {
    robot.setAutoDelay(40);
    robot.setAutoWaitForIdle(true);
     
    System.out.println("About to move mouse to top left corner of screen");
    robot.delay(2000);
    robot.mouseMove(10, 10);
 
    robot.delay(500);
    type("Hello, world");
 
    System.exit(0);
  }

  private void type(int i)
  {
    robot.delay(40);
    robot.keyPress(i);
    robot.keyRelease(i);
  }
 
  private void type(String s)
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
}