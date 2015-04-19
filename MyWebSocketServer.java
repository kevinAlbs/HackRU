/*
Compile with:
javac -cp Java-WebSocket/dist/java_websocket.jar TestServer.java
Run with:
java -cp Java-WebSocket/dist/java_websocket.jar:. TestServer
*/
import java.net.UnknownHostException;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.awt.AWTException;
import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

public class MyWebSocketServer extends WebSocketServer{
	private InputEmulator ie;
	private Thread ie_thread;

	public MyWebSocketServer(int port) throws UnknownHostException, AWTException {
		super( new InetSocketAddress(InetAddress.getLocalHost(), port) );
		ie = new InputEmulator();
		ie_thread = new Thread(ie);
		ie_thread.start();
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ){
		System.out.println("Websocket open");
	}
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ){
		System.out.println("Websocket closed");
	}

	@Override
	public void onMessage( WebSocket conn, String message ){
		System.out.println("Recieved message " + message);
		if(message.equals("getinfo")){
			//special command, return screen size, and any other relevant info TODO
		} else {
			ie.enqueueCommand(message);
		}
	}

	@Override
	public void onError( WebSocket conn, Exception ex ){}

	public static void main(String[] args) throws UnknownHostException{
		MyWebSocketServer server = null;
		try {
			server = new MyWebSocketServer(8081);	
		} catch(AWTException awte){
			System.err.println("Your system does not support the Java Robot library. We cannot emulate your mouse/keyboard");
		} catch (UnknownHostException uhe){
			System.err.println("A server cannot be created");
		}		
		server.start();
		InetSocketAddress addr = server.getAddress();
		System.out.println("Input Emulator is Running");
		// TODO spawn new process for http server
		System.out.println("Navigate your phone to http://" + addr.getAddress().getHostAddress() + ":" + addr.getPort());

		long sex = 60;
		System.out.println("You have " + sex + " seconds before this program terminates");

		Timer t = new Timer();
		t.schedule(new TimerTask(){
			public void run(){
				System.exit(0);
			}
		}, sex * 1000);
	}
}