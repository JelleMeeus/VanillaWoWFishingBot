package controller.Listeners;

import model.FisherMan;
import model.Lure;
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
public class StartButtonListener implements ActionListener {
    private mainGUI main;
    private FisherMan fisherMan;
    private Thread thdFishing;
    public StartButtonListener(mainGUI main, FisherMan fisherMan)
    {
        this.main = main;
        this.fisherMan = fisherMan;
        this.thdFishing = null;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea consoleOutput = main.getConsoleOutput();
        UITools.writeToConsoleWithTS(consoleOutput, "Attempting to " + Lang.EN_NODE_START);
        //TODO: Change values, validate, check, transform
        fisherMan.setInterrupted(false);
        fisherMan.setLure( (Lure)main.getComboBoxLureSelection().getSelectedItem() );
        fisherMan.setLureAmount( (int)main.getSpinnerAmountOfLures().getValue() );
        fisherMan.setUseLure( main.getCheckBoxEnableLure().isSelected());
        fisherMan.setDebugMode( main.getCheckBoxEnableDebug().isSelected() );
        fisherMan.setLureLastApplied(null);
        fisherMan.setScanSpeedProperty( main.getSliderSpeed().getValue()  );
        fisherMan.setSensitivityProperty( main.getSliderSensitivity().getValue() );
        fisherMan.setX_steps((  Integer.parseInt( main.getSpinnerHorizontalSteps().getValue().toString() )));
        fisherMan.setUseVolumeDetection( main.getCheckBoxEnableVolumeDetection().isSelected() );
        fisherMan.setY_steps(  Integer.parseInt( main.getSpinnerVerticalSteps().getValue().toString() ));

        boolean error = true;
        if (thdFishing != null && thdFishing.isAlive())
            UITools.writeToConsoleWithTS(main.getConsoleOutput(),Lang.EN_ERROR_ALREADY_FISHING);
        else if (!fisherMan.isCallibrated())
            UITools.writeToConsoleWithTS(main.getConsoleOutput(),Lang.EN_ERROR_NOT_CALIBRATED);
        else
            error = false;
        if (error) return;

        Main.startFishing();


    }
}
