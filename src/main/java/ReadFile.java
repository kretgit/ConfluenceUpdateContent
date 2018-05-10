/**
 * Created by sbt-kretov-aa on 03.05.2018.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class ReadFile {


    //static String nameOfTXTFile = "C://Users//SBT-Kretov-AA//Downloads//status.txt";
    static String nameOfTXTFile = GetPass.STATUS_PATH; //place where you should put body-file
    static String textFromTXTFile = "";

    public static void readFile () {


//convert from text file to string
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            //new FileInputStream(nameOfTXTFile), "utf-8")); //windows-1251,cp1251
                            new FileInputStream(nameOfTXTFile), GetPass.ENCODING_STATUS)); //UTF8

            String c;
            while ((c = br.readLine() ) != null) {

                textFromTXTFile += c + "\n";
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(textFromTXTFile);



    }

}
