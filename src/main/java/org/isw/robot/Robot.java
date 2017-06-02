package org.isw.robot;

import java.io.File;
import java.util.Scanner;

/**
 * Author MichalS.
 * Mail: michas1991@gmail.com
 */
public class Robot {

    public static void main(String[] args) {
        final Robot robot = new Robot();
        try{
            robot.doChosenAction(args[0]);
        } catch (Exception e) {
            //System.out.print(e);
            robot.printHelp();
        }
    }

    private void doChosenAction(String arg1) {

        switch (arg1){
            case "MEASURE": {
                System.out.println("measuring");
                Scanner reader = new Scanner(System.in);  // Reading from System.in
                System.out.println("Enter a number: ");
                int length = reader.nextInt(); // Scans the next token of the input as an int.
                //Measurement T1 = new Measurement("Robot", length);
                Measurement T2 = new Measurement("Sensor", length);
                //T1.start();
                T2.start();
                break;
            }
            case "FILES": {
                File folder = new File("C:\\Users\\MichalS\\Desktop\\magisterka\\architecture");
                File[] listOfFiles = folder.listFiles();

                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        System.out.println("File " + listOfFiles[i].getName());
                    } else if (listOfFiles[i].isDirectory()) {
                        System.out.println("Directory " + listOfFiles[i].getName());
                    }
                }
                break;
            }
            case "LAST_RESULT": {
                File folder = new File("C:\\Users\\MichalS\\Desktop\\magisterka\\architecture");
                File[] listOfFiles = folder.listFiles();

                //do printing of file
                break;
            }
            default: {
                printHelp();
                break;
            }
        }
    }

    private void printHelp(){
        //print help
        System.out.print("this is help");
    }
}
