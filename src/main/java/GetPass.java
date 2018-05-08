import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sbt-kretov-aa on 04.05.2018.
 */
public class GetPass {

    public static String BASE_URL;
    public static String USERNAME;
    public static String PASSWORD;
    public static String ENCODING;
    public static String STATUS_PATH;
    public static String ENCODING_STATUS;
    public static String PAGE_ID;


    static String path = System.getProperty("user.name");

    public static void getCredentials() throws IOException {

        FileInputStream fis;
        Properties prop = new Properties();

        try {

            fis = new FileInputStream("C:\\Users\\" + path + "\\confluence.properties");
            prop.load(fis);

    BASE_URL = prop.getProperty("BASE_URL");
    USERNAME = prop.getProperty("USERNAME");
    PASSWORD = prop.getProperty("PASSWORD");
    ENCODING = prop.getProperty("ENCODING");
    STATUS_PATH = prop.getProperty("STATUS_PATH");
    ENCODING_STATUS = prop.getProperty("ENCODING_STATUS");
    PAGE_ID = prop.getProperty("PAGE_ID");


        } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
    }

        System.out.println(BASE_URL + " " + USERNAME + " " + PASSWORD + " " + ENCODING);
        System.out.println(path);


    }

}
