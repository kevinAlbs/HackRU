/*
Compile with:
javac -cp Java-WebSocket/dist/java_websocket.jar TestServer.java
Run with:
java -cp Java-WebSocket/dist/java_websocket.jar:. TestServer
*/
import java.net.UnknownHostException;
import java.net.InetSocketAddress;
import java.net.InetAddress;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

public class MyWebSocketServer extends WebSocketServer{

	public MyWebSocketServer(int port) throws UnknownHostException {
		super( new InetSocketAddress(InetAddress.getLocalHost(), port) );
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ){
		System.out.println("Websocket open");
	}
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ){}

	@Override
	public void onMessage( WebSocket conn, String message ){
		System.out.println("Recieved message " + message);
		conn.send("Echo: " + message);
	}

	@Override
	public void onError( WebSocket conn, Exception ex ){}

	public static void main(String[] args) throws UnknownHostException{
		MyWebSocketServer server = new MyWebSocketServer(8081);
		server.start();
		InetSocketAddress addr = server.getAddress();
		System.out.println("Server listening at " + addr.getHostString() + ":" + addr.getPort());
	}
}