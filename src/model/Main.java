package model;
import controller.Listeners.CaptureRectangle;
import view.*;
import model.Tools.Audio;

import javax.sound.sampled.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
   private static Thread thdFishing;
   public static FisherMan fisherMan;
    public static void main(String[] args) {
        fisherMan = new FisherMan();
       mainGUI mainGUI = new mainGUI(fisherMan);
    }
    public static void startFishing()
    {
        thdFishing = new Thread(fisherMan);
        thdFishing.start();
    }

    public static void callibrate()
    {
       new Thread(new Runnable() {
            public void run() {
                Rectangle screen = CaptureRectangle.getInstance().getResult();
                fisherMan.setTooltipRegion(screen);
                fisherMan.callibrateauto(CaptureRectangle.getInstance().getScreenShot());
            }
        }).start();
    }
}
