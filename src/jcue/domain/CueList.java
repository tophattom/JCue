package jcue.domain;

import java.util.ArrayList;
import java.util.EventListener;
import javax.swing.ListModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.eventcue.EventCue;
import jcue.domain.fadecue.FadeCue;

/**
 * Stores the cues created. Only on instance of CueList can
 * be created. Use getInstance() to retrieve it.
 * 
 * @author Jaakko
 */
public class CueList extends AbstractTableModel implements ListModel {
    
    private static volatile CueList instance = null;

    protected EventListenerList listListenerList = new EventListenerList();
    private ArrayList<AbstractCue> cues;
    private AbstractCue currentCue;
    private int counter;
    private DeviceManager dm;

    private CueList() {
        super();
        
        this.cues = new ArrayList<AbstractCue>();
        this.currentCue = null;

        this.counter = 1;

        this.dm = DeviceManager.getInstance();
    }
    
    /**
     * 
     * @return instance of CueList
     */
    public static CueList getInstance() {
        if (instance == null) {
            synchronized (CueList.class) {
                if (instance == null) {
                    instance = new CueList();
                }
            }
        }
        
        return instance;
    }

    /**
     * Create and add a new cue.
     * 
     * @param cueType type of the new cue
     */
    public void addCue(CueType cueType) {
        AbstractCue cue = null;

        if (cueType == CueType.AUDIO) {
            cue = new AudioCue("Q" + counter, "", this.dm.getAutoIncludeDevices());
        } else if (cueType == CueType.EVENT) {
            cue = new EventCue("Q" + counter, "");
        } else if (cueType == CueType.FADE) {
            cue = new FadeCue("Q" + counter, "");
        } else if (cueType == CueType.NOTE) {
        }

        this.cues.add(cue);

        //Notify JList and JTable about new element
        fireIntervalAdded(cue, getSize() - 1, getSize());
        super.fireTableRowsInserted(getSize() - 1, getSize());

        //Increment the "id" counter
        this.counter++;
    }

    /**
     * Add existing cue to the list.
     * 
     * @param cue cue to add
     */
    public void addCue(AbstractCue cue) {
        this.cues.add(cue);

        //Notify JList and JTable about new element
        fireIntervalAdded(cue, getSize() - 1, getSize());
        super.fireTableRowsInserted(getSize() - 1, getSize());

        //Increment the "id" counter
        this.counter++;
    }

    /**
     * Get cue by its index.
     * 
     * @param pos index starting from 0
     * @return cue at index
     */
    public AbstractCue getCue(int pos) {
        return this.cues.get(pos);
    }

    /**
     * 
     * @return amount of cues in the list
     */
    public int size() {
        return this.cues.size();
    }
    
    /**
     * Returns the index of specific cue.
     * 
     * @param cue cue which index to get
     * @return cue's index
     */
    public int getCueIndex(AbstractCue cue) {
        return this.cues.lastIndexOf(cue);
    }

    /**
     * Returns list of all cues.
     * 
     * @return list of all cues
     */
    public ArrayList<AbstractCue> getCues() {
        return cues;
    }

    /**
     * Returns a list of all cues excluding the cue given.
     * 
     * @param exclude cue to exclude from the result
     * @return resulting list
     */
    public ArrayList<AbstractCue> getCues(AbstractCue exclude) {
        ArrayList<AbstractCue> result = new ArrayList<AbstractCue>();
        
        for (AbstractCue ac : this.cues) {
            if (ac != exclude) {
                result.add(ac);
            }
        }
        
        return result;
    }
    
    /**
     * Returns a list of cues of given type.
     * 
     * @param type type of cues
     * @return cues of given type
     */
    public ArrayList<AbstractCue> getCues(CueType type) {
        ArrayList<AbstractCue> result = new ArrayList<AbstractCue>();
        
        for (AbstractCue ac : this.cues) {
            if (ac.getType() == type) {
                result.add(ac);
            }
        }
        
        return result;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Model stuff">
    @Override
    public int getSize() {
        return this.cues.size();
    }
    
    @Override
    public Object getElementAt(int i) {
        return this.cues.get(i);
    }
    
    @Override
    public void addListDataListener(ListDataListener ll) {
        listListenerList.add(ListDataListener.class, ll);
    }
    
    @Override
    public void removeListDataListener(ListDataListener ll) {
        listListenerList.remove(ListDataListener.class, ll);
    }
    
    public ListDataListener[] getListDataListeners() {
        return (ListDataListener[]) listListenerList.getListeners(ListDataListener.class);
    }
    
    public void fireContentsChanged(Object source, int index0, int index1) {
        Object[] listeners = listListenerList.getListenerList();
        ListDataEvent e = null;
        
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                if (e == null) {
                    e = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index0, index1);
                }
                ((ListDataListener) listeners[i + 1]).contentsChanged(e);
            }
        }
    }
    
    protected void fireIntervalAdded(Object source, int index0, int index1) {
        Object[] listeners = listListenerList.getListenerList();
        ListDataEvent e = null;
        
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                if (e == null) {
                    e = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1);
                }
                ((ListDataListener) listeners[i + 1]).contentsChanged(e);
            }
        }
    }
    
    protected void fireIntervalRemoved(Object source, int index0, int index1) {
        Object[] listeners = listListenerList.getListenerList();
        ListDataEvent e = null;
        
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ListDataListener.class) {
                if (e == null) {
                    e = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1);
                }
                ((ListDataListener) listeners[i + 1]).contentsChanged(e);
            }
        }
    }
    
    @Override
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listListenerList.getListeners(listenerType);
    }
    
    @Override
    public int getRowCount() {
        return this.cues.size();
    }
    
    @Override
    public int getColumnCount() {
        return 5;
    }
    
    @Override
    public Object getValueAt(int i, int i1) {
        AbstractCue cue = this.cues.get(i);
        
        if (i1 == 0) {
            return cue.getName() + " " + cue.getDescription();
        } else if (i1 == 1) {
            return cue.getType();
        } else if (i1 == 2) {
            if (cue instanceof AudioCue) {
                AudioCue ac = (AudioCue) cue;
                return ac.getState();
            }
        }
        
        return null;
    }
    //</editor-fold>
}
