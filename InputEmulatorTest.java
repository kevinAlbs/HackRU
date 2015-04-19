import java.awt.AWTException;

public class InputEmulatorTest {
	public static void main(String[] args) throws AWTException{
		InputEmulator ie = new InputEmulator();
		Thread t = new Thread(ie);
		t.start();
		try{
			Thread.sleep(1000);
		} catch(Exception e){}
		ie.enqueueCommand("keypress:a");
	}
}