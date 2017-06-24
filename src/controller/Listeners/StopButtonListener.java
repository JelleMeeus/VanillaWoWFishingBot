package controller.Listeners;

import model.FisherMan;
import model.Tools.Lang;
import model.Tools.UITools;
import view.mainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jelle on 23-6-2017.
 */
public class StopButtonListener implements ActionListener {
    private mainGUI main;
    private FisherMan fisherMan;
    public StopButtonListener(mainGUI main, FisherMan fisherMan)
    {
        this.main = main;
        this.fisherMan = fisherMan;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        /* Calls model to stop fishing */
        JTextArea consoleOutput = main.getConsoleOutput();
        UITools.writeToConsoleWithTS(consoleOutput, Lang.EN_NODE_STOP);

        //TODO: STOP FISHING
        fisherMan.interrupt();
    }
}
