package at.ac.tuwien.swtm.analytics.model;

import at.ac.tuwien.swtm.common.model.BaseEntity;
import at.ac.tuwien.swtm.common.model.Point;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class Wastebin extends BaseEntity<Long> {

    private String name;
    private Point fixedLocation;

    @NotNull
    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Embedded
    public Point getFixedLocation() {
        return fixedLocation;
    }

    public void setFixedLocation(Point fixedLocation) {
        this.fixedLocation = fixedLocation;
    }
}
