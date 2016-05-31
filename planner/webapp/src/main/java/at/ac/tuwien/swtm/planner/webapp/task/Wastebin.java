package at.ac.tuwien.swtm.planner.webapp.task;

import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinMomentRepresentation;
import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.math.BigDecimal;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
@PlanningEntity
public class Wastebin {

    private Long id;
    private Vehicle assignedVehicle;
    private Double longitude;
    private Double latitude;
    private BigDecimal payload;
    private BigDecimal fillingDegree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PlanningVariable(valueRangeProviderRefs = "vehicleRange")
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
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

    public BigDecimal getPayload() {
        return payload;
    }

    public void setPayload(BigDecimal payload) {
        this.payload = payload;
    }

    public BigDecimal getFillingDegree() {
        return fillingDegree;
    }

    public void setFillingDegree(BigDecimal fillingDegree) {
        this.fillingDegree = fillingDegree;
    }

    public static Wastebin fromWastebinRepresentation(WastebinMomentRepresentation wastebinMomentRepresentation) {
        Wastebin wastebin = new Wastebin();
        wastebin.setId(wastebinMomentRepresentation.getId());
        wastebin.setLatitude(wastebinMomentRepresentation.getLatitude());
        wastebin.setLongitude(wastebinMomentRepresentation.getLongitude());
        wastebin.setFillingDegree(wastebinMomentRepresentation.getFillingDegree());
        wastebin.setPayload(wastebinMomentRepresentation.getPayload());

        return wastebin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wastebin)) return false;

        Wastebin wastebin = (Wastebin) o;

        return id != null ? id.equals(wastebin.id) : wastebin.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
