/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.ui;

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
        this.fileLabel = new JLabel("File:");
        this.fileField = new JTextField();
        this.fileField.setEditable(false);
        this.fileButton = new JButton("...");
        
        this.lengthLabel = new JLabel("Length:");
        this.lengthField = new JTextField();
        this.lengthField.setEditable(false);
        
        this.inLabel = new JLabel("Start:");
        this.outLabel = new JLabel("End:");
        this.inField = new JTextField();
        this.outField = new JTextField();
        
        this.volumeLabel = new JLabel("Volume:");
        this.volumeSlider = new JSlider(0, 1000);
        this.volumeField = new JTextField();
        
        this.panLabel = new JLabel("Panning:");
        this.panSlider = new JSlider(-1000, 1000);
        this.panField = new JTextField();
        
        this.loopStartLabel = new JLabel("Loop start:");
        this.loopStartField = new JTextField();
        this.loopEndLabel = new JLabel("Loop end:");
        this.loopEndField = new JTextField();
        this.loopCountLabel = new JLabel("Loop count:");
        this.loopCountField = new JTextField();
        this.loopCheck = new JCheckBox("Loop");
        
        //Waveform
        
        //TODO: change texts to icons
        this.playButton = new JButton("Play");
        this.pauseButton = new JButton("Pause");
        this.stopButton = new JButton("Stop");
    }
    
    public void showUI(JPanel container) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 3, 5, 3);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        //File field
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0;
        container.add(this.fileLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.5;
        c.gridwidth = 3;
        container.add(this.fileField, c);
        
        c.gridx = 4;
        c.weightx = 0;
        c.gridwidth = 1;
        container.add(this.fileButton, c);
        //************
        
        //Length field
        c.gridy = 5;
        c.gridx = 0;
        c.weightx = 0;
        container.add(this.lengthLabel, c);
        
        c.gridx = 1;
        c.weightx = 0;
        container.add(this.lengthField, c);
        //*********
        
        //In, out and volume
        c.gridy = 6;
        c.gridx = 0;
        c.weightx = 0;
        container.add(this.inLabel, c);
        
        c.gridx = 1;
        c.weightx = 0;
        container.add(this.inField, c);
        
        c.gridx = 2;
        c.weightx = 0;
        container.add(this.outLabel, c);
        
        c.gridx = 3;
        c.weightx = 0.5;
        container.add(this.outField, c);
        
        c.gridx = 4;
        c.weightx = 0;
        container.add(this.volumeLabel, c);
        
        c.gridx = 5;
        c.weightx = 0.5;
        container.add(this.volumeSlider, c);
        
        c.gridx = 6;
        container.add(this.volumeField, c);
        //***********
        
        
    }
}
