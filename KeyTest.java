import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyTest extends JFrame{
	public static void main(String[] args){
		JFrame f = new KeyTest();
		f.setSize(100,100);
		f.setVisible(true);
		
		f.setLocationRelativeTo(null);
	}
	public KeyTest(){
		this.addKeyListener(new KeyListener(){
			 public void keyTyped(KeyEvent e) {
		        System.out.println("Hello");
		        System.out.println(e.getKeyCode());
		    }

		    /** Handle the key-pressed event from the text field. */
		    public void keyPressed(KeyEvent e) {
		         System.out.println("Hello");
		        System.out.println(e.getKeyCode());
		    }

		    /** Handle the key-released event from the text field. */
		    public void keyReleased(KeyEvent e) {
		        //displayInfo(e, "KEY RELEASED: ");
		    }
		});
	}
	
}