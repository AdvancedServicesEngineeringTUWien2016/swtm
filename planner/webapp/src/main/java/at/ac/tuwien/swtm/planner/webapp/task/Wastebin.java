package at.ac.tuwien.swtm.planner.webapp.task;

import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinMomentRepresentation;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.AnchorShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

import java.math.BigDecimal;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
public class Wastebin implements RouteComponent {

    private Long id;
    private RouteComponent previousRouteComponent;
    private Double longitude;
    private Double latitude;
    private BigDecimal payload;
    private BigDecimal fillingDegree;

    // Shadow variables
    private Wastebin nextWastebin;
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PlanningVariable(graphType = PlanningVariableGraphType.CHAINED, valueRangeProviderRefs = {"wastebinRange", "vehicleRange"})
    public RouteComponent getPreviousRouteComponent() {
        return previousRouteComponent;
    }

    public void setPreviousRouteComponent(RouteComponent previousRouteComponent) {
        this.previousRouteComponent = previousRouteComponent;
    }

    @AnchorShadowVariable(sourceVariableName = "previousRouteComponent")
    @Override
    public Vehicle getVehicle() {
        return vehicle;
//        return previousRouteComponent == null ? null : previousRouteComponent.getVehicle();
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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

    @Override
    public Wastebin getNextWastebin() {
        return nextWastebin;
    }

    @Override
    public void setNextWastebin(Wastebin nextWastebin) {
        this.nextWastebin = nextWastebin;
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
