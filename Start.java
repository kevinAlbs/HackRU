public class Start {
    public static void main(String [] args) {
        try {
            System.out.println("Starting SOCKET SERVER....");
            MyWebSocketServer.main(new String[0]);
            System.out.println("SOCKET SERVER running\n\nStarting LOCAL WEB SERVER.....");
            RunServer.main(new String[0]);
            System.out.println("LOCAL WEB SERVER running\nAll servers running!");
        } catch (Exception e) {
            System.out.println("Could not start servers");
        }

    }
}

