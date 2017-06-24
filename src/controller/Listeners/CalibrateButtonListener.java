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
public class CalibrateButtonListener implements ActionListener {
    private mainGUI main;
    private FisherMan fisherMan;
    public CalibrateButtonListener(mainGUI main, FisherMan fisherMan)
    {
        this.main = main;
        this.fisherMan = fisherMan;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Callibrate
        if ( fisherMan.getTooltipRegion() == null)
        {
            UITools.writeToConsoleWithTS(main.getConsoleOutput(),"Select tooltip region first!");
            return;
        }
        Main.callibrate();
    }
}
