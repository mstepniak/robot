package org.isw.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class Measurement extends Thread {

    private Thread t;
    private String threadName;
    private int measurementLength;
    //private final String serverAddress = "141.58.102.110";
    //private final String serverAddress = "localhost";
    private final String serverAddress = "169.254.58.190";

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

        Socket socket = new Socket(serverAddress, 1004); //5001 or 9090 for localhost
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
    }
}