package at.ac.tuwien.swtm.analytics.event;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class GPSConflict {

    private Long wastebinId;
    private String wastebinName;

    public GPSConflict(Long wastebinId, String wastebinName) {
        this.wastebinName = wastebinName;
    }

    public Long getWastebinId() {
        return wastebinId;
    }

    public String getWastebinName() {
        return wastebinName;
    }
}
