package jcue.domain;

import jouvieje.bass.Bass;
import jouvieje.bass.defines.BASS_DEVICE_STATUS;
import jouvieje.bass.structures.BASS_DEVICEINFO;
import jouvieje.bass.structures.BASS_INFO;

/**
 * Represents a physical output device.
 * 
 * @author Jaakko
 */
public class SoundDevice implements Comparable<SoundDevice> {
    
    private String name;
    private int id;
    
    private double volume;
    
    private boolean autoInclude;
    
    private BASS_INFO bassInfo;
    
    public SoundDevice(String name, int id) {
        this.name = name;
        this.id = id;
        
        this.volume = 1.0;
        
        this.autoInclude = false;
    }
    
    /**
     * 
     * @return is device enabled
     */
    public boolean isEnabled() {
        BASS_DEVICEINFO info = BASS_DEVICEINFO.allocate();
        Bass.BASS_GetDeviceInfo(this.id, info);
        
        if ((info.getFlags() & BASS_DEVICE_STATUS.BASS_DEVICE_ENABLED) == 1) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * @return is device system's default device
     */
    public boolean isDefault() {
        BASS_DEVICEINFO info = BASS_DEVICEINFO.allocate();
        Bass.BASS_GetDeviceInfo(this.id, info);
        
        if ((info.getFlags() & BASS_DEVICE_STATUS.BASS_DEVICE_DEFAULT) == 2) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * @return is device initialized
     */
    public boolean isInitialized() {
        BASS_DEVICEINFO info = BASS_DEVICEINFO.allocate();
        Bass.BASS_GetDeviceInfo(this.id, info);
        
        if ((info.getFlags() & BASS_DEVICE_STATUS.BASS_DEVICE_INIT) == 4) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Initializes the device using speficied sample rate.
     * 
     * @param sampleRate desired sample rate
     * @throws Exception when device init fails
     */
    public void init(int sampleRate) throws Exception {
        boolean BASS_Init = Bass.BASS_Init(this.id, sampleRate, 0, null, null);
        
        if (!BASS_Init) {
            throw new Exception("Device " + this.id + " couldn't be initialized! BASS error code: " + Bass.BASS_ErrorGetCode());
        }
        
        this.bassInfo = BASS_INFO.allocate();
        Bass.BASS_GetInfo(bassInfo);
    }
    
    public void setSampleRate(int sampleRate) throws Exception {
        if (this.isInitialized()) {
            Bass.BASS_SetDevice(this.id);
            Bass.BASS_Free();
        }
        
        this.init(sampleRate);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSpeakers() {
        if (this.bassInfo != null) {
            return this.bassInfo.getSpeakers();
        }
        
        return 0;
    }
    
    /**
     * Sets the auto include status. Auto include devices
     * are automatically added to new cues.
     * 
     * @param autoInclude wether to auto include this device
     */
    public void setAutoInclude(boolean autoInclude) {
        this.autoInclude = autoInclude;
    }

    public boolean isAutoInclude() {
        return autoInclude;
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SoundDevice other = (SoundDevice) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public int compareTo(SoundDevice t) {
        return this.id - t.id;
    }
}
