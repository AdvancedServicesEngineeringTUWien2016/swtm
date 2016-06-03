package at.ac.tuwien.swtm.analytics.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
@Embeddable
public class WastebinStateId implements Serializable {

    private Long wastebinMomentId;
    private WastebinMomentState.State state;

    public WastebinStateId() { }

    public WastebinStateId(Long wastebinMomentId, WastebinMomentState.State state) {
        this.wastebinMomentId = wastebinMomentId;
        this.state = state;
    }

    @Column(name = "WASTEBIN_MOMENT_ID")
    public Long getWastebinMomentId() {
        return wastebinMomentId;
    }

    public void setWastebinMomentId(Long wastebinMomentId) {
        this.wastebinMomentId = wastebinMomentId;
    }

    @Enumerated(EnumType.STRING)
    public WastebinMomentState.State getState() {
        return state;
    }

    public void setState(WastebinMomentState.State state) {
        this.state = state;
    }
}
