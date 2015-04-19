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
        server.createContext("/mouse", new MyHandler(copyFile("ui.html")));
        server.createContext("/rotate", new MyHandler(copyFile("rotate.html")));
        server.setExecutor(null); // creates a default executor
        String full_address = "http://" + DETERMINED_IP + ":8000/";
        System.out.println("HTTP Server Running.");
        System.out.println("Navigate to " + full_address + "mouse for a trackpad");
        System.out.println("Navigate to "+ full_address + "rotate for a GIMP rotation button");
        server.start();
    }

    private static String copyFile(String filename) {
        BufferedReader br = null;
        PrintWriter pw = null; 
        String sourceFileName = filename;
        String destinationFileName = "mod_" + filename;
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
        return destinationFileName;
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
        private String filename;
        public MyHandler(String filename){
            this.filename = filename;
        }
        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            File file = new File(filename);
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
