package model.Tools;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by jelle on 22-6-2017.
 */
public interface CaptureRectangle {
    public static Rectangle getResult()
    {
        Rectangle result;
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final BufferedImage screen = robot.createScreenCapture(new Rectangle(screenSize));
        final ScreenCaptureWindow screenCaptureWindow = new ScreenCaptureWindow(screen);
        result = screenCaptureWindow.getCaptureRect();
        return result;
    }
}
