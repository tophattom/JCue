package jcue.domain.audiocue;

import jcue.domain.SoundDevice;
import jouvieje.bass.Bass;
import jouvieje.bass.defines.BASS_ATTRIB;
import jouvieje.bass.defines.BASS_POS;
import jouvieje.bass.structures.HSTREAM;

/**
 *
 * @author Jaakko
 */
public class VirtualOutput {
    
    private SoundDevice outputDevice;
    
    private HSTREAM stream;
    private double volume, pan;
    private boolean muted;

    public VirtualOutput(SoundDevice outputDevice) {
        this.outputDevice = outputDevice;
        
        this.volume = 1.0;
        this.pan = 0.0;
        
        this.muted = false;
    }
    
    public void loadFile(String path) throws Exception {
        if (this.stream != null) {
            Bass.BASS_StreamFree(this.stream);
            this.stream = null;
        }
        
        Bass.BASS_SetDevice(this.outputDevice.getId());
        this.stream = Bass.BASS_StreamCreateFile(false, path, 0, 0, 0);
        
        //Loading failed, throw an exception
        if (this.stream == null) {
            throw new Exception("Error! Loading audio file " + path + " failed! Device: " + this.outputDevice.getName());
        }
    }
    
    public void setVolume(double newVolume, double masterVolume) {
        this.volume = newVolume;
        
        if (this.stream != null && !this.muted) {
            Bass.BASS_ChannelSetAttribute(this.stream.asInt(), BASS_ATTRIB.BASS_ATTRIB_VOL, (float) (newVolume * masterVolume));
        }
    }
    
    public void setVolumeDirect(double newVolume) {
        if (this.stream != null && !this.muted) {
            Bass.BASS_ChannelSetAttribute(this.stream.asInt(), BASS_ATTRIB.BASS_ATTRIB_VOL, (float) newVolume);
        }
    }
    
    public void updateVolume(double masterVolume) {
        if (this.stream != null && !this.muted) {
            Bass.BASS_ChannelSetAttribute(this.stream.asInt(), BASS_ATTRIB.BASS_ATTRIB_VOL, (float) (this.volume * masterVolume));
        }
    }
    
    public double getVolume() {
        return this.volume;
    }
    
    public void setPan(double newPan) {
        this.pan = newPan;
        
        if (this.stream != null) {
            Bass.BASS_ChannelSetAttribute(this.stream.asInt(), BASS_ATTRIB.BASS_ATTRIB_PAN, (float) newPan);
        }
    }
    
    public double getPan() {
        return this.pan;
    }
    
    public void link(HSTREAM masterStream) {
        if (this.stream != null) {
            Bass.BASS_ChannelSetLink(masterStream.asInt(), this.stream.asInt());
        }
    }
    
    public void unlink(HSTREAM masterStream) {
        if (this.stream != null) {
            Bass.BASS_ChannelRemoveLink(masterStream.asInt(), this.stream.asInt());
        }
    }
    
    public void setPosition(double newPos) {
        if (this.stream != null) {
            long bytePos = Bass.BASS_ChannelSeconds2Bytes(this.stream.asInt(), newPos);
            Bass.BASS_ChannelSetPosition(this.stream.asInt(), bytePos, BASS_POS.BASS_POS_BYTE);
        }
    }

    public void mute() {
        this.muted = true;
        
        if (this.stream != null) {
            Bass.BASS_ChannelSetAttribute(this.stream.asInt(), BASS_ATTRIB.BASS_ATTRIB_VOL, 0);
        }
    }
    
    public void unmute(double masterVolume) {
        this.muted = false;
        
        setVolume(this.volume, masterVolume);
    }

    public boolean isMuted() {
        return muted;
    }
    
    
    @Override
    protected void finalize() throws Throwable {
        if (this.stream != null) {
            Bass.BASS_StreamFree(this.stream);
        }
        
        super.finalize();
    }
}
