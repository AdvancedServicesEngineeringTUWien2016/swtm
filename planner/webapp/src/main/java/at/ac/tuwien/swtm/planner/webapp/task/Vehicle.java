package at.ac.tuwien.swtm.planner.webapp.task;

import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;

import java.math.BigDecimal;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
public class Vehicle {

    private Long id;
    private BigDecimal capacity;
    private BigDecimal range;
    private Double longitude;
    private Double latitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public BigDecimal getRange() {
        return range;
    }

    public void setRange(BigDecimal range) {
        this.range = range;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
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

    public static Vehicle fromVehicleRepresentation(VehicleRepresentation vehicleRepresentation) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleRepresentation.getId());
        vehicle.setLatitude(vehicleRepresentation.getLatitude());
        vehicle.setLongitude(vehicleRepresentation.getLongitude());
        vehicle.setCapacity(vehicleRepresentation.getCapacity());
        vehicle.setRange(vehicleRepresentation.getRange());

        return vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;

        Vehicle vehicle = (Vehicle) o;

        return id != null ? id.equals(vehicle.id) : vehicle.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
