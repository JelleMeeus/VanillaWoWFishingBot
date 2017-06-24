package controller.Listeners;

import model.Tools.Lang;
import model.Tools.UITools;
import view.mainGUI;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by jelle on 23-6-2017.
 */
public class AlwaysOnTopButtonListener implements ItemListener {
    private mainGUI main;
    public AlwaysOnTopButtonListener(mainGUI main)
    {
        this.main = main;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JTextArea consoleOutput = main.getConsoleOutput();
        boolean isEnabled = ( e.getStateChange() == ItemEvent.SELECTED);
        if ( isEnabled )
        {
            //selected
            UITools.writeToConsoleWithTS(consoleOutput, Lang.EN_LABEL_ON_TOP_ENABLE);
        }
        else
        {
            //not selected
            UITools.writeToConsoleWithTS(consoleOutput, Lang.EN_LABEL_ON_TOP_DISABLE);
        }
        //TODO: enable setting always on top GUI
        main.setAlwaysOnTop(isEnabled);
    }
}
