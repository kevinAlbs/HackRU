import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.*;
import java.util.*;
public class Test {
    public static void main(String [] args) throws UnknownHostException {
    //    System.out.println(InetAddress.getLocalHost().getHostAddress());
        Enumeration e;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch(Exception q) {
            return;
        }

        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            String up = "no";
            try {
                if(n.isUp())
                    up = "yes";
                if (n.isLoopback() || !n.isUp())
                    continue;
            } catch (Exception q) {
                return;
            }
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if(!(i instanceof Inet4Address))
                    continue;
                System.out.println(i.getHostAddress() + " isUP: " + up );
            }
        }

    }
}

