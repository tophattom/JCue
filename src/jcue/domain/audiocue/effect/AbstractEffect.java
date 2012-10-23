package jcue.domain.audiocue.effect;

import java.util.ArrayList;
import jouvieje.bass.Bass;
import jouvieje.bass.structures.HFX;
import jouvieje.bass.utils.Pointer;

/**
 *
 * @author Jaakko
 */
public abstract class AbstractEffect {
    
    private ArrayList<HFX> handles;
    private Pointer effectStruct;
    
    protected int type;

    public ArrayList<HFX> getHandles() {
        return handles;
    }

    public void setHandles(ArrayList<HFX> handles) {
        this.handles = handles;
    }
    
    public Pointer getEffectStruct() {
        return effectStruct;
    }

    public void setEffectStruct(Pointer effectStruct) {
        this.effectStruct = effectStruct;
    }

    public int getType() {
        return type;
    }
    
    protected void updateEffect() {
        if (this.effectStruct != null && this.handles.size() > 0) {
            for (HFX hfx : this.handles) {
                Bass.BASS_FXSetParameters(hfx, this.effectStruct);
            }
        }
    }
    
    public abstract String[] getParameters();
    public abstract void setParameter(String param, double value);

}
