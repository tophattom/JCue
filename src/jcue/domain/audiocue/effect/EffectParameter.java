package jcue.domain.audiocue.effect;

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
    
    
}
