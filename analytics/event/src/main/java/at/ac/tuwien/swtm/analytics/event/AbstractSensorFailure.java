package at.ac.tuwien.swtm.analytics.event;

import sun.management.Sensor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
public abstract class AbstractSensorFailure implements Serializable {
    private static final long serialVersionUID = 2070309842474792416L;

    private SensorType sensorType;
    private Long wastebinId;
    private String wastebinName;
    private LocalDateTime timestamp;
    private LocalDateTime lastValidMomentTimestamp;

    public AbstractSensorFailure(SensorType sensorType, Long wastebinId, String wastebinName, LocalDateTime timestamp, LocalDateTime lastValidMomentTimestamp) {
        this.sensorType = sensorType;
        this.wastebinId = wastebinId;
        this.wastebinName = wastebinName;
        this.timestamp = timestamp;
        this.lastValidMomentTimestamp = lastValidMomentTimestamp;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public Long getWastebinId() {
        return wastebinId;
    }

    public String getWastebinName() {
        return wastebinName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LocalDateTime getLastValidMomentTimestamp() {
        return lastValidMomentTimestamp;
    }
}
