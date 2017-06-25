package controller.Listeners;

import model.FisherMan;
import model.Main;
import model.Tools.Lang;
import model.Tools.UITools;
import view.mainGUI;

import javax.swing.*;
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

        Main.callibrate();
        UITools.writeToConsoleWithTS(consoleOutput,Lang.EN_LABEL_SELECTEDRECTANGLE);
    }
}
