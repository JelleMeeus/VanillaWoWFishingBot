package controller.Listeners;

import model.FisherMan;
import model.Tools.Lang;
import model.Tools.UITools;
import view.mainGUI;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.Listeners.CaptureRectangle.getInstance;

/**
 * Created by jelle on 6/25/2017.
 */
public class VolumeSelectListener implements ActionListener {
    private mainGUI main;
    private FisherMan fisherMan;
    public VolumeSelectListener(mainGUI mainGUI, FisherMan fisherMan) {
        this.main = mainGUI;
        this.fisherMan = fisherMan;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Rectangle screen = getInstance().getResult();  /* Calls select rectangle command */
        fisherMan.setVolumeRegion(screen);
    }
}
