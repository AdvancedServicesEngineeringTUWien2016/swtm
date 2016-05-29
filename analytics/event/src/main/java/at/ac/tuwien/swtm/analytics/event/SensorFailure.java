package at.ac.tuwien.swtm.analytics.event;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class SensorFailure {

    private Long wastebinId;
    private String wastebinName;
    private SensorType sensorType;

    public SensorFailure(Long wastebinId, String wastebinName, SensorType sensorType) {
        this.wastebinId = wastebinId;
        this.wastebinName = wastebinName;
        this.sensorType = sensorType;
    }

    public Long getWastebinId() {
        return wastebinId;
    }

    public String getWastebinName() {
        return wastebinName;
    }

    public SensorType getSensorType() {
        return sensorType;
    }
}
