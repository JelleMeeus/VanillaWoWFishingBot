package controller.Listeners;

import model.FisherMan;
import model.Tools.UITools;
import view.mainGUI;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by jelle on 24-6-2017.
 */
public class SliderListener implements ChangeListener {
    private FisherMan fisherMan;
    private mainGUI main;

    public SliderListener(mainGUI main, FisherMan fisherMan)
    {
        this.fisherMan = fisherMan;
        this.main = main;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        fisherMan.setScanSpeedProperty( main.getSliderSpeed().getValue()  );
        fisherMan.setSensitivityProperty( main.getSliderSensitivity().getValue() );
        if (fisherMan.isDebugMode())
        {
            UITools.writeToConsoleWithTS( main.getConsoleOutput(),
                    String.format("Debug Sensitivity: %s\n Delay: %s",
                            fisherMan.getSensitivityProperty(),
                            fisherMan.getScanSpeedProperty()));
        }
        //TODO: formula, update
        int valueHorizontalSteps = (int) main.getSpinnerHorizontalSteps().getValue();
        int valueVerticalSteps = (int) main.getSpinnerVerticalSteps().getValue();
        double value = 1.2 * 22000 / (valueHorizontalSteps * valueVerticalSteps);

        main.getTextFieldMinDelay().setText(String.format("%3f2",value));
     }
}
