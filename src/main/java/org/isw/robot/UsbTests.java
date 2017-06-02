package org.isw.robot;

import jssc.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class UsbTests {

    private static String[] PORT_NAMES;

    public static void main(String[] args) {
        PORT_NAMES = SerialPortList.getPortNames();
        UsbTests usbTests = new UsbTests();
        usbTests.readPorts();
        usbTests.readFromUsb();

    }

    private void readPorts() {

        if (PORT_NAMES.length == 0) {
            System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
            System.out.println("Press Enter to exit...");
            try {
                System.in.read();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }

        for (int i = 0; i < PORT_NAMES.length; i++){
            System.out.println(PORT_NAMES[i]);
        }
    }
    private void readFromUsb(){
        SerialPort serialPort = new SerialPort(PORT_NAMES[0]);
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(9600, 8, 1, 0);//Set params.
            byte[] buffer = serialPort.readBytes(4);//Read 4 bytes from serial port
            String str = new String(buffer, StandardCharsets.UTF_8);
            System.out.print(str);
            serialPort.closePort();//Close serial port
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }

    }
}


