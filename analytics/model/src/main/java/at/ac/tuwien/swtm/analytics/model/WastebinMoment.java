package at.ac.tuwien.swtm.analytics.model;

import at.ac.tuwien.swtm.common.model.BaseEntity;
import at.ac.tuwien.swtm.common.model.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
@Entity
public class WastebinMoment extends BaseEntity<Long> {

    private Wastebin wastebin;
    private LocalDateTime timestamp;
    private Point location = new Point();
    private Point overriddenLocation = new Point();
    private BigDecimal fillingDegree;
    private BigDecimal payload;
    private Set<WastebinMomentState> wastebinMomentStates;

    @ManyToOne(optional = false)
    public Wastebin getWastebin() {
        return wastebin;
    }

    public void setWastebin(Wastebin wastebin) {
        this.wastebin = wastebin;
    }

    @NotNull
    @Column(nullable = false)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime created) {
        this.timestamp = created;
    }

    @Embedded
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "OVERRIDDEN_LATITUDE")),
            @AttributeOverride(name = "longitude", column = @Column(name = "OVERRIDDEN_LONGITUDE"))
    })
    @Embedded
    public Point getOverriddenLocation() {
        return overriddenLocation;
    }

    public void setOverriddenLocation(Point overriddenLocation) {
        this.overriddenLocation = overriddenLocation;
    }

    @Column(scale = 2)
    public BigDecimal getFillingDegree() {
        return fillingDegree;
    }

    public void setFillingDegree(BigDecimal fillingDegree) {
        this.fillingDegree = fillingDegree;
    }

    @Column(scale = 2)
    public BigDecimal getPayload() {
        return payload;
    }

    public void setPayload(BigDecimal payload) {
        this.payload = payload;
    }

    @OneToMany(fetch = FetchType.LAZY)
    public Set<WastebinMomentState> getWastebinMomentStates() {
        return wastebinMomentStates;
    }

    public void setWastebinMomentStates(Set<WastebinMomentState> wastebinMomentStates) {
        this.wastebinMomentStates = wastebinMomentStates;
    }
}
