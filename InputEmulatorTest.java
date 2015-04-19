import java.awt.AWTException;

public class InputEmulatorTest {
	public static void main(String[] args) throws AWTException{
		InputEmulator ie = new InputEmulator();
		Thread t = new Thread(ie);
		t.start();
		try{
			Thread.sleep(5000);
		} catch(Exception e){}
		ie.enqueueCommand("keydown:enter");
		ie.enqueueCommand("keyup:enter");
	}
}