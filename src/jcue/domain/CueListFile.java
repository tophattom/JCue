package jcue.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
}
