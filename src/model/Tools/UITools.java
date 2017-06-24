package model.Tools;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jelle on 23-6-2017.
 */
public class UITools {
    private static UITools ourInstance = new UITools();
    public static UITools getInstance() {
        return ourInstance;
    }

    private UITools() {
    }

    public static String getTimeStamp()
    {
        return String.format("%s ", new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
    }
    public static void writeToConsoleWithTS(JTextArea consoleOutput,String s)
    {
        consoleOutput.append(getTimeStamp());
        consoleOutput.append(s);
        consoleOutput.append("\n");
    }
}
