package at.ac.tuwien.swtm.analytics.rest.api.model;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
public class WastebinRepresentation {

    private Long id;
    private String name;
    private Double fixedLatitude;
    private Double fixedLongitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFixedLatitude() {
        return fixedLatitude;
    }

    public void setFixedLatitude(Double fixedLatitude) {
        this.fixedLatitude = fixedLatitude;
    }

    public Double getFixedLongitude() {
        return fixedLongitude;
    }

    public void setFixedLongitude(Double fixedLongitude) {
        this.fixedLongitude = fixedLongitude;
    }
}
