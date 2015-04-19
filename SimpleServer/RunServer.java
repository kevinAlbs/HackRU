import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RunServer {
    // this assumes that the file is in the same directory as the Java server file
    private static String STATIC_FILE_LOCATION = "ui-working.html";

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/controller", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            File file = new File(STATIC_FILE_LOCATION);
            FileInputStream fs = new FileInputStream(file);
            final byte[] buffer = new byte[0x10000];
            int count = 0;
            while((count = fs.read(buffer)) >= 0) {
                os.write(buffer,0,count);
            }
            fs.close();
            os.close();
        }
    }

}

