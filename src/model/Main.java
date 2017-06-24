package model;
import model.Tools.Lang;
import model.Tools.UITools;
import view.*;

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
                fisherMan.callibrate();
            }
        }).start();
    }
}
