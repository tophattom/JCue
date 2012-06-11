package jcue.domain;

/**
 * Base class for all different types of cues. Defines properties common to all
 * types such as name, description and start info. Also defines three abstract
 * methods for starting, stopping and pausing cues.
 *
 * @author Jaakko
 */
public abstract class Cue {

    private String name, description;
    private StartMode startMode;
    private double startDelay;

    public Cue(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public StartMode getStartMode() {
        return startMode;
    }

    public double getStartDelay() {
        return startDelay;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDelay(double startDelay) {
        this.startDelay = startDelay;
    }

    public void setStartMode(StartMode startMode) {
        this.startMode = startMode;
    }

    public abstract void start();

    public abstract void pause();

    public abstract void stop();
}
