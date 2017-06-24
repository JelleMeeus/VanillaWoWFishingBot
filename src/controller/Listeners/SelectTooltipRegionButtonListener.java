package controller.Listeners;

import model.FisherMan;
import model.Tools.CaptureRectangle;
import model.Tools.Lang;
import model.Tools.UITools;
import view.mainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jelle on 23-6-2017.
 */
public class SelectTooltipRegionButtonListener implements ActionListener {
    private mainGUI main;
    private FisherMan fisherMan;
    public SelectTooltipRegionButtonListener(mainGUI main, FisherMan fisherMan)
    {
        this.main = main;
        this.fisherMan = fisherMan;
    }
    @Override
    /* Calls model to start fishing */
    public void actionPerformed(ActionEvent e) {
        JTextArea consoleOutput = main.getConsoleOutput(); /* Gets console output from gui */
        UITools.writeToConsoleWithTS(consoleOutput,Lang.EN_LABEL_SELECTINGRECTANGLE);

        Rectangle screen = CaptureRectangle.getResult();  /* Calls select rectangle command */

        //if (screen==null) { screen = new Rectangle(); }  /* User has not selected a rectangle */
        /*
        x_coord = (int) screen.getX();
        y_coord = (int) screen.getY();
        width = (int) screen.getWidth();
        height = (int) screen.getHeight();
        */
        //UITools.writeToConsoleWithTS(consoleOutput,String.format("X:%5d Y:%5d W:%5d H:%5d\n",x_coord,y_coord, width, height));
        //TODO: rewrite manual calibrating to automaticly find tooltip coordinate
        UITools.writeToConsoleWithTS(consoleOutput,Lang.EN_LABEL_SELECTEDRECTANGLE);
        fisherMan.setTooltipRegion(screen);
    }
}
