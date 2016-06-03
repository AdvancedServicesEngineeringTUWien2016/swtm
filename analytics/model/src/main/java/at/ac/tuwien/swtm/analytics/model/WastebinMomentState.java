package at.ac.tuwien.swtm.analytics.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
@Entity
public class WastebinMomentState {

    private WastebinStateId id;
    private WastebinMoment wastebinMoment;

    public WastebinMomentState() { }

    public WastebinMomentState(WastebinStateId id) {
        this.id = id;
    }

    @EmbeddedId
    public WastebinStateId getId() {
        return id;
    }

    public void setId(WastebinStateId id) {
        this.id = id;
    }

    @JoinColumn(name = "WASTEBIN_MOMENT_ID", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public WastebinMoment getWastebinMoment() {
        return wastebinMoment;
    }

    public void setWastebinMoment(WastebinMoment wastebinMoment) {
        this.wastebinMoment = wastebinMoment;
    }

    public static enum State {
        GPS_CONFLICT,
        GPS_FAILURE,
        PAYLOAD_FAILURE,
        FILLING_DEGREE_FAILURE;

        public static List<State> getFailureStates() {
            return Arrays.asList(GPS_FAILURE, PAYLOAD_FAILURE, FILLING_DEGREE_FAILURE);
        }
    }

}
