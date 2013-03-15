package jcue.domain;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jcue.domain.audiocue.AudioCue;
import jcue.domain.audiocue.effect.AbstractEffect;
import jcue.domain.eventcue.AbstractEvent;
import jcue.domain.eventcue.EffectEvent;
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
    private static HashMap<AbstractEvent, String> targetQueue = new LinkedHashMap<AbstractEvent, String>();

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

            //Set missing parents and targets
            processParentQueue();
            processTargetQueue();
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

    public static void addToTargetQueue(AbstractEvent event, String targetName) {
        targetQueue.put(event, targetName);
    }

    private static void processParentQueue() {
        for (AbstractCue ac : parentQueue.keySet()) {
            String parentName = parentQueue.get(ac);
            AbstractCue parentCue = CueList.getInstance().getCue(parentName);

            if (parentCue != null) {
                ac.setParentCue(parentCue);
            }
        }

        parentQueue.clear();
    }

    private static void processTargetQueue() {
        for (AbstractEvent ae : targetQueue.keySet()) {
            String targetName = targetQueue.get(ae);

            if (!targetName.startsWith("effect:")) {
                AudioCue targetCue = (AudioCue) CueList.getInstance().getCue(targetName);

                if (targetCue != null) {
                    ae.setTargetCue(targetCue);
                }
            } else {
                EffectEvent ee = (EffectEvent) ae;
                String effectName = targetName.substring(7, targetName.length());

                if (ae.getTargetCue() != null) {
                    AbstractEffect effect = ae.getTargetCue().getEffectRack().getEffect(effectName);

                    if (effect != null) {
                        ee.setTargetEffect(effect);
                    }
                }
            }

        }
    }
}
