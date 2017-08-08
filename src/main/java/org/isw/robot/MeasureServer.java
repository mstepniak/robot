package org.isw.robot;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class MeasureServer extends Thread {

    private static final byte ADDRESS = 0x68;
    private static final byte GYRO_XOUT_H_ADD = 0x43;
    private static final byte GYRO_XOUT_L_ADD = 0x44;
    private static final byte GYRO_YOUT_H_ADD = 0x45;
    private static final byte GYRO_YOUT_L_ADD = 0x46;
    private static final byte GYRO_ZOUT_H_ADD = 0x47;
    private static final byte GYRO_ZOUT_L_ADD = 0x48;
    private static final int DURATION = 1;
    private static final Logger ROBOT_LOGGER = Logger.getLogger("robotLog");
    private static final Logger SENSOR_LOGGER = Logger.getLogger("sensorLog");
    private static volatile long activatedAt = Long.MAX_VALUE;
    private Thread t;
    private String threadName;

    MeasureServer(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }

    private static boolean isActive() {
        long activeFor = System.currentTimeMillis() - activatedAt;

        return activeFor >= 0 && activeFor <= DURATION;
    }

    private static String decimal2Hex(int d) {
        String digits = "0123456789ABCDEF";
        if (d == 0) return "0";
        String hex = "";
        while (d > 0) {
            int digit = d % 16;                // rightmost digit
            hex = digits.charAt(digit) + hex;  // string concatenation
            d = d / 16;
        }
        return hex;
    }

    public void run() {
        System.out.println("Running " + threadName);
        if (threadName.equalsIgnoreCase("Robot")) {
            try {
                udpServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                i2CReceiver();
            } catch (InterruptedException | I2CFactory.UnsupportedBusNumberException | IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    private void activate() {
        activatedAt = System.currentTimeMillis();
    }

    private void udpServer() throws IOException {
        PropertyConfigurator.configure("log4j.properties");

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

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            //starting of generation of sensor logs
            activate();
            timeStamp = MicroTimestamp.INSTANCE.getMicro();

            String sentence = new String(receivePacket.getData());
            dxCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 0, 8)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dyCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 8, 16)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dzCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 16, 24)).order(ByteOrder.BIG_ENDIAN).getDouble();
            drxCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 24, 32)).order(ByteOrder.BIG_ENDIAN).getDouble();
            dryCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 32, 40)).order(ByteOrder.BIG_ENDIAN).getDouble();
            drzCord = ByteBuffer.wrap(Arrays.copyOfRange(receiveData, 40, 48)).order(ByteOrder.BIG_ENDIAN).getDouble();

            message = timeStamp + " " + dxCord + " " + dyCord + " " + dzCord + " " + drxCord + " " + dryCord + " " + drzCord;
            ROBOT_LOGGER.debug(message);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }

    private void i2CReceiver() throws InterruptedException, IOException, I2CFactory.UnsupportedBusNumberException {
        int gyroXHigh;
        int gyroXLow;
        int gyroYHigh;
        int gyroYLow;
        int gyroZHigh;
        int gyroZLow;
        double xTotal;
        double yTotal;
        double zTotal;
        String message;
        String timeStamp = MicroTimestamp.INSTANCE.getMicro();

        final I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        I2CDevice device = bus.getDevice(ADDRESS);
        gyroXHigh = device.read(GYRO_XOUT_H_ADD);
        gyroXLow = device.read(GYRO_XOUT_L_ADD);
        gyroYHigh = device.read(GYRO_YOUT_H_ADD);
        gyroYLow = device.read(GYRO_YOUT_L_ADD);
        gyroZHigh = device.read(GYRO_ZOUT_H_ADD);
        gyroZLow = device.read(GYRO_ZOUT_L_ADD);

        Thread.sleep(5000);

        while (true) {

            while (isActive()) {

                timeStamp = MicroTimestamp.INSTANCE.getMicro();
                gyroXHigh = device.read(GYRO_XOUT_H_ADD);
                gyroXLow = device.read(GYRO_XOUT_L_ADD);
                gyroYHigh = device.read(GYRO_YOUT_H_ADD);
                gyroYLow = device.read(GYRO_YOUT_L_ADD);
                gyroZHigh = device.read(GYRO_ZOUT_H_ADD);
                gyroZLow = device.read(GYRO_ZOUT_L_ADD);

                xTotal = (Integer.valueOf(decimal2Hex(gyroXHigh) + "" + decimal2Hex(gyroXLow), 16).shortValue()/145.6);
                yTotal = (Integer.valueOf(decimal2Hex(gyroYHigh) + "" + decimal2Hex(gyroYLow), 16).shortValue()/145.6);
                zTotal = (Integer.valueOf(decimal2Hex(gyroZHigh) + "" + decimal2Hex(gyroZLow), 16).shortValue()/145.6);

                message = timeStamp + " " + xTotal + " " + yTotal + " " + zTotal;

                SENSOR_LOGGER.debug(message);
           }
        }
    }
}
