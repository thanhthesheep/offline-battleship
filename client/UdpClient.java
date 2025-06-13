package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Scanner;


/*
Step 1: Create socket object for carrying the data -- DONE
Step 2: Create the datagramPacket for sending the data -- DONE
Step 3: invoke the send call to actually send the date

Note: User says bye to break the loop and close the socket
*/


public class UdpClient{
    public static void main(String args[]) throws IOException{
        

        DatagramSocket socket;
        DatagramPacket sending;
        int port;
        byte[] send = new byte[65535];
        Scanner scanner = new Scanner(System.in);

        port = args.length > 0 ? Integer.parseInt(args[0]):8000;
        InetAddress ip = InetAddress.getLocalHost();

        socket = new DatagramSocket();


        while(true)
        {
            String inp = scanner.nextLine();
            send = inp.getBytes();
    
            sending = new DatagramPacket(send,send.length,ip,port);
                System.out.println("Sending packet to server at port: " + port);
                socket.send(sending);

            System.out.println("sending message: " + inp);

            if(inp.equals("bye"))
            {
                System.out.println("Ending connection...bye");
                break;
            }
        }
        socket.close();
        scanner.close();
}
}

