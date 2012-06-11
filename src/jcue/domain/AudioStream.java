/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcue.domain;

import jouvieje.bass.Bass;
import jouvieje.bass.defines.BASS_POS;
import jouvieje.bass.structures.HSTREAM;

/**
 *
 * @author Jaakko
 */
public class AudioStream {

    private HSTREAM stream;
    private String filePath;
    
    private double length;

    public AudioStream() {
    }

    public boolean loadFile(String path) {
        if (this.stream != null) {
            Bass.BASS_StreamFree(this.stream);
            this.stream = null;
            this.filePath = "";
        }

        this.stream = Bass.BASS_StreamCreateFile(false, path, 0, 0, 0);

        if (this.stream == null) {
            return false;
        }
        
        double bytePos = Bass.BASS_ChannelGetLength(this.stream.asInt(), BASS_POS.BASS_POS_BYTE);
        this.length = Bass.BASS_ChannelBytes2Seconds(this.stream.asInt(), (long) bytePos);
        
        this.filePath = path;

        return true;
    }
    
    public void play() {
        if (this.stream != null) {
            Bass.BASS_ChannelPlay(this.stream.asInt(), false);
        }
    }
    
    public void pause() {
        if (this.stream != null) {
            Bass.BASS_ChannelPause(this.stream.asInt());
        }
    }
    
    public void stop() {
        if (this.stream != null) {
            Bass.BASS_ChannelStop(this.stream.asInt());
        }
    }

    public double getLength() {
        return length;
    }

    public String getFilePath() {
        return filePath;
    }
}
