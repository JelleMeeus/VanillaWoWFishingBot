package model;

import model.Tools.Lang;
import model.Tools.UITools;
import org.w3c.dom.css.Rect;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;


/**
 * Created by jelle on 23-6-2017.
 */
public class FisherMan implements Runnable {
    private Lure lure; //holds a Lure object
    private int lureAmount; // amount of lure in bags
    private boolean callibrated;
    private boolean interrupted;
    private Rectangle tooltipRegion; // region where to find tooltip
    private Rectangle bobberRegion; // region where to find bobber
    private JTextArea consoleTextArea;
    private boolean useLure;
    private Long lureLastApplied;
    Point pntTooltipLocation;
    private double scanSpeedProperty;
    private double sensitivityProperty;

    public void setTYPE_DELAY(long TYPE_DELAY) {
        this.TYPE_DELAY = TYPE_DELAY;
    }

    private long TYPE_DELAY;

    public void setCPU_DELAY_VOLUME(long CPU_DELAY_VOLUME) {
        this.CPU_DELAY_VOLUME = CPU_DELAY_VOLUME;
    }

    private long CPU_DELAY_VOLUME;

    public void setVolumeValue(double volumeValue) {
        this.volumeValue = volumeValue;
    }

    private double volumeValue;

    private Rectangle volumeRegion;
    private boolean useVolumeDetection;

    public void setUseVolumeDetection(boolean useVolumeDetection) {
        this.useVolumeDetection = useVolumeDetection;
    }

    private double x_steps;
    private double y_steps;

    public void setX_steps(double x_steps) {
        this.x_steps = x_steps;
    }

    public void setY_steps(double y_steps) {
        this.y_steps = y_steps;
    }

    public void setLureLastApplied(Long lureLastApplied) {
        this.lureLastApplied = lureLastApplied;
    }

    public void setScanSpeedProperty(double scanSpeedProperty) {
        this.scanSpeedProperty = scanSpeedProperty;
    }

    private boolean debugMode;

    private static DisplayMode USER_MAIN_DISPLAY =
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    private Robot robot;
    private final int CONTROL_RESOLUTION = 1920 * 1080;
    private final long LURE_SLEEP = 7500;

    public FisherMan()
    {
        //generates default FisherMan object
        interrupted = false;
        callibrated = false;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        /* Starts fishing process */
        try {
            try {
                fish();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public String toString() {
        return "FisherMan{" +
                "lure=" + lure +
                ", lureAmount=" + lureAmount +
                ", callibrated=" + callibrated +
                ", interrupted=" + interrupted +
                ", tooltipRegion=" + tooltipRegion +
                ", bobberRegion=" + bobberRegion +
                ", consoleTextArea=" + consoleTextArea +
                ", useLure=" + useLure +
                ", lureLastApplied=" + lureLastApplied +
                ", pntTooltipLocation=" + pntTooltipLocation +
                ", scanSpeedProperty=" + scanSpeedProperty +
                ", sensitivityProperty=" + sensitivityProperty +
                ", debugMode=" + debugMode +
                ", robot=" + robot +
                ", CONTROL_RESOLUTION=" + CONTROL_RESOLUTION +
                ", LURE_SLEEP=" + LURE_SLEEP +
                '}';
    }

    public double getScanSpeedProperty() {
        return scanSpeedProperty;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setSensitivityProperty(double sensitivityProperty) {
        if (sensitivityProperty == 0)
        {
            sensitivityProperty = 1;
        }
        this.sensitivityProperty = ( sensitivityProperty / 100.0 );
    }

    public double getSensitivityProperty() {
        return sensitivityProperty;
    }

    public void interrupt() {
        interrupted = true;
    }

    public boolean isUseLure() {
        return useLure;
    }

    public void setUseLure(boolean useLure) {
        this.useLure = useLure;
    }

    public boolean isCallibrated() {
        return callibrated;
    }

    public void setCallibrated(boolean callibrated) {
        this.callibrated = callibrated;
    }

    public Lure getLure() {
        return lure;
    }

    public void setLure(Lure lure) {
        this.lure = lure;
    }

    public int getLureAmount() {
        return lureAmount;
    }

    public void setLureAmount(int lureAmount) {
        this.lureAmount = lureAmount;
    }


    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public Rectangle getTooltipRegion() {
        return tooltipRegion;
    }

    public void setTooltipRegion(Rectangle tooltipRegion) {
        this.tooltipRegion = tooltipRegion;
    }

    public Rectangle getBobberRegion() {
        return bobberRegion;
    }

    public void setBobberRegion(Rectangle bobberRegion) {
        this.bobberRegion = bobberRegion;
    }

    public JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }

    public void setConsoleTextArea(JTextArea consoleTextArea) {
        this.consoleTextArea = consoleTextArea;
    }

    /**
     * Primary fishing loop.
     * Continue infinitely if no max time is specified.
     * Attempt to cast your fishing rod and reel in a fish if one exists.
     * Output to the console if the fishing was successful or not.
     * Sleep for a random amount of time between (BASE_SLEEP / 1000)
     * and (BASE_SLEEP / 1000) + DELAY_VARIANCE amount of seconds.
     */
    private void fish() throws InterruptedException, AWTException {
        int DELAY_VARIANCE = 2000;
        int BASE_SLEEP = 4000;
        sleep(BASE_SLEEP);
        while ( !isInterrupted() )
        {
            if ( useLure && (lureAmount==0) )
            {
                UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_MSG_LOGOUT_CONFIRM);
                interrupt();
                break;
            }
            if ( shouldApply() )
            {
                applyLure(); /* If a lure needs to be re-applied, use one. */
            }
            UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_CAST_FISHING);
            typeStr(Lang.EN_CAST_FISHING);


            if (scan_mod())
            {
                if (useVolumeDetection)
                {
                    //Todo: rewrite this ugly if statement
                    if ( reelInVolume() )
                    {
                        UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_MSG_FISH_CAUGHT);
                    }
                    else
                    {
                        UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_ERROR_SPLASH_MISSING);
                    }
                }
                else
                {
                    if ( reelIn() )
                    {
                        UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_MSG_FISH_CAUGHT);
                    }
                    else
                    {
                        UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_ERROR_SPLASH_MISSING);
                    }
                }
            }
            else
            {
                UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_ERROR_BOBBER_MISSING);
            }

            sleep(BASE_SLEEP + fluctuate((long) (DELAY_VARIANCE * Math.random())));
        }
    }

    private boolean reelInVolume()
    {
        final Point mouse = MouseInfo.getPointerInfo().getLocation();
        final long START_TS = System.currentTimeMillis(), GIVE_UP_TS = 26000;
        //final int CPU_DELAY = 25;
        /* If the user moves his mouse, then we will still have memory of the right coordinates. */
        final int MOUSE_X = mouse.x, MOUSE_Y = mouse.y;
        final int VOLUME_X = volumeRegion.x, VOLUME_Y = volumeRegion.y;

        /* As long as the in-game cast is still going, there's hope of catching the fish. */
        while (!interrupted && !timePassed(START_TS, GIVE_UP_TS))
        {
            /* Sleep to prevent max-CPU usage. */
            sleep(CPU_DELAY_VOLUME);
            /* Find the average blue where the mouse is */

            if ( colorIsVolume( robot.getPixelColor( VOLUME_X , VOLUME_Y )))
            {
                /* Shift right click to loot the fish. */
                robot.mouseMove(MOUSE_X, MOUSE_Y);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                return true;
            }
        }
        return false;
    }

    private boolean colorIsVolume(final Color c)
    {
        if ( debugMode)
            UITools.writeToConsoleWithTS(getConsoleTextArea(),
                    String.format("Needed: R: %d <60 G: %d >175 B: %d <60",
                            c.getRed(), c.getGreen(), c.getBlue()));

        return c.getRed() < 70
                && c.getGreen() > 165
                && c.getBlue() < 70;
    }

    /**
     * Search the user's display for the fishing bobber.
     * Grab the mouse and move it across a grid of x,y locations.
     * If a tooltip appears, that must mean we have hovered over
     * the fishing bobber. Calibration must be correct for this to work.
     * @return - True if the bobber was located, false if otherwise.
     */
    private boolean scan_mod()
    {
        /* Variables to help us navigate across the user's screen. */
        final int DELAY_TIME = 7000; //TODO: option in case running wow in virtual machine to increase this delay
         /* Loop through RANGE_SCALE% of the user's display. */
        final int Y_START = (int) bobberRegion.getY();
        final int Y_END = (int) (bobberRegion.getY() + bobberRegion.getHeight());
        final int X_START = (int) bobberRegion.getX();
        final int X_END = (int) (bobberRegion.getX() + bobberRegion.getWidth());
        final int X_PIX_SKIP = (int) ((X_END - X_START)/x_steps);
        final int Y_PIX_SKIP = (int) ((Y_END - Y_START)/y_steps);

        /* Reset the mouse position so we don't accidentally hover over the bobber again. */
        robot.mouseMove( (int) bobberRegion.getX(), (int) bobberRegion.getY());
        /* For users with slower computers. Their GPU needs time to load the bobber in. */
        sleep(DELAY_TIME);

        /* Loop through the center portion of the user's screen. */
        for (int y = Y_START; y < Y_END; y += Y_PIX_SKIP)
            for (int x = X_START; x < X_END; x += X_PIX_SKIP)
            {
                if (interrupted) return false;
                /* Move the mouse in hopes that we will be over the bobber. */
                robot.mouseMove(x, y);
                /* Wait for the user's GPU to load the tooltip in. */
                sleep((long) scanSpeedProperty);
                /* If the color at the tooltip location matches what colors that
                   we know the tooltip is, then we found the bobber. */
                final Color pixelColor = robot.getPixelColor(pntTooltipLocation.x, pntTooltipLocation.y);
                if (colorIsTooltip(pixelColor))
                    return true;
            }
        return false;
    }

    /**
     * Called when we know the mouse is right on-top of the fishing bobber.
     * Scan the around around the mouse cursor and find the average amount of
     * blue in a circular radius. If the blue ever changes too much from what
     * it was originally, the splash may have occured, so attempt to loot the fish.
     * @return - True if a splash was detected, false if the function gave up after GIVE_UP_TS.
     */
    private boolean reelIn()
    {
        final Point mouse = MouseInfo.getPointerInfo().getLocation();
        final long START_TS = System.currentTimeMillis(), GIVE_UP_TS = 25000;
        final int CPU_DELAY = 50;
        /* If the user moves his mouse, then we will still have memory of the right coordinates. */
        final int MOUSE_X = mouse.x, MOUSE_Y = mouse.y;

        /* Determine how much blue there WAS at the start of this cycle. */
        final double ctrlBlue = avgBlueProximity(MOUSE_X, MOUSE_Y);

        /* As long as the in-game cast is still going, there's hope of catching the fish. */
        while (!interrupted && !timePassed(START_TS, GIVE_UP_TS))
        {
            /* Sleep to prevent max-CPU usage. */
            sleep(CPU_DELAY);
            /* Find the average blue where the mouse is */
            final double avgBlue = avgBlueProximity(MOUSE_X, MOUSE_Y);
            final double diff = Math.abs(ctrlBlue - avgBlue);
            if ( debugMode)
                UITools.writeToConsoleWithTS(getConsoleTextArea(),
                        Lang.EN_DEBUG_COLOR_THRESH.replaceFirst("%1",
                                String.format("%.2f", diff))
                                .replaceFirst("%2", String.format("%.2f", sensitivityProperty)));

            /* If the difference in blue changed enough, the bobber just splashed! */
            if (Math.abs(ctrlBlue - avgBlue) >= sensitivityProperty)
            {
                /* Shift right click to loot the fish. */
                robot.mouseMove(MOUSE_X, MOUSE_Y);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                return true;
            }
        }

        return false;
    }

    /**
     * Searches in a 360 degree sweep of all nearby pixels that
     * are within `RADIUS` units. At each pixel, read the RGB
     * data and sum up only the blue of that pixel. Divide the
     * sum by the total amount of pixels visited and then return
     * the average.
     * @param centerX - X coordinate of the center of the area to check.
     * @param centerY - Y coordinate of the center of the area to check.
     * @return - The average amount of blue in the entire search area.
     */
    private double avgBlueProximity(final int centerX, final int centerY)
    {
        /* Variables which will help us find the average. */
        int blueSum = 0, pixelCount = 0;
        /* Take a screenshot so we can evaluate the state of the pixels. */
        final BufferedImage ss = screenshot();

        /* Variables to prevent out of bounds exceptions when looping across the image. */
        final int RADIUS = 75, SQ_R = RADIUS * RADIUS,
                Y_END = Math.min(ss.getHeight() - 1, centerY + RADIUS),
                X_END = Math.min(ss.getWidth() - 1, centerX + RADIUS),
                X_START = Math.max(0, centerX - RADIUS),
                Y_START = Math.max(0, centerY - RADIUS);

        for (int y = Y_START; y <= Y_END; y++)
        {
            /* Y Radius at this given Y coordinate. */
            final int Y_RAD = (y - centerY) * (y - centerY);
            for (int x = X_START; x <= X_END; x++)
            {
                /* Determine if this pixel is inside the circle. */
                if ((x - centerX) * (x - centerX) + Y_RAD <= SQ_R)
                {
                    /* AND with 0xFF will grab only the blue from this pixel. */
                    blueSum += ss.getRGB(x, y) & 0xFF;
                    pixelCount++;
                }
            }
        }
        /* Divide by zero protection. */
        return (pixelCount > 0) ? (double)blueSum / pixelCount : 0;
    }

    private void applyLure()
    {
        //TODO: test applyLure()
        //TODO: test if /use lure works
        if (isDebugMode())
        {
            UITools.writeToConsoleWithTS(getConsoleTextArea(),"Applying Lure");
        }
        typeStr(Lang.EN_USE.concat(getLure().getName()));
        UITools.writeToConsoleWithTS(getConsoleTextArea(), Lang.EN_MSG_LURE_APPLY);
        sleep(1000); //can be lowered
        typeStr(Lang.EN_GRAB_POLE);
        sleep(LURE_SLEEP);
        lureLastApplied = System.currentTimeMillis();
        if (isDebugMode())
        {
            UITools.writeToConsoleWithTS(getConsoleTextArea(),"Lure Applied");
        }
    }

    /*checks wheter or not lure should be applied*/
    /* Checks if lure should be used*/
    /* lures left > 0*/
    /*  if lure has expired */
   private boolean shouldApply()
    {
        return isUseLure()
                && (lureAmount > 0)
                && ((lureLastApplied == null)
                || timePassed(lureLastApplied + getLure().getDuration(), System.currentTimeMillis()));
    }

    /**
     * Returns whether or not it's been long enough from `start` (millis)
     * to this point in time (millis) based on `duration`.
     * @param start - Time in which the duration started.
     * @param duration - Duration of time which is being checked.
     * @return - Whether or not the time duration has passed.
     */
    private boolean timePassed(final long start, final long duration)
    {
        return System.currentTimeMillis() - start >= duration;
    }

    /**
     * Fluctuate a number randomly based on the FLUCTUATION percentage.
     * @param input - Base number.
     * @return - Fluctuated number based on the parameter.
     */
    private long fluctuate(long input)
    {
        final float FLUCTUATION = 0.15f;
        return fluctuate(input, FLUCTUATION);
    }

    /**
     * Fluctuate a number either positively or negatively based on
     * the parameter passed in for fluctuation. Fluctuation should
     * be a percentage, so a 15% fluctuation would mean 0.15f.
     * A 15% fluctuation means that an `input` of 100 could yield
     * values between 85 and 115.
     * @param input - Base number.
     * @param fluctuation - Fluctuation percentage.
     * @return - Fluctuated number.
     */
    private long fluctuate(final long input, final float fluctuation)
    {
        final boolean positive = Math.random() >= 0.5;
        return (long)(input * (1 + (positive ? 1 : -1) * Math.random() * fluctuation));
    }

    /**
     * Presses enter, pastes `output` by pressing shift + insert,
     * then presses enter again.
     * @param output - String to output.
     */
    private void typeStr(final String output)
    {
        /* Delay in-between key press and release. */
        final long PASTE_DELAY = 100; //was 25
        try
        {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable data = cb.getContents(null);
            cb.setContents(new StringSelection(output), null);
            sleep(TYPE_DELAY);
            sleep(TYPE_DELAY);
            robot.keyPress(KeyEvent.VK_ENTER);
            sleep(TYPE_DELAY);
            robot.keyRelease(KeyEvent.VK_ENTER);
            sleep(TYPE_DELAY);
            robot.keyPress(KeyEvent.VK_CONTROL);
            sleep(TYPE_DELAY);
            robot.keyPress(KeyEvent.VK_V);
            sleep(TYPE_DELAY);
            robot.keyRelease(KeyEvent.VK_V);
            sleep(TYPE_DELAY);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            sleep(TYPE_DELAY);
            /* Press enter afterwards. */
            robot.keyPress(KeyEvent.VK_ENTER);
            sleep(TYPE_DELAY);
            robot.keyRelease(KeyEvent.VK_ENTER);
            sleep(TYPE_DELAY);
            cb.setContents(data, null);
        }
        catch (IllegalStateException e)
        {
            UITools.writeToConsoleWithTS(getConsoleTextArea(),e.getMessage());
        }
    }

    /**
     * Attempts to calibrate the program by locating the Fishing Bobber tooltip.
     * If found, the point at which it was found at is saved and thus the program is calibrated.
     * @return - Whether or not the Tooltip was found.
     */
    public boolean callibrateauto(BufferedImage screenShot)
    {
        /*Coordinates upperLeft and bottomRight that define a Rectangle in which the tooltip might be*/
        Point upperLeft;
        Point bottomRight;
        upperLeft = new Point(
                (int)(getTooltipRegion().getX()),
                (int)(getTooltipRegion().getY())
        );
        bottomRight = new Point(
                (int)(getTooltipRegion().getX() + getTooltipRegion().getWidth() ),
                (int)(getTooltipRegion().getY() + getTooltipRegion().getHeight())
        );

        UITools.writeToConsoleWithTS(getConsoleTextArea(),
                String.format("Coords x %d y %d ; x %d y %d",upperLeft.x,upperLeft.y,bottomRight.x,bottomRight.y)
        );

        for (int y = upperLeft.y; y < bottomRight.y; y++)
            for (int x = upperLeft.x; x < bottomRight.x ; x++)
            {
                //System.out.println(String.format("x %d y %d",x,y));
                Color pixelColor = parseByteColor(screenShot.getRGB(x, y));
                if (colorIsTooltip(pixelColor))
                {
                    /* Set this location as the location of the Fishing Bobber tooltip. */
                    pntTooltipLocation = new Point(x, y);
                    UITools.writeToConsoleWithTS(getConsoleTextArea(),
                            String.format("TooltipLocation x %d y %d  \n %s",
                                    pntTooltipLocation.x,
                                    pntTooltipLocation.y,
                                    Lang.EN_MSG_CALIBRATE_SUCCESS)
                    );
                    setCallibrated(true);
                    return true;
                }
            }
        UITools.writeToConsoleWithTS(getConsoleTextArea(),Lang.EN_MSG_CALIBRATE_FAIL);
        setCallibrated(false);
        return false;
    }

    /**
     * Test if a given color meets the conditions for what the Fishing Bobber tooltip SHOULD have.
     * @param c - Color in which we are testing.
     * @return - Whether or not the color matches the tooltip.
     */
    private boolean colorIsTooltip(final Color c)
    {
        return c.getRed() > 200
                && c.getGreen() > 200 //was 200
                && c.getBlue() < 50;
    }

    /*Puts current thread to sleep for milliseconds ms*/
    private void sleep(final long milliseconds)
    {
        try
        {
            Thread.currentThread().sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            interrupt();
        }
    }

    /**
     * Converts a color from byte-code to a Color object.
     * @param color - Byte code of RGBA.
     * @return - Color object.
     */
    private Color parseByteColor(final int color)
    {
        final int alpha = (color >>> 24) & 0xFF;
        final int red = (color >>> 16) & 0xFF;
        final int green = (color >>> 8) & 0xFF;
        final int blue = color & 0xFF;
        return new Color(red, green, blue, alpha);
    }

    /**
     * Takes a screenshot of the user's primary monitor.
     * @return - BufferedImage Screenshot of the monitor.
     */
    private BufferedImage screenshot()
    {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final BufferedImage screen = robot.createScreenCapture(new Rectangle(screenSize));
        return screen;
    }
    public void setVolumeRegion(Rectangle volumeRegion) {
        this.volumeRegion = volumeRegion;
    }
}
