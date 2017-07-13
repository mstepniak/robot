package org.isw.robot;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class Management {
    public static void main(String[] args) {
        try{
            MeasureServer T1 = new MeasureServer("Robot");
            MeasureServer T2 = new MeasureServer("Sensor");
            T1.start();
            T2.start();
        } catch (Exception e)
        {
            //do something
        }
    }
}
