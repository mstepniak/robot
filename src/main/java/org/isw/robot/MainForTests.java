package org.isw.robot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class MainForTests {

    public static void main(String[] args) {
        long millis = System.currentTimeMillis() %1000;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        System.out.println("from robot " + timeStamp + ":" + millis);
    }
}
