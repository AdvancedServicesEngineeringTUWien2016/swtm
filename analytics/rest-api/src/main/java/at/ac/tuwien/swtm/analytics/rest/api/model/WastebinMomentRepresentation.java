package at.ac.tuwien.swtm.analytics.rest.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 31.05.2016.
 */
public class WastebinMomentRepresentation {

    private Long id;
    private String name;
    private Double longitude;
    private Double latitude;
    private LocalDateTime timestamp;
    private BigDecimal fillingDegree;
    private BigDecimal payload;

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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getFillingDegree() {
        return fillingDegree;
    }

    public void setFillingDegree(BigDecimal fillingDegree) {
        this.fillingDegree = fillingDegree;
    }

    public BigDecimal getPayload() {
        return payload;
    }

    public void setPayload(BigDecimal payload) {
        this.payload = payload;
    }
}
