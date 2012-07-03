package jcue.domain;

import jouvieje.bass.Bass;
import jouvieje.bass.defines.BASS_DEVICE_STATUS;
import jouvieje.bass.structures.BASS_DEVICEINFO;

/**
 *
 * @author Jaakko
 */
public class SoundDevice {
    
    private String name;
    private int id;
    
    private double volume;
    
    private boolean autoInclude;

    public SoundDevice(String name, int id) {
        this.name = name;
        this.id = id;
        
        this.volume = 1.0;
        
        this.autoInclude = false;
    }
    
    public boolean isEnabled() {
        BASS_DEVICEINFO info = BASS_DEVICEINFO.allocate();
        Bass.BASS_GetDeviceInfo(this.id, info);
        
        if ((info.getFlags() & BASS_DEVICE_STATUS.BASS_DEVICE_ENABLED) == 1) {
            return true;
        }
        
        return false;
    }
    
    public boolean isDefault() {
        BASS_DEVICEINFO info = BASS_DEVICEINFO.allocate();
        Bass.BASS_GetDeviceInfo(this.id, info);
        
        if ((info.getFlags() & BASS_DEVICE_STATUS.BASS_DEVICE_DEFAULT) == 2) {
            return true;
        }
        
        return false;
    }
    
    public boolean isInitialized() {
        BASS_DEVICEINFO info = BASS_DEVICEINFO.allocate();
        Bass.BASS_GetDeviceInfo(this.id, info);
        
        if ((info.getFlags() & BASS_DEVICE_STATUS.BASS_DEVICE_INIT) == 4) {
            return true;
        }
        
        return false;
    }
    
    public void init(int sampleRate) throws Exception {
        boolean BASS_Init = Bass.BASS_Init(this.id, sampleRate, 0, null, null);
        
        if (!BASS_Init) {
            throw new Exception("Device " + this.id + " couldn't be initialized! BASS error code: " + Bass.BASS_ErrorGetCode());
        }
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
    
    
}
