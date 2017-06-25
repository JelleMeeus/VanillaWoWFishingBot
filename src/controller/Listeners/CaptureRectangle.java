package controller.Listeners;

import model.Tools.ScreenCaptureWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by jelle on 24-6-2017.
 */
public class CaptureRectangle {
    private CaptureRectangle(){}

    private static CaptureRectangle uniqueInstance;
    public static CaptureRectangle getInstance()
    {
        if (uniqueInstance == null)
        {
            uniqueInstance = new CaptureRectangle();
        }
        return uniqueInstance;
    }
    private BufferedImage screenShot;
    public BufferedImage getScreenShot() {
        return screenShot;
    }

    public Rectangle getResult()
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
        screenShot = screen;
        final ScreenCaptureWindow screenCaptureWindow = new ScreenCaptureWindow(screen);
        result = screenCaptureWindow.getCaptureRect();
        return result;
    }
}
