package at.ac.tuwien.swtm.analytics.webapp.event;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class GPSFailure {

    private String wastebinName;

    public GPSFailure(String wastebinName) {
        this.wastebinName = wastebinName;
    }
}
