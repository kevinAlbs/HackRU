/*
Compile with:
javac -cp Java-WebSocket/dist/java_websocket.jar TestServer.java
Run with:
java -cp Java-WebSocket/dist/java_websocket.jar:. TestServer
*/
import java.net.UnknownHostException;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

public class MyWebSocketServer extends WebSocketServer{
	private InputEmulator ie;
	private Thread ie_thread;

	public MyWebSocketServer(int port) throws UnknownHostException, AWTException {
		//super( new InetSocketAddress(InetAddress.getLocalHost(), port) );
		super( new InetSocketAddress(fetchLocalIP(), port) );
		ie = new InputEmulator();
		ie_thread = new Thread(ie);
		ie_thread.start();
    }

     private static InetAddress fetchLocalIP() {
        InetAddress myLocalIP = null;
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                // filters out localhost
                if (n.isLoopback() || !n.isUp()) {
                    continue;
                }
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(!(i instanceof Inet4Address)) {
                        continue;
                    }
                    myLocalIP = i;
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching IP address");
            System.exit(1);
        }
        if(myLocalIP == null) {
            System.out.println("Error fetching IP address");
            System.exit(1);
        }
        return myLocalIP;
    }

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		conn.send("info:" + width + "," + height);
	}

	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ){
		System.out.println("Websocket closed");
	}

	@Override
	public void onMessage( WebSocket conn, String message ){
		//System.out.println("Recieved message " + message);
		ie.enqueueCommand(message);
	}

	@Override
	public void onError( WebSocket conn, Exception ex ){}

	public static void main(String[] args) throws UnknownHostException, AWTException{
		MyWebSocketServer server = null;
		server = new MyWebSocketServer(8081);			
		server.start();
		InetSocketAddress addr = server.getAddress();
		System.out.println("Web Socket Server Running.");
		/*
		long sex = 300;
		System.out.println("You have " + sex + " seconds before this program terminates");

		Timer t = new Timer();
		t.schedule(new TimerTask(){
			public void run(){
				System.exit(0);
			}
		}, sex * 1000);
		*/
	}
}
