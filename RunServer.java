import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedReader;

import java.net.InetSocketAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.*;
import java.util.*;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RunServer {
    // this assumes that the file is in the same directory as the Java server file
    private static String STATIC_FILE_NAME = "ui.html";
    private static String DETERMINED_IP = null;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        copyFile();
        server.createContext("/controller", new MyHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("HTTP Server Running...");
        server.start();
    }

    private static void copyFile() {
        BufferedReader br = null;
        PrintWriter pw = null; 
        String sourceFileName = STATIC_FILE_NAME;
        String destinationFileName = "mod_" + STATIC_FILE_NAME;
        try {
            br = new BufferedReader(new FileReader( sourceFileName ));
            pw =  new PrintWriter(new FileWriter( destinationFileName ));

            String line;
            while ((line = br.readLine()) != null) {
                line = replaceIP(line);
                pw.println(line);
            }

            br.close();
            pw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        STATIC_FILE_NAME = destinationFileName;
    }
    private static String replaceIP(String src) {
        if(DETERMINED_IP == null) {
            determineIP();
        }
        return src.replaceAll("<LOCAL_IP>", DETERMINED_IP);
    }
    private static void determineIP() {
        String localIP = "not found";
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
                    localIP = i.getHostAddress();
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching IP address");
            System.exit(1);
        }
        if(localIP.equals("not found")) {
            System.out.println("Error fetching IP address");
            System.exit(1);
        }
        DETERMINED_IP = localIP;
    }

    public static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            File file = new File(STATIC_FILE_NAME);
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
