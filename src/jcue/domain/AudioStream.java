package jcue.domain;

import jouvieje.bass.Bass;
import jouvieje.bass.defines.BASS_ATTRIB;
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

        //TODO: create waveform image

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

    public void setPosition(double pos) {
        if (this.stream != null) {
            long bytePos = Bass.BASS_ChannelSeconds2Bytes(this.stream.asInt(), pos);
            Bass.BASS_ChannelSetPosition(this.stream.asInt(), bytePos, BASS_POS.BASS_POS_BYTE);
        }
    }

    public double getPosition() {
        if (this.stream != null) {
            long bytePos = Bass.BASS_ChannelGetPosition(this.stream.asInt(), BASS_POS.BASS_POS_BYTE);
            return Bass.BASS_ChannelBytes2Seconds(this.stream.asInt(), bytePos);
        }

        return -1;
    }

    public void startVolumeChange(double newVolume, double duration) {
        if (this.stream != null) {
            Bass.BASS_ChannelSlideAttribute(this.stream.asInt(),
                    BASS_ATTRIB.BASS_ATTRIB_VOL,
                    (float) newVolume,
                    (int) (duration * 1000));
        }
    }
    
    public void startPanChange(double newPan, double duration) {
        if (this.stream != null) {
            Bass.BASS_ChannelSlideAttribute(this.stream.asInt(),
                    BASS_ATTRIB.BASS_ATTRIB_PAN,
                    (float) newPan,
                    (int) (duration * 1000));
        }
    }
    
    public void setVolume(double volume) {
        Bass.BASS_ChannelSetAttribute(this.stream.asInt(),
                BASS_ATTRIB.BASS_ATTRIB_VOL,
                (float) volume);
    }
    
    public void setPan(double pan) {
        Bass.BASS_ChannelSetAttribute(this.stream.asInt(),
                BASS_ATTRIB.BASS_ATTRIB_PAN,
                (float) pan);
    }

    public double getLength() {
        return length;
    }

    public String getFilePath() {
        return filePath;
    }
}
