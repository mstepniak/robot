package org.isw.robot;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */

import java.text.SimpleDateFormat;

/**
 * Class to generate timestamps with microsecond precision
 * For example: MicroTimestamp.INSTANCE.getMicro() = "2012-10-21 19:13:45.267128"
 */
public enum MicroTimestamp {
    INSTANCE;

    private long startDate;
    private long startNanoseconds;
    private SimpleDateFormat dateFormat;

    private MicroTimestamp() {
        this.startDate = System.currentTimeMillis();
        this.startNanoseconds = System.nanoTime();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public String getMicro() {
        long microSeconds = (System.nanoTime() - this.startNanoseconds) / 1000;
        long date = this.startDate + (microSeconds / 1000);
        return this.dateFormat.format(date) + String.format("%03d", microSeconds % 1000);
    }
}
