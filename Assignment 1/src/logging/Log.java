package logging;

/**
 * Created by Johanna on 2016-02-03.
 */
public class Log {

    private static boolean loggingIsOn = true;

    public static void Information(String str, Object... objects){
        if(loggingIsOn)
            System.out.println(String.format(str, objects));
    }
}
