package at.ac.tuwien.swtm.analytics.event;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class GPSConflict extends AbstractSensorFailure {

    private static final long serialVersionUID = 4028179435676962160L;

    public GPSConflict(Long wastebinId, String wastebinName, LocalDateTime timestamp, LocalDateTime lastValidMomentTimestamp) {
        super(SensorType.GPS, wastebinId, wastebinName, timestamp, lastValidMomentTimestamp);
    }

}
