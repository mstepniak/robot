package org.isw.robot;


import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class MainForTests {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        Thread.sleep(1);
        long endTime = System.nanoTime();

        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println((endTime- startTime ));

        System.out.println((long)2000000 - (endTime- startTime));
    }
/**
    private static final byte ADDRESS = 0x68;
    private static final byte GYRO_XOUT_H_ADD = 0x43;
    private static final byte GYRO_XOUT_L_ADD = 0x44;
    private static final byte GYRO_YOUT_H_ADD = 0x45;
    private static final byte GYRO_YOUT_L_ADD = 0x46;
    private static final byte GYRO_ZOUT_H_ADD = 0x47;
    private static final byte GYRO_ZOUT_L_ADD = 0x48;

    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
        int gyroXHigh;
        int gyroXLow;
        int gyroYHigh;
        int gyroYLow;
        int gyroZHigh;
        int gyroZLow;
        int xTotal;
        int yTotal;
        int zTotal;
        String message;
        long timeStamp = System.nanoTime();
        //
        //String milliSeconds;
        //SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

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
            timeStamp = System.nanoTime();
            gyroXHigh = device.read(GYRO_XOUT_H_ADD);
            gyroXLow = device.read(GYRO_XOUT_L_ADD);
            gyroYHigh = device.read(GYRO_YOUT_H_ADD);
            gyroYLow = device.read(GYRO_YOUT_L_ADD);
            gyroZHigh = device.read(GYRO_ZOUT_H_ADD);
            gyroZLow = device.read(GYRO_ZOUT_L_ADD);
            System.out.println(System.nanoTime()-timeStamp);

            ///xTotal = Integer.valueOf(gyroXHigh + "" + gyroXLow, 16).shortValue();
            //yTotal = Integer.valueOf(gyroYHigh + "" + gyroYLow, 16).shortValue();
            //zTotal = Integer.valueOf(gyroZHigh + "" + gyroZLow, 16).shortValue();

            //message = xTotal + ", " + yTotal + ", " + zTotal;
            //System.out.println(message);

        }
    }
*/
 }