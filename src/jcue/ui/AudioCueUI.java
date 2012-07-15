/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.*;

/**
 *
 * @author Jaakko
 */
public class AudioCueUI {
    
    private JLabel fileLabel;
    private JTextField fileField;
    private JButton fileButton;
    
    private JLabel lengthLabel;
    private JTextField lengthField;
    
    private JLabel inLabel, outLabel, fadeInLabel, fadeOutLabel;
    private JTextField inField, outField, fadeInField, fadeOutField;
    
    private JLabel volumeLabel, panLabel;
    private JSlider volumeSlider, panSlider;
    private JTextField volumeField, panField;
    
    private JLabel loopStartLabel, loopEndLabel, loopCountLabel;
    private JTextField loopStartField, loopEndField, loopCountField;
    private JCheckBox loopCheck;
    
    //private WaveformPanel waveform;
    
    private JButton playButton, pauseButton, stopButton;

    public AudioCueUI() {
        //File field
        this.fileLabel = new JLabel("File:");
        this.fileField = new JTextField();
        this.fileField.setEditable(false);
        this.fileButton = new JButton("...");
        //********
        
        //Length field
        this.lengthLabel = new JLabel("Length:");
        this.lengthField = new JTextField();
        this.lengthField.setEditable(false);
        //*******
        
        //In and out fields
        this.inLabel = new JLabel("Start:");
        this.outLabel = new JLabel("End:");
        this.inField = new JTextField();
        this.outField = new JTextField();
        //**********
        
        //VOlume control
        this.volumeLabel = new JLabel("Volume:");
        this.volumeSlider = new JSlider(0, 1000);
        this.volumeField = new JTextField();
        //********
        
        //Fade in and fade out
        this.fadeInLabel = new JLabel("Fade in:");
        this.fadeInField = new JTextField();
        this.fadeOutLabel = new JLabel("Fade out:");
        this.fadeOutField = new JTextField();
        //********
        
        //Pan control
        this.panLabel = new JLabel("Panning:");
        this.panSlider = new JSlider(-1000, 1000);
        this.panField = new JTextField();
        //********
        
        //Loop controls
        this.loopStartLabel = new JLabel("Loop start:");
        this.loopStartField = new JTextField();
        this.loopEndLabel = new JLabel("Loop end:");
        this.loopEndField = new JTextField();
        this.loopCountLabel = new JLabel("Loop count:");
        this.loopCountField = new JTextField();
        this.loopCheck = new JCheckBox("Loop");
        //*********
        
        //Waveform
        
        //TODO: change texts to icons
        this.playButton = new JButton("Play");
        this.pauseButton = new JButton("Pause");
        this.stopButton = new JButton("Stop");
    }
    
    public void showUI(JPanel container) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 3, 5, 3);
        
        //File field
        UtilsUI.setGBC(c, 0, 4, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.fileLabel, c);
        
        UtilsUI.setGBC(c, 1, 4, 0.5, 0, 3, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.fileField, c);
        
        UtilsUI.setGBC(c, 4, 4, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.fileButton, c);
        //************
        
        //Length field
        UtilsUI.setGBC(c, 0, 5, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.lengthLabel, c);
        
        UtilsUI.setGBC(c, 1, 5, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.lengthField, c);
        //*********
        
        //In, out and volume
        UtilsUI.setGBC(c, 0, 6, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.inLabel, c);
        
        UtilsUI.setGBC(c, 1, 6, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.inField, c);
        
        UtilsUI.setGBC(c, 2, 6, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.outLabel, c);
        
        UtilsUI.setGBC(c, 3, 6, 0.5, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.outField, c);
        
        UtilsUI.setGBC(c, 4, 6, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.volumeLabel, c);
        
        UtilsUI.setGBC(c, 5, 6, 0.5, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.volumeSlider, c);
        
        UtilsUI.setGBC(c, 6, 6, 0.5, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.volumeField, c);
        //***********
        
        //Fade in, fade out and panning
        UtilsUI.setGBC(c, 0, 7, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.fadeInLabel, c);
        
        UtilsUI.setGBC(c, 1, 7, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.fadeInField, c);
        
        UtilsUI.setGBC(c, 2, 7, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.fadeOutLabel, c);
        
        UtilsUI.setGBC(c, 3, 7, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.fadeOutField, c);
        
        UtilsUI.setGBC(c, 4, 7, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.panLabel, c);
        
        UtilsUI.setGBC(c, 5, 7, 0.5, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.panSlider, c);
        
        UtilsUI.setGBC(c, 6, 7, 0.5, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.panField, c);
        //*************
        
        //Looping controls
        UtilsUI.setGBC(c, 0, 8, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.loopStartLabel, c);
        
        UtilsUI.setGBC(c, 1, 8, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.loopStartField, c);
        
        UtilsUI.setGBC(c, 2, 8, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.loopEndLabel, c);
        
        UtilsUI.setGBC(c, 3, 8, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.loopEndField, c);
        
        UtilsUI.setGBC(c, 4, 8, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.loopCountLabel, c);
        
        UtilsUI.setGBC(c, 5, 8, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.loopCountField, c);
        
        UtilsUI.setGBC(c, 6, 8, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL);
        container.add(this.loopCheck, c);
        //**********
    }
    
    public void setVolumeControlValue(double value) {
        this.volumeField.setText(String.valueOf((1000 * value) / 10));
        this.volumeSlider.setValue((int) (1000 * value));
    }
    
    public void setPanControlValue(double value) {
        this.panField.setText(String.valueOf((1000 * value) / 10));
        this.panSlider.setValue((int) (1000 * value));
    }
}
