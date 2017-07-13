package org.isw.robot;


import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class I2C {

    private static final byte ADDRESS = 0x68;
    private static final byte GYRO_XOUT_H_ADD = 0x43;
    private static final byte GYRO_XOUT_L_ADD = 0x44;
    private static final byte GYRO_YOUT_H_ADD = 0x45;
    private static final byte GYRO_YOUT_L_ADD = 0x46;
    private static final byte GYRO_ZOUT_H_ADD = 0x47;
    private static final byte GYRO_ZOUT_L_ADD = 0x48;

    private void spiReceiver() throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
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
        String timeStamp;
        //
        //String milliSeconds;
        //SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        PropertyConfigurator.configure("log4j.properties");

        final I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        com.pi4j.io.i2c.I2CDevice device = bus.getDevice(ADDRESS);


        while (true) {
            gyroXHigh = device.read(GYRO_XOUT_H_ADD);
            gyroXLow = device.read(GYRO_XOUT_L_ADD);
            gyroYHigh = device.read(GYRO_YOUT_H_ADD);
            gyroYLow = device.read(GYRO_YOUT_L_ADD);
            gyroZHigh = device.read(GYRO_ZOUT_H_ADD);
            gyroZLow = device.read(GYRO_ZOUT_L_ADD);
            timeStamp = MicroTimestamp.INSTANCE.getMicro();
            //milliSeconds = dateFormat.format(System.currentTimeMillis());

            xTotal = Integer.valueOf(gyroXHigh + "" + gyroXLow, 16).shortValue();
            yTotal = Integer.valueOf(gyroYHigh + "" + gyroYLow, 16).shortValue();
            zTotal = Integer.valueOf(gyroZHigh + "" + gyroZLow, 16).shortValue();

            message = timeStamp + ", " + xTotal + ", " + yTotal + ", " + zTotal;
 //           if (isActive()) {
  //              SENSOR_LOGGER.debug(message);
   //         }
            //Thread.sleep(250);
        }
    }
}