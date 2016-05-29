package at.ac.tuwien.swtm.resources.model;

import at.ac.tuwien.swtm.common.model.BaseEntity;
import at.ac.tuwien.swtm.common.model.Point;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class Vehicle extends BaseEntity<Long> {
    private BigDecimal capacity;
    private String description;
    private Point location;

    @NotNull
    @Column(nullable = false, precision = 2)
    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(nullable = false)),
            @AttributeOverride(name = "longitude", column = @Column(nullable = false))
    })
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
