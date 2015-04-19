import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.awt.event.KeyEvent;
 
public class InputEmulator implements Runnable
{
  private ConcurrentLinkedQueue<String> commandQ;
  private Robot robot = new Robot();
   
  public InputEmulator() throws AWTException
  {
    //robot.setAutoDelay(40);
    robot.setAutoWaitForIdle(true);
    commandQ = new ConcurrentLinkedQueue<String>();
  }

  private void type(int i)
  {
    robot.delay(40);
    robot.keyPress(i);
    robot.keyRelease(i);
  }
 
  private void type(String s)
  {
    System.out.println("Typing " + s);
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

  private void runCommand(String s){
    String[] parts = s.split(":");
    String name = parts[0];
    String[] values = {};
    if (parts.length == 2) {
      values = parts[1].split(",");
    }
    if(name.equals("keypress")){
      for(String k : values){
        type(k);
      }
    } else if(name.equals("keydown")){
      for(String k : values){
        if(k.equals("shift")){
          //hacky, but will do later
          robot.delay(40);
          robot.keyPress(KeyEvent.VK_SHIFT);
        } else if(k.equals("enter")){
          robot.delay(40);
          robot.keyPress(KeyEvent.VK_ENTER);
        }
      }
    } else if(name.equals("keyup")) {
      for(String k : values){
        if(k.equals("shift")){
          //hacky, but will do later
          robot.delay(40);
          robot.keyRelease(KeyEvent.VK_SHIFT);
        } else if(k.equals("enter")){
          robot.delay(40);
          robot.keyRelease(KeyEvent.VK_ENTER);
        }
      }
    }else if (name.equals("movemouse")){
      int x = Integer.parseInt(values[0]);
      int y = Integer.parseInt(values[1]);
      robot.mouseMove(x,y);
      System.out.println("Moving mouse");
      //robot.delay()
    } else if (name.equals("mouseclick")) {
      robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    } else if (name.equals("mousedown")){
      robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    } else if (name.equals("mouseup")){
      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
  }

  public void enqueueCommand(String s){
    commandQ.add(s);
  }

  public void run(){
    while(true){
      while(!commandQ.isEmpty()){
        String command = commandQ.remove();
        System.out.println("Parsing " + command);
        runCommand(command);
      }
    }
  }
}
