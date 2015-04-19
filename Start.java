import java.net.UnknownHostException;
import java.awt.AWTException;

public class Start {
    public static void main(String [] args) {
        try {
            System.out.println("Starting web socket server.");
            MyWebSocketServer.main(new String[0]);
            System.out.println("\nStarting HTTP server.....");
            RunServer.main(new String[0]);
            System.out.println("\nAll servers running!");
        } catch(AWTException awte){
            System.err.println("Your system does not support the Java Robot library. We cannot emulate your mouse/keyboard");
        } catch (UnknownHostException uhe){
            System.err.println("The HTTP server cannot be created");
        } catch (Exception e) {
            System.out.println("Could not start servers. An unknown exception has occurred.");
            e.printStackTrace();
        }

    }
}

