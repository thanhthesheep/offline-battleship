package server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer{

    public static void main(String[] args) throws IOException
    {

        DatagramSocket socket = null;
        DatagramPacket packet = null;
        int port;
        byte[] receive = new byte[65535];

        port = args.length > 0 ? Integer.parseInt(args[1]) : 8000;
        System.out.println("Server is running on port: " + port);
            socket = new DatagramSocket(port);


        packet = null;

        while(true)
        {
                packet = new DatagramPacket(receive, receive.length);
                
                socket.receive(packet);

                //Try to print out the message to confirm
                System.out.println("Client:-"+unpackMessage(receive));

                //Exit if the server if the client sends "bye"
                if(unpackMessage(receive).toString().equals("bye"))
                {
                    System.out.println("Ending connection...bye");
                    socket.close();
                    break;
                }
                receive = new byte[65535]; // Reset the receive buffer
        }

    }

    private static StringBuilder unpackMessage(byte[] data)
    {
        // This method will convert the byte array into a string
        if(data == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(data.length > 0 && data[i] != 0)
        {
            sb.append((char)data[i]);
            i++;
        }
        return sb;
    }
}