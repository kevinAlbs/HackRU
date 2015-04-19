public class Start {
    public static void main(String [] args) {
        try {
            System.out.println("hello");
            MyWebSocketServer.main(new String[0]);
            System.out.println("world!!!!");
        } catch (Exception e) {
            System.out.println("Could not start Socket Server");
        }

    }
}

