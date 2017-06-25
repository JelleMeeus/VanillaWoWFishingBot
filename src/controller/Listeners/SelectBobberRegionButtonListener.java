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
    public void actionPerformed(ActionEvent e)
    {
        JTextArea consoleOutput = main.getConsoleOutput(); /* Gets console output from gui */
        UITools.writeToConsoleWithTS(consoleOutput,Lang.EN_LABEL_SELECTINGBOBBER);
        Rectangle screen = getInstance().getResult();  /* Calls select rectangle command */
        UITools.writeToConsoleWithTS(consoleOutput,Lang.EN_LABEL_SELECTEDBOBBER);
        fisherMan.setBobberRegion(screen);
    }
}
