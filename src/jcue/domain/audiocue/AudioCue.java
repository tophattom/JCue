package jcue.domain.audiocue;

import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import jcue.domain.*;
import jcue.domain.audiocue.effect.EffectRack;
import jcue.domain.fadecue.ParameterEnvelope;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 *
 * @author Jaakko
 */
public class AudioCue extends AbstractCue {

    private AudioStream audio;
    private double inPos, outPos;
    
    private double fadeIn, fadeOut;
    private ParameterEnvelope fadeInCurve, fadeOutCurve;
    
    private double volume;
    
    private ArrayList<SoundDevice> outputs;
    
    private EffectRack effectRack;

    public AudioCue(String name, String description, ArrayList<SoundDevice> outputs) {
        super(name, description, CueType.AUDIO);
        
        this.outputs = outputs;

        this.audio = new AudioStream(this.outputs);
        
        this.volume = 1.0;
        
        this.fadeIn = 0.0;
        this.fadeOut = 0.0;
        
        this.effectRack = new EffectRack(this);
    }

    public void loadAudio(String filePath) {
        if (filePath == null) {
            return;
        }
        
        try {
            this.audio.loadFile(filePath);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        this.inPos = 0;
        this.outPos = this.audio.getLength();
        
        if (this.getDescription().isEmpty()) {
            File audioFile = new File(filePath);
            String desc = audioFile.getName();
            
            this.setDescription(desc);
        }
        
        CueList cl = CueList.getInstance();
        cl.fireContentsChanged(this, cl.getCueIndex(this) - 1, cl.getCueIndex(this));
    }

    @Override
    public void start(boolean delay) {
        super.start(delay);
        
        effectRack.resetToDefaults();
        
        if (!delay || super.getStartDelay() == 0) {
            if (this.audio != null) {
                //Set audio position to start if the cue is not paused
                if (this.state == CueState.STOPPED || state == CueState.WAITING) {
                    this.audio.setPosition(this.inPos);
                    this.audio.setMasterVolume(this.volume);
                    
                    //If fade in time has been set, start the fade
                    if (this.fadeInCurve != null) {
                        this.audio.setMasterVolumeDirect(0);
                        fadeInCurve.start();
                    }
                }
                
                this.audio.play();              //Start playing the audio

                if (fadeInCurve == null) {
                    this.state = CueState.PLAYING;
                }
            }
        }
    }

    @Override
    public void pause() {
        if (this.audio != null) {
            //TODO: pause fades when pausing audio
            this.audio.pause();
            super.state = CueState.PAUSED;
        }
    }

    @Override
    public void stop() {
        super.stop();
        
        if (this.audio != null) {
            this.audio.stop();
            this.audio.setPosition(this.inPos);
        }
        
        if (this.fadeInCurve != null) {
            fadeInCurve.stop();
        }

        if (this.fadeOutCurve != null) {
            fadeOutCurve.stop();
        }
    }

    //Getters and setters
    public double getFadeIn() {
        return fadeIn;
    }

    public double getFadeOut() {
        return fadeOut;
    }

    public double getInPos() {
        return inPos;
    }

    public double getOutPos() {
        return outPos;
    }

    public double getVolume() {
        return volume;
    }
    
    public double getDeviceVolume(SoundDevice sd) {
        return this.audio.getDeviceVolume(sd);
    }
    
    public double getRemainingTime() {
        return this.outPos - this.audio.getPosition();
    }

    public AudioStream getAudio() {
        return audio;
    }
    
    public void setFadeIn(double fadeIn) {
        this.fadeIn = fadeIn;
        
        if (fadeIn > 0) {
            if (this.fadeInCurve == null) {
                fadeInCurve = new ParameterEnvelope();
                
                fadeInCurve.setTargetCue(this);
            }
            
            this.updateFadeCurves();
        } else {
            fadeInCurve = null;
        }
    }

    public void setFadeOut(double fadeOut) {
        this.fadeOut = fadeOut;
        
        if (fadeOut > 0) {
            if (this.fadeOutCurve == null) {
                fadeOutCurve = new ParameterEnvelope(true);
                
                fadeOutCurve.setTargetCue(this);
            }
            
            this.updateFadeCurves();
            
        } else {
            fadeOutCurve = null;
        }
    }

    public void setInPos(double inPos) {
        this.inPos = inPos;
    }

    public void setOutPos(double outPos) {
        this.outPos = outPos;
        this.audio.setOutPosition(outPos, this);
        
        this.updateFadeCurves();
    }

    public void setVolume(double volume) {
        this.volume = volume;
        this.audio.setMasterVolume(volume);
        
        this.updateFadeCurves();
    }
    
    public void setDeviceVolume(SoundDevice sd, double volume) {
        this.audio.setDeviceVolume(volume, sd);
    }

    public ArrayList<SoundDevice> getOutputs() {
        return outputs;
    }
    
    public void addOutput(SoundDevice sd) {
        if (!this.outputs.contains(sd)) {
            this.outputs.add(sd);
            this.audio.addOutput(sd);
        }
    }
    
    public boolean removeOutput(SoundDevice sd) {
        if (!this.outputs.contains(sd)) {
            return true;
        }
        
        if (this.outputs.size() == 1) {
            return false;
        }
        
        this.audio.removeOutput(sd);
        this.outputs.remove(sd);
        
        CueList cl = CueList.getInstance();
        cl.fireContentsChanged(this, cl.getCueIndex(this) - 1, cl.getCueIndex(this));
        
        return true;
    }
    
    public EffectRack getEffectRack() {
        return effectRack;
    }
    
    private void updateFadeCurves() {
        double midY = this.volume / 2.0;
        
        if (this.fadeInCurve != null) {
            QuadCurve2D inCurve = fadeInCurve.getCurves().get(0);
            fadeInCurve.setCurve(inCurve, 0, 1, 0.5, midY, 1, 1.0 - this.volume, false);
            fadeInCurve.setDuration(this.fadeIn);
        }
        
        if (this.fadeOutCurve != null) {
            QuadCurve2D outCurve = fadeOutCurve.getCurves().get(0);
            fadeOutCurve.setCurve(outCurve, 0, 1.0 - this.volume, 0.5, midY, 1, 1, false);
            fadeOutCurve.setDuration(this.fadeOut);
            
            this.audio.setFadeOut(this.outPos - this.fadeOut, fadeOutCurve);
        }
    }

    public void startFadeOut() {
        if (this.fadeOutCurve != null) {
            fadeOutCurve.start();
        }
    }

    @Override
    public Element toElement(Document doc) {
        Element result = super.toElement(doc);
        
        //File name
        Element fileElem = doc.createElement("filepath");
        fileElem.appendChild(doc.createTextNode(audio.getFilePath()));
        result.appendChild(fileElem);
        
        //In and out positions
        Element inElem = doc.createElement("in");
        inElem.appendChild(doc.createTextNode(Double.toString(inPos)));
        result.appendChild(inElem);
        
        Element outElem = doc.createElement("out");
        outElem.appendChild(doc.createTextNode(Double.toString(outPos)));
        result.appendChild(outElem);
        
        //Fade in and out times
        Element fadeInElem = doc.createElement("fadein");
        fadeInElem.appendChild(doc.createTextNode(Double.toString(fadeIn)));
        result.appendChild(fadeInElem);
        
        Element fadeOutElem = doc.createElement("fadeout");
        fadeOutElem.appendChild(doc.createTextNode(Double.toString(fadeOut)));
        result.appendChild(fadeOutElem);
        
        //Volume
        Element volumeElem = doc.createElement("mastervolume");
        volumeElem.appendChild(doc.createTextNode(Double.toString(volume)));
        result.appendChild(volumeElem);
        
        //Outputs
        Element outputsElem = doc.createElement("outputs");
        for (SoundDevice sd : outputs) {
            Element outputElem = doc.createElement("output");
            
            //Device id
            Element deviceElem = doc.createElement("deviceid");
            deviceElem.appendChild(doc.createTextNode(Integer.toString(sd.getId())));
            outputElem.appendChild(deviceElem);
            
            //Volume and pan
            Element volElem = doc.createElement("volume");
            volElem.appendChild(doc.createTextNode(Double.toString(audio.getDeviceVolume(sd))));
            outputElem.appendChild(volElem);
            
            Element panElem = doc.createElement("pan");
            panElem.appendChild(doc.createTextNode(Double.toString(audio.getDevicePan(sd))));
            outputElem.appendChild(panElem);
            
            //Muted
            Element mutedElem = doc.createElement("muted");
            mutedElem.appendChild(doc.createTextNode(Boolean.toString(audio.isMuted(sd))));
            outputElem.appendChild(mutedElem);
            
            outputsElem.appendChild(outputElem);
        }
        result.appendChild(outputsElem);
        
        //Effect rack
        result.appendChild(effectRack.toElement(doc));
        
        return result;
    }

    public static AudioCue fromElement(Element elem) {
        //General cue stuff
        String name = ProjectFile.getTagValue("name", elem);
        String description = ProjectFile.getTagValue("description", elem);
        StartMode startMode = StartMode.fromString(ProjectFile.getTagValue("startmode", elem));
        double delay = Double.parseDouble(ProjectFile.getTagValue("delay", elem));

        String parentName = ProjectFile.getTagValue("parentcue", elem);
        AbstractCue parentCue = CueList.getInstance().getCue(parentName);

        //Temporary structure for outputs' info
        LinkedHashMap<SoundDevice, VirtualOutput> outputInfo = new LinkedHashMap<SoundDevice, VirtualOutput>();

        //Load info on used outputs
        NodeList outputNodes = elem.getElementsByTagName("output");
        for (int i = 0; i < outputNodes.getLength(); i++) {
            Element outputElem = (Element) outputNodes.item(i);

            if (outputElem.getParentNode().getParentNode() == elem) {
                int deviceId = Integer.parseInt(ProjectFile.getTagValue("deviceid", outputElem));
                double vol = Double.parseDouble(ProjectFile.getTagValue("volume", outputElem));
                double pan = Double.parseDouble(ProjectFile.getTagValue("pan", outputElem));
                boolean muted = Boolean.parseBoolean(ProjectFile.getTagValue("muted", outputElem));

                SoundDevice device = DeviceManager.getInstance().getDevice(deviceId);
                VirtualOutput tmpOutput = new VirtualOutput(device);
                tmpOutput.setPan(pan);
                tmpOutput.setVolume(vol, 0);
                if (muted) {
                    tmpOutput.mute();
                }

                outputInfo.put(device, tmpOutput);
            }
        }

        //Create resulting cue
        AudioCue result = new AudioCue(name, description, new ArrayList<SoundDevice>(outputInfo.keySet()));

        result.setStartMode(startMode);
        result.setStartDelay(delay);

        //Handle parent cue
        if (parentCue != null) {
            result.setParentCue(parentCue);
        } else {
            ProjectFile.addToParentQueue(result, parentName);
        }

        //Load audio
        String filePath = ProjectFile.getTagValue("filepath", elem);
        if (filePath != null) {
            result.loadAudio(filePath);
        }

        //Parse parameters
        double inPos = Double.parseDouble(ProjectFile.getTagValue("in", elem));
        double outPos = Double.parseDouble(ProjectFile.getTagValue("out", elem));

        double fadeInTime = Double.parseDouble(ProjectFile.getTagValue("fadein", elem));
        double fadeOutTime = Double.parseDouble(ProjectFile.getTagValue("fadeout", elem));

        double masterVolume = Double.parseDouble(ProjectFile.getTagValue("mastervolume", elem));

        //Set parameters
        result.setInPos(inPos);
        result.setOutPos(outPos);
        result.setFadeIn(fadeInTime);
        result.setFadeOut(fadeOutTime);
        result.setVolume(masterVolume);

        //Set device parameters
        for (SoundDevice sd : outputInfo.keySet()) {
            VirtualOutput tmpOutput = outputInfo.get(sd);

            result.setDeviceVolume(sd, tmpOutput.getVolume());
            result.getAudio().setDevicePan(tmpOutput.getPan(), sd);
            if (tmpOutput.isMuted()) {
                result.getAudio().muteOutput(sd);
            }
        }

        //Load effects
        Element rackElem = (Element) elem.getElementsByTagName("effectrack").item(0);
        result.getEffectRack().fromElement(rackElem);

        return result;
    }
}
