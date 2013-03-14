package jcue.domain;

import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jaakko
 */
public class ProjectFile {

    public static String currentPath = "";

    private static HashMap<Integer, Integer> idReplace = new HashMap<Integer, Integer>();
    private static HashMap<AbstractCue, String> parentQueue = new HashMap<AbstractCue, String>();

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
        if (file == null) {
            return;
        }

        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            //Check if device configuration matches
            NodeList deviceNodes = doc.getElementsByTagName("device");
            for (int i = 0; i < deviceNodes.getLength(); i++) {
                Element deviceElem = (Element) deviceNodes.item(i);

                String name = getTagValue("name", deviceElem);
                Integer id = Integer.parseInt(getTagValue("id", deviceElem));

                SoundDevice tmpDevice = new SoundDevice(name, id);
                if (!DeviceManager.getInstance().hasDevice(tmpDevice)) {
                    System.out.println("Computer's device configuration doesn't match project file's. Aborting.");
                    return;
                }
            }

            //Load cues
            NodeList cueNodes = doc.getElementsByTagName("cue");
            for (int i = 0; i < cueNodes.getLength(); i++) {
                Element cueElem = (Element) cueNodes.item(i);

                CueList.getInstance().addCue(AbstractCue.fromElement(cueElem));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        currentPath = file.getAbsolutePath();
    }

    public static String getTagValue(String sTag, Element elem) {
        NodeList nList = elem.getElementsByTagName(sTag).item(0).getChildNodes();
        Node value = nList.item(0);

        if (value != null) {
            return value.getNodeValue();
        }

        return "";
    }

    public static void addToParentQueue(AbstractCue cue, String parentName) {
        parentQueue.put(cue, parentName);
    }

    private static void processParentQueue() {

    }
}
