package view;

import controller.Listeners.*;
import javafx.scene.control.Slider;
import model.FisherMan;
import model.Lure;
import model.LureFactory;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by jelle on 23-6-2017.
 */
public class mainGUI extends JFrame {
    /* Convenience variables. */
    final int WINDOW_WIDTH = 470, WINDOW_HEIGHT = 600;
    final String WINDOW_TITLE = "1.12 Fishing Bot Vanilla - WIP";

    private FisherMan fisherMan;

    /*Create Lure Items*/
    private ArrayList<Lure> lureItems =  LureFactory.createLureArrayList();
    /*ArrayList to Lure[] Array*/
    private Lure[] comboItemsArray = lureItems.toArray(new Lure[lureItems.size()]);

    /*Define components*/
    private JPanel rootPanel;
    private JButton btnStart;
    private JButton btnStop;
    private JComboBox comboBoxLureSelection;
    private JCheckBox checkBoxEnableLure;
    private JTextArea textAreaConsoleOutput;
    private JButton buttonClearConsoleOutput;
    private JButton buttonStart;
    private JButton buttonStop;
    private JScrollPane scrollPaneConsoleOutput;
    private JSlider sliderSensitivity;
    private JSlider sliderSpeed;
    private JPanel SlidersSpeedandSensitivity;
    private JCheckBox checkBoxEnableDebug;
    private JCheckBox checkBoxAlwaysOnTop;
    private JButton buttonCalibrate;
    private JButton buttonSelectTooltipRegion;
    private JSpinner spinnerAmountOfLures;
    private JProgressBar progressBar1;
    private JCheckBox checkBoxEnableHourMinute;
    private JComboBox comboBox1;
    private JTextArea toDoTextArea1;
    private JTextArea toDoTextArea;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JButton buttonSelectBobberRegionButton;
    private JSpinner spinnerVerticalSteps;
    private JSpinner spinnerHorizontalSteps;
    private JCheckBox enableVolumeDetectionCheckBox;
    private JSlider sliderVolume;
    private JButton selectVolumeRegionButton;
    private JTextField textFieldMinDelay;

    public mainGUI(FisherMan fisherMan){
        this.fisherMan = fisherMan;
        this.fisherMan.setConsoleTextArea(this.textAreaConsoleOutput);
        textAreaConsoleOutput.setEditable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        //list1.setListData( comboItemsArray);
        comboBoxLureSelection.setModel(new DefaultComboBoxModel(comboItemsArray));

        DefaultCaret caret = (DefaultCaret) textAreaConsoleOutput.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setVisible(true);
        setTitle(WINDOW_TITLE);
        //TODO: enable setResizable(false); after GUI 100% finished
        //setResizable(false);

        /*addActionListeners*/
        addActionListeners();

        };

    public JSpinner getSpinnerVerticalSteps() {
        return spinnerVerticalSteps;
    }

    public JSpinner getSpinnerHorizontalSteps() {
        return spinnerHorizontalSteps;
    }

    private void addActionListeners(){
        buttonStart.addActionListener(new StartButtonListener(this,fisherMan));
        buttonStop.addActionListener(new StopButtonListener(this, fisherMan));
        buttonClearConsoleOutput.addActionListener(new ClearConsoleListener(this));
        buttonSelectTooltipRegion.addActionListener(new SelectTooltipRegionButtonListener(this, fisherMan));
        buttonCalibrate.addActionListener(new CalibrateButtonListener(this, fisherMan));
        buttonSelectBobberRegionButton.addActionListener(new SelectBobberRegionButtonListener(this, fisherMan));
        checkBoxAlwaysOnTop.addItemListener(new AlwaysOnTopButtonListener(this));
        sliderSensitivity.addChangeListener(new SliderListener(this,fisherMan));
        sliderSpeed.addChangeListener(new SliderListener(this,fisherMan));

        selectVolumeRegionButton.addActionListener(new VolumeSelectListener(this,fisherMan));

        spinnerHorizontalSteps.setValue(32);
        spinnerVerticalSteps.setValue(31);


        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_TYPED)
                        {
                            char c = e.getKeyChar();
                            if (c=='Q')
                            {
                                fisherMan.setInterrupted(true);
                            }
                        }
                        return false;
                    }
                });

    }

    public JTextArea getConsoleOutput()
    {
        return this.textAreaConsoleOutput;
    };
    public JCheckBox getCheckBoxEnableLure() { return this.checkBoxEnableLure;};
    public JCheckBox getCheckBoxEnableDebug() { return this.checkBoxEnableDebug;};
    public JCheckBox getCheckBoxAlwaysOnTop() { return this.checkBoxAlwaysOnTop;};
    public JCheckBox getCheckBoxEnableHourMinute() {return this.checkBoxEnableHourMinute;};
    public JComboBox getComboBoxLureSelection() { return this.comboBoxLureSelection;};

    public JSlider getSliderSensitivity() {
        return sliderSensitivity;
    }

    public JCheckBox getCheckBoxEnableVolumeDetection() {
        return enableVolumeDetectionCheckBox;
    }
    public JSlider getSliderVolume() {
        return sliderVolume;
    }

    public JSlider getSliderSpeed() {
        return sliderSpeed;
    }

    public JSpinner getSpinnerAmountOfLures(){
        return spinnerAmountOfLures;
    }
    public JTextField getTextFieldMinDelay(){return textFieldMinDelay;}
}
