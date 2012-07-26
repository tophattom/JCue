package jcue.ui;

import java.text.ParseException;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Jaakko
 */
public class TimeFormatter extends JFormattedTextField.AbstractFormatter {

    @Override
    public Object stringToValue(String string) throws ParseException {
        string = string.replace(',', '.');
        String[] parts = string.split(":");
        double mins = Double.parseDouble(parts[0]);
        double secs = Double.parseDouble(parts[1]);
        
        return mins * 60.0 + secs;
    }

    @Override
    public String valueToString(Object o) throws ParseException {
        if (o == null) {
            return "00:00,00";
        }
        
        double value = (Double) o;
        int mins = (int) (value / 60.0);
        double secs = (value / 60.0 - mins) * 60.0;
        
        return String.format("%d:%05.2f", mins, secs);
    }
}
