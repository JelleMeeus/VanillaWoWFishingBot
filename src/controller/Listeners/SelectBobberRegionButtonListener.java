package controller.Listeners;

import model.FisherMan;
import model.Tools.Lang;
import model.Tools.UITools;
import view.mainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.Listeners.CaptureRectangle.getInstance;

/**
 * Created by jelle on 23-6-2017.
 */
public class SelectBobberRegionButtonListener implements ActionListener {
    private mainGUI main;
    private FisherMan fisherMan;
    public SelectBobberRegionButtonListener(mainGUI main, FisherMan fisherMan)
    {
        this.main = main;
        this.fisherMan = fisherMan;
    }
    @Override
    /* Calls model to start fishing */
    public void actionPerformed(ActionEvent e) {
        int x_coord; /* Stores value of upper left x coordinate*/
        int y_coord; /* Stores value of upper left y coordinate */
        int width; /* Stores width of the rectangle */
        int height; /* Stores height of the rectangle */

        JTextArea consoleOutput = main.getConsoleOutput(); /* Gets console output from gui */

        UITools.writeToConsoleWithTS(consoleOutput,Lang.EN_LABEL_SELECTINGBOBBER);
        Rectangle screen = getInstance().getResult();  /* Calls select rectangle command */
        /*if (screen==null) { screen = new Rectangle(); } */  /* User has not selected a rectangle */
        /*
        x_coord = (int) screen.getX();
        y_coord = (int) screen.getY();
        width = (int) screen.getWidth();
        height = (int) screen.getHeight();
        */
        //UITools.writeToConsoleWithTS(consoleOutput,String.format("X:%5d Y:%5d W:%5d H:%5d\n",x_coord,y_coord, width, height));
        UITools.writeToConsoleWithTS(consoleOutput,Lang.EN_LABEL_SELECTEDBOBBER);
        fisherMan.setBobberRegion(screen);
    }
}
