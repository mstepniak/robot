package org.isw.robot;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */

import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

class UDPServer {
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(1234);
        byte[] receiveData = new byte[48];
        byte[] sendData = new byte[48];

        double dxCord;
        double dyCord;
        double dzCord;
        double drxCord;
        double dryCord;
        double drzCord;
        String timeStamp;
        String message;

        PrintWriter out = new PrintWriter("C:\\Users\\MichalS\\Desktop\\data.txt");

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            //timeStamp = new SimpleDateFormat("yyyyMMdd.HHmmss.S").format(Calendar.getInstance().getTime());
            timeStamp = MicroTimestamp.INSTANCE.getMicro();

            String sentence = new String(receivePacket.getData());

            dxCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 0, 8)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dyCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 8, 16)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dzCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 16, 24)).order(ByteOrder.BIG_ENDIAN).getDouble();
            drxCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 24, 32)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dryCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 32, 40)).order(ByteOrder.BIG_ENDIAN).getDouble();
            drzCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 40, 48)).order(ByteOrder.BIG_ENDIAN).getDouble();

            message = timeStamp + ", " + dxCord + ", " + dyCord + ", " + dzCord + ", " + drxCord + ", " + dryCord + ", " + drzCord;
            out.println(message);
            out.flush();

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }
}
