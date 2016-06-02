package at.ac.tuwien.swtm.admin.view.model;

import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;

import java.math.BigDecimal;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 02.06.2016.
 */
public class VehicleViewModel {
    private Long id;
    private String description;
    private BigDecimal capacity;
    private BigDecimal range;
    private String routeColor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getRange() {
        return range;
    }

    public void setRange(BigDecimal range) {
        this.range = range;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public static VehicleViewModel fromRepresentation(VehicleRepresentation vehicleRepresentation) {
        VehicleViewModel vehicleViewModel = new VehicleViewModel();
        vehicleViewModel.setId(vehicleRepresentation.getId());
        vehicleViewModel.setCapacity(vehicleRepresentation.getCapacity());
        vehicleViewModel.setRange(vehicleRepresentation.getRange());
        vehicleViewModel.setDescription(vehicleRepresentation.getDescription());
        return vehicleViewModel;
    }
}
