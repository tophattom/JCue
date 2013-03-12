package jcue.domain.audiocue.effect;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jaakko
 */
public class EffectParameter {
    
    private String name;
    private String unit;

    private double value;
    private double maxValue, minValue;
    
    private String type;

    public EffectParameter(String name, String unit, double minValue, double maxValue, String type) {
        this.name = name;
        this.unit = unit;
        
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.type = type;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = Math.max(this.minValue, Math.min(this.maxValue, value));
    }

    @Override
    public String toString() {
        String format;
        
        if (this.type.equals("double")) {
            return this.name + ": " + String.format("%.2f", this.value) + " " + this.unit;
        } else if (this.type.equals("int")) {
            return this.name + ": " + ((int) this.value) + " " + this.unit;
        }
        
        return "";
    }


    public Element toElement(Document doc) {
        Element result = doc.createElement("parameter");

        //Name
        Element nameElem = doc.createElement("name");
        nameElem.appendChild(doc.createTextNode(name));
        result.appendChild(nameElem);

        //Unit
        Element unitElem = doc.createElement("unit");
        unitElem.appendChild(doc.createTextNode(unit));
        result.appendChild(unitElem);

        //Value
        Element valueElem = doc.createElement("value");
        valueElem.appendChild(doc.createTextNode(Double.toString(value)));
        result.appendChild(valueElem);

        //Max and min values
        Element maxElem = doc.createElement("maxvalue");
        maxElem.appendChild(doc.createTextNode(Double.toString(maxValue)));
        result.appendChild(maxElem);

        Element minElem = doc.createElement("minvalue");
        minElem.appendChild(doc.createTextNode(Double.toString(minValue)));
        result.appendChild(minElem);

        //Type
        Element typeElem = doc.createElement("type");
        typeElem.appendChild(doc.createTextNode(type));
        result.appendChild(typeElem);

        return result;
    }
}
