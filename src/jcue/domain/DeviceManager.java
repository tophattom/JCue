package jcue.domain;

import java.util.ArrayList;
import jouvieje.bass.Bass;
import jouvieje.bass.structures.BASS_DEVICEINFO;

/**
 * Class for managing sound devices.
 *
 * @author Jaakko
 */
public class DeviceManager {
    private static volatile DeviceManager instance = null;;
    
    //List for all of the devices
    private ArrayList<SoundDevice> devices;
    
    private DeviceManager() {
        this.devices = new ArrayList<SoundDevice>();
        
        BASS_DEVICEINFO info = BASS_DEVICEINFO.allocate();
        int i = 0;
        
        //Get information on system devices
        while (Bass.BASS_GetDeviceInfo(i, info)) {
            if (!info.getName().equals("No sound")) {   //Don't include no sound -device
                SoundDevice sd = new SoundDevice(info.getName(), i);

                //By default init all enabled device
                if (sd.isEnabled()) {
                    try {
                        sd.init(44100);
                    } catch (Exception ex) {
                    }
                }

                //System default device should be automatically added to all cues
                if (sd.isDefault()) {
                    sd.setAutoInclude(true);
                }

                this.devices.add(sd);
            }
            
            i++;
        }
    }
    
    public static DeviceManager getInstance() {
        if (instance == null) {
            synchronized (DeviceManager.class) {
                if (instance == null) {
                    instance = new DeviceManager();
                }
            }
        }
        
        return instance;
    }

    /**
     * 
     * @return All devices available 
     */
    public ArrayList<SoundDevice> getDevices() {
        return devices;
    }
    
    /**
     * 
     * @param id Id number of the device
     * @return SoundDevice object with the specified id
     */
    public SoundDevice getDevice(int id) {
        for (SoundDevice d : this.devices) {
            if (d.getId() == id) {
                return d;
            }
        }
        
        return null;
    }
    
    /**
     * 
     * @return A list of enabled devices 
     */
    public ArrayList<SoundDevice> getEnabledDevices() {
        ArrayList<SoundDevice> result = new ArrayList<SoundDevice>();
        
        for (SoundDevice d : this.devices) {
            if (d.isEnabled()) {
                result.add(d);
            }
        }
        
        return result;
    }
    
    /**
     * 
     * @return A list of devices that should be automatically includet for new cues
     */
    public ArrayList<SoundDevice> getAutoIncludeDevices() {
        ArrayList<SoundDevice> result = new ArrayList<SoundDevice>();
        
        for (SoundDevice d : this.devices) {
            if (d.isAutoInclude()) {
                result.add(d);
            }
        }
        
        return result;
    }
    
    /**
     * 
     * @return True if at least one device has been initialized
     */
    public boolean isInitialized() {
        for (SoundDevice sd : this.devices) {
            if (sd.isInitialized()) {
                return true;
            }
        }
        
        return false;
    }
}
