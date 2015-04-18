import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;


public class RunServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
//            BufferedReader br = new BufferedReader(new FileReader("Basic.html"));
            //String response = "<h1>This is the response</h1>";
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            File file = new File("Basic.html");
            FileInputStream fs = new FileInputStream(file);
            final byte[] buffer = new byte[0x10000];
            int count = 0;
            while((count = fs.read(buffer)) >= 0) {
                os.write(buffer,0,count);
            }
//            os.write(Files.readAllBytes(path));
            //os.write(response.getBytes());
            fs.close();
            os.close();
        }
    }

}

