package org.isw.robot;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

class UDPServer {
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(1234);
        byte[] receiveData = new byte[48];
        byte[] sendData = new byte[48];
        /**
        byte[] xCord = new byte[8];
        byte[] yCord = new byte[8];
        byte[] zCord = new byte[8];
        byte[] rxCord = new byte[8];
        byte[] ryCord = new byte[8];
        byte[] rzCord = new byte[8];
*/
        double dxCord;
        double dyCord;
        double dzCord;
        double drxCord;
        double dryCord;
        double drzCord;

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            long millis = System.currentTimeMillis() %1000;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

            String sentence = new String(receivePacket.getData());
            //System.out.println(Arrays.toString(receiveData));

            dxCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 0, 8)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dyCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 8, 16)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dzCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 16, 24)).order(ByteOrder.BIG_ENDIAN).getDouble();
            drxCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 24, 32)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dryCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 32, 40)).order(ByteOrder.BIG_ENDIAN).getDouble();
            drzCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 40, 48)).order(ByteOrder.BIG_ENDIAN).getDouble();

            System.out.println(timeStamp + ":" + millis + "," +  dxCord + "," + dyCord + "," + dzCord + "," + drxCord+ "," + dryCord+ "," + drzCord);
            /**
            xCord = Arrays.copyOfRange(receiveData, 0, 8);
            yCord = Arrays.copyOfRange(receiveData, 8, 16);
            zCord = Arrays.copyOfRange(receiveData, 16, 24);
            rxCord = Arrays.copyOfRange(receiveData, 24, 32);
            ryCord = Arrays.copyOfRange(receiveData, 32, 40);
            rzCord = Arrays.copyOfRange(receiveData, 40, 48);


            System.out.println(Arrays.toString(xCord));
            System.out.println(Arrays.toString(yCord));
            System.out.println(Arrays.toString(zCord));
            System.out.println(Arrays.toString(rxCord));
            System.out.println(Arrays.toString(ryCord));
            System.out.println(Arrays.toString(rzCord));

            System.out.println(ByteBuffer.wrap(xCord).order(ByteOrder.BIG_ENDIAN).getDouble());
            System.out.println(ByteBuffer.wrap(yCord).order(ByteOrder.BIG_ENDIAN).getDouble());
            System.out.println(ByteBuffer.wrap(zCord).order(ByteOrder.BIG_ENDIAN).getDouble());
            System.out.println(ByteBuffer.wrap(rxCord).order(ByteOrder.BIG_ENDIAN).getDouble());
            System.out.println(ByteBuffer.wrap(ryCord).order(ByteOrder.BIG_ENDIAN).getDouble());
            System.out.println(ByteBuffer.wrap(rzCord ).order(ByteOrder.BIG_ENDIAN).getDouble());
             */
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
