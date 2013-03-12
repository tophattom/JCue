package jcue.domain;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
public class ProjectFile {

    public static String currentPath = "";

    
    public static void saveProject() {
        if (currentPath.isEmpty()) {
            return;
        }

        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            Element projectElem = doc.createElement("project");
            doc.appendChild(projectElem);
            
            //Devices
            Element devicesElem = doc.createElement("devices");
            for (SoundDevice sd : DeviceManager.getInstance().getDevices()) {
                devicesElem.appendChild(sd.toElement(doc));
            }
            projectElem.appendChild(devicesElem);
            
            //Cues
            Element cuesElem = doc.createElement("cues");
            for (AbstractCue ac : CueList.getInstance().getCues()) {
                cuesElem.appendChild(ac.toElement(doc));
            }
            projectElem.appendChild(cuesElem);
            
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(currentPath));
            
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public static void openProject(File file) {

        currentPath = file.getAbsolutePath();
    }
}
