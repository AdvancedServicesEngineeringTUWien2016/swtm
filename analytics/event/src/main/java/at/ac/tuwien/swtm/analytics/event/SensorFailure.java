package at.ac.tuwien.swtm.analytics.event;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class SensorFailure extends AbstractSensorFailure {

    private static final long serialVersionUID = 443944223386097065L;

    public SensorFailure(SensorType sensorType, Long wastebinId, String wastebinName, LocalDateTime timestamp, LocalDateTime lastValidMomentTimestamp) {
        super(sensorType, wastebinId, wastebinName, timestamp, lastValidMomentTimestamp);
    }

}
