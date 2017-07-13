package org.isw.robot;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class Raspberry {

    private static final byte ADDRESS = 0x68;
    private static final byte GYRO_XOUT_H_ADD = 0x43;
    private static final byte GYRO_XOUT_L_ADD = 0x44;
    private static final byte GYRO_YOUT_H_ADD = 0x45;
    private static final byte GYRO_YOUT_L_ADD = 0x46;
    private static final byte GYRO_ZOUT_H_ADD = 0x47;
    private static final byte GYRO_ZOUT_L_ADD = 0x48;
    private static final Logger SENSOR_LOGGER = Logger.getLogger("sensorLog");

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
        //int test;
        //short temperature;
        String message;
        PropertyConfigurator.configure("log4j.properties");

        //System.out.println("dupa");
        final I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        I2CDevice device = bus.getDevice(ADDRESS);

        //test = device.read(0x1A);
        //System.out.print(String.format("0x%02X", test));

        while (true){
            System.out.println("before reading" + MicroTimestamp.INSTANCE.getMicro());
            gyroXHigh = device.read(GYRO_XOUT_H_ADD);
            System.out.println(MicroTimestamp.INSTANCE.getMicro());
            gyroXLow = device.read(GYRO_XOUT_L_ADD);
            System.out.println(MicroTimestamp.INSTANCE.getMicro());
            gyroYHigh = device.read(GYRO_YOUT_H_ADD);
            System.out.println(MicroTimestamp.INSTANCE.getMicro());
            gyroYLow = device.read(GYRO_YOUT_L_ADD);
            System.out.println(MicroTimestamp.INSTANCE.getMicro());
            gyroZHigh = device.read(GYRO_ZOUT_H_ADD);
            System.out.println(MicroTimestamp.INSTANCE.getMicro());
            gyroZLow = device.read(GYRO_ZOUT_L_ADD);
            System.out.println("last" + MicroTimestamp.INSTANCE.getMicro());

            xTotal = Integer.valueOf(gyroXHigh + "" + gyroXLow,16).shortValue();
            yTotal = Integer.valueOf(gyroYHigh + "" + gyroYLow,16).shortValue();
            zTotal = Integer.valueOf(gyroZHigh + "" + gyroZLow,16).shortValue();
            message = MicroTimestamp.INSTANCE.getMicro() + ", " + xTotal + ", " + yTotal + ", " + zTotal;
            //message = MicroTimestamp.INSTANCE.getMicro() + ", " + xTotal;

            SENSOR_LOGGER.debug(message);
            Thread.sleep(1000);
/**
            //temperature = Integer.valueOf(device.read(0x41) + "" + device.read(0x42),16).shortValue();
            //temperature =  (short) Integer.parseInt(device.read(0x41) + "" + device.read(0x42),16);
            //System.out.println((temperature/340)+36.53);

            //System.out.println("before printing" + MicroTimestamp.INSTANCE.getMicro());
            System.out.print("X: ");
            //System.out.print(String.format("0x%02X", gyroXHigh));
            System.out.println(xTotal);
            //System.out.println(String.format("0x%02X", gyroXLow));
            System.out.print("Y: ");
            //System.out.print(String.format("0x%02X", gyroYHigh));
            System.out.println(yTotal);
            //System.out.println(String.format("0x%02X", gyroYLow));
            System.out.print("Z: ");
            //System.out.print(String.format("0x%02X", gyroZHigh));
            System.out.println(zTotal);
            //System.out.println(String.format("0x%02X", gyroZLow));
            System.out.println("");
            //System.out.println("after printing" + MicroTimestamp.INSTANCE.getMicro());
            //System.out.println(Integer.valueOf(gyroXHigh + "" + gyroXLow,16).shortValue());

            Thread.sleep(250);
 */
        }
    }
}
