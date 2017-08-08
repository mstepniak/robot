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
    /**
     * public static void main(String[] args) throws InterruptedException {
     * long startTime = System.nanoTime();
     * Thread.sleep(1);
     * long endTime = System.nanoTime();
     * <p>
     * System.out.println(startTime);
     * System.out.println(endTime);
     * System.out.println((endTime- startTime ));
     * <p>
     * System.out.println((long)2000000 - (endTime- startTime));
     * }
     */
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
        double xTotal;
        double yTotal;
        double zTotal;
        String message;
        long timeStamp = System.nanoTime();
        System.out.println(timeStamp);
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

        double xCalibrate = (Integer.valueOf(decimal2Hex(gyroXHigh) + "" + decimal2Hex(gyroXLow), 16).shortValue()/145.6);
        double yCalibrate = (Integer.valueOf(decimal2Hex(gyroYHigh) + "" + decimal2Hex(gyroYLow), 16).shortValue()/145.6);
        double zCalibrate = (Integer.valueOf(decimal2Hex(gyroZHigh) + "" + decimal2Hex(gyroZLow), 16).shortValue()/145.6);

        double xValues = 0;
        double yValues = 0;
        double zValues = 0;

        int xinfTominuszero30 = 0;
        int xminusZero20Tominuszero20 = 0;
        int xminuszero20Tominuszero10 = 0;
        int xminuszero10Tozero = 0;
        int xzeroTozero10 = 0;
        int xzero10Tozero20 = 0;
        int xzero20Tozero30 = 0;
        int xzero30Toinf = 0;

        int yinfTominuszero30 = 0;
        int yminusZero20Tominuszero20 = 0;
        int yminuszero20Tominuszero10 = 0;
        int yminuszero10Tozero = 0;
        int yzeroTozero10 = 0;
        int yzero10Tozero20 = 0;
        int yzero20Tozero30 = 0;
        int yzero30Toinf = 0;

        int zinfTominuszero30 = 0;
        int zminusZero20Tominuszero20 = 0;
        int zminuszero20Tominuszero10 = 0;
        int zminuszero10Tozero = 0;
        int zzeroTozero10 = 0;
        int zzero10Tozero20 = 0;
        int zzero20Tozero30 = 0;
        int zzero30Toinf = 0;

        for (int i=0;i<10000;i++) {
            gyroXHigh = device.read(GYRO_XOUT_H_ADD);
            gyroXLow = device.read(GYRO_XOUT_L_ADD);
            gyroYHigh = device.read(GYRO_YOUT_H_ADD);
            gyroYLow = device.read(GYRO_YOUT_L_ADD);
            gyroZHigh = device.read(GYRO_ZOUT_H_ADD);
            gyroZLow = device.read(GYRO_ZOUT_L_ADD);

            xValues =  ((Integer.valueOf(decimal2Hex(gyroXHigh) + "" + decimal2Hex(gyroXLow), 16).shortValue()/145.6) - xCalibrate);
            yValues =  ((Integer.valueOf(decimal2Hex(gyroYHigh) + "" + decimal2Hex(gyroYLow), 16).shortValue()/145.6) - yCalibrate);
            zValues =  ((Integer.valueOf(decimal2Hex(gyroZHigh) + "" + decimal2Hex(gyroZLow), 16).shortValue()/145.6) - zCalibrate);
            if (xValues<-0.3){
                xinfTominuszero30++;
            } else if (xValues<-0.2) {
                xminusZero20Tominuszero20++;
            } else if (xValues < -0.1) {
                xminuszero20Tominuszero10++;
            } else if (xValues < 0) {
                xminuszero10Tozero++;
            } else if (xValues < 0.1) {
                xzeroTozero10++;
            } else if (xValues < 0.2) {
                xzero10Tozero20++;
            } else if (xValues < 0.3) {
                xzero20Tozero30++;
            } else {
                xzero30Toinf++;
            }

            if (yValues<-0.3){
                yinfTominuszero30++;
            } else if (yValues<-0.2) {
                yminusZero20Tominuszero20++;
            } else if (yValues < -0.1) {
                yminuszero20Tominuszero10++;
            } else if (yValues < 0) {
                yminuszero10Tozero++;
            } else if (yValues < 0.1) {
                yzeroTozero10++;
            } else if (yValues < 0.2) {
                yzero10Tozero20++;
            } else if (yValues < 0.3) {
                yzero20Tozero30++;
            } else {
                yzero30Toinf++;
            }

            if (zValues<-0.4){
                zinfTominuszero30++;
            } else if (zValues<-0.2) {
                zminusZero20Tominuszero20++;
            } else if (zValues < -0.1) {
                zminuszero20Tominuszero10++;
            } else if (zValues < 0) {
                zminuszero10Tozero++;
            } else if (zValues < 0.1) {
                zzeroTozero10++;
            } else if (zValues < 0.2) {
                zzero10Tozero20++;
            } else if (zValues < 0.4) {
                zzero20Tozero30++;
            } else {
                zzero30Toinf++;
            }

        }
        System.out.println("X:");
        System.out.println("xinfTominuszero30: " + xinfTominuszero30);
        System.out.println("-20Tominuszero30: " + xminusZero20Tominuszero20);
        System.out.println("xminuszero20Tominuszero10: " + xminuszero20Tominuszero10);
        System.out.println("xminuszero10Tozero: " + xminuszero10Tozero);
        System.out.println("xzeroTozero10: " + xzeroTozero10);
        System.out.println("xzero10Tozero20: " + xzero10Tozero20);
        System.out.println("xzero20Tozero30: " + xzero20Tozero30);
        System.out.println("xzero30Toinf: " + xzero30Toinf);
        System.out.println("Y:");
        System.out.println("xinfTominuszero30: " + yinfTominuszero30);
        System.out.println("-20Tominuszero30: " + yminusZero20Tominuszero20);
        System.out.println("xminuszero20Tominuszero10: " + yminuszero20Tominuszero10);
        System.out.println("xminuszero10Tozero: " + yminuszero10Tozero);
        System.out.println("xzeroTozero10: " + yzeroTozero10);
        System.out.println("xzero10Tozero20: " + yzero10Tozero20);
        System.out.println("xzero20Tozero30: " + yzero20Tozero30);
        System.out.println("xzero30Toinf: " + yzero30Toinf);
        System.out.println("Z:");
        System.out.println("xinfTominuszero30: " + zinfTominuszero30);
        System.out.println("-20Tominuszero30: " + zminusZero20Tominuszero20);
        System.out.println("xminuszero20Tominuszero10: " + zminuszero20Tominuszero10);
        System.out.println("xminuszero10Tozero: " + zminuszero10Tozero);
        System.out.println("xzeroTozero10: " + zzeroTozero10);
        System.out.println("xzero10Tozero20: " + zzero10Tozero20);
        System.out.println("xzero20Tozero30: " + zzero20Tozero30);
        System.out.println("xzero30Toinf: " + zzero30Toinf);

/**
        while (true) {
            gyroXHigh = device.read(GYRO_XOUT_H_ADD);
            gyroXLow = device.read(GYRO_XOUT_L_ADD);
            gyroYHigh = device.read(GYRO_YOUT_H_ADD);
            gyroYLow = device.read(GYRO_YOUT_L_ADD);
            gyroZHigh = device.read(GYRO_ZOUT_H_ADD);
            gyroZLow = device.read(GYRO_ZOUT_L_ADD);

            xTotal = (Integer.valueOf(decimal2Hex(gyroXHigh) + "" + decimal2Hex(gyroXLow), 16).shortValue()/145.6) - xCalibrate;
            yTotal = (Integer.valueOf(decimal2Hex(gyroYHigh) + "" + decimal2Hex(gyroYLow), 16).shortValue()/145.6) - yCalibrate;
            zTotal = (Integer.valueOf(decimal2Hex(gyroZHigh) + "" + decimal2Hex(gyroZLow), 16).shortValue()/145.6) - zCalibrate;

            message = xTotal + ", " + yTotal + ", " + zTotal;
            System.out.println(message);
            Thread.sleep(100);
        }
 */
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
}