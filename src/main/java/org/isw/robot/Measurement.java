package org.isw.robot;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class Measurement extends Thread {

    private Thread t;
    private String threadName;
    private int measurementLength;
    private final String serverAddress = "localhost";


    Measurement(String name, int length) {
        threadName = name;
        measurementLength = length;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        if (threadName.equalsIgnoreCase("Robot")) {
            try {
                dataFromRobot(measurementLength);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            dataFromSensor(measurementLength);
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

    private void dataFromRobot(int length) throws IOException {
        /*
        long millis = System.currentTimeMillis() %1000;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        System.out.println("from robot " + timeStamp + ":" + millis);
        */

        Socket socket = new Socket(serverAddress, 9090);
        BufferedReader input =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String answer = input.readLine();
        System.out.println("answer from socket: " + answer);
    }

    private void dataFromSensor(int length) {
        /*
        long millis = System.currentTimeMillis() % 1000;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        System.out.println("from sensor " + timeStamp + ":" + millis);
        */

        String[] ports = SerialPortList.getPortNames();
        SerialPort serialPort = new SerialPort(ports[0]);

        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(9600, 8, 1, 0);//Set params.
            byte[] buffer = serialPort.readBytes(4);//Read 4 bytes from serial port
            String str = new String(buffer, StandardCharsets.UTF_8);
            System.out.print(str);
            serialPort.closePort();//Close serial port
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
}