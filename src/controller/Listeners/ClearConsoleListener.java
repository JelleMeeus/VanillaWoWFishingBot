package controller.Listeners;

import view.mainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jelle on 23-6-2017.
 */
public class ClearConsoleListener implements ActionListener {
    private mainGUI main;
    public ClearConsoleListener(mainGUI main)
    {
        this.main = main;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        /* Calls model to start fishing */
        JTextArea consoleOutput = main.getConsoleOutput();
        consoleOutput.setText("");
    }
}
