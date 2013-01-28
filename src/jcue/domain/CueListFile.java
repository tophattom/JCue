package jcue.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jcue.domain.audiocue.AudioCue;
import jcue.domain.audiocue.AudioStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jaakko
 */
public class CueListFile {
    
    public static void saveList(File file) {
        try {
            //Create document
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            //Add root element
            Element rootElem = doc.createElement("cues");
            doc.appendChild(rootElem);
            
            //Loop through all cues
            ArrayList<AbstractCue> cues = CueList.getInstance().getCues();
            for (AbstractCue cue : cues) {
                //Add cue element to the document
                rootElem.appendChild(cueElement(cue, doc));
            }
            
            //Save document to a file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            
            StreamResult result = new StreamResult(file);
            //StreamResult result = new StreamResult(System.out);
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void loadList(File file) {
        
    }
    
    private static Element cueElement(AbstractCue cue, Document doc) {
        Element result = doc.createElement("cue");
        
        //Type
        Element typeElem = doc.createElement("type");
        typeElem.appendChild(doc.createTextNode(cue.getType().toString()));
        result.appendChild(typeElem);
        
        //Name
        Element nameElem = doc.createElement("name");
        nameElem.appendChild(doc.createTextNode(cue.getName()));
        result.appendChild(nameElem);
        
        //Description
        Element descElem = doc.createElement("description");
        descElem.appendChild(doc.createTextNode(cue.getName()));
        result.appendChild(descElem);
        
        //Start mode
        Element smElem = doc.createElement("startmode");
        smElem.appendChild(doc.createTextNode(cue.getStartMode().toString()));
        result.appendChild(smElem);
        
        //Start delay
        Element delayElem = doc.createElement("delay");
        delayElem.appendChild(doc.createTextNode(Double.toString(cue.getStartDelay())));
        result.appendChild(delayElem);
        
        //Parent and child cues
        Element parentElem = doc.createElement("parentcue");
        AbstractCue parent = cue.getParentCue();
        if (parent != null) {
            parentElem.appendChild(doc.createTextNode(parent.getName()));
        }
        result.appendChild(parentElem);
        addChildCues(cue, result, doc);
        
        
        if (cue instanceof AudioCue) {
            result.appendChild(audioElement((AudioCue) cue, doc));
        }
        
        
        return result;
    }
    
    private static void addChildCues(AbstractCue cue, Element cueElem, Document doc) {
        Element childrenElem = doc.createElement("children");
        
        LinkedHashSet<AbstractCue> childCues = cue.getChildCues();
        for (AbstractCue c : childCues) {
            Element childElem = doc.createElement("child");
            childElem.appendChild(doc.createTextNode(c.getName()));
            childrenElem.appendChild(childElem);
        }
        
        cueElem.appendChild(childrenElem);
    }
    
    private static Element audioElement(AudioCue ac, Document doc) {
        Element result = doc.createElement("audio");
        
        //FIle
        Element fileElem = doc.createElement("filepath");
        AudioStream stream = ac.getAudio();
        if (stream != null) {
            fileElem.appendChild(doc.createTextNode(ac.getAudio().getFilePath()));
        }
        result.appendChild(fileElem);
        
        //In and out positions
        Element inElem = doc.createElement("in");
        inElem.appendChild(doc.createTextNode(Double.toString(ac.getInPos())));
        result.appendChild(inElem);
        
        Element outElem = doc.createElement("out");
        outElem.appendChild(doc.createTextNode(Double.toString(ac.getOutPos())));
        result.appendChild(outElem);
        
        //Fade in and out times
        Element fadeInElem = doc.createElement("fadein");
        fadeInElem.appendChild(doc.createTextNode(Double.toString(ac.getFadeIn())));
        result.appendChild(fadeInElem);
        
        Element fadeOutElem = doc.createElement("fadeout");
        fadeOutElem.appendChild(doc.createTextNode(Double.toString(ac.getFadeOut())));
        result.appendChild(fadeOutElem);
        
        //Master volume
        Element volumeElem = doc.createElement("mastervolume");
        volumeElem.appendChild(doc.createTextNode(Double.toString(ac.getVolume())));
        result.appendChild(volumeElem);
        
        return result;
    }
}
