package at.ac.tuwien.swtm.admin.view.model;

import at.ac.tuwien.swtm.resources.rest.api.VehiclesResource;
import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;
import org.primefaces.model.map.LatLng;

import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 02.06.2016.
 */
public class RouteViewModel {

    private VehicleRepresentation vehicle;
    private List<LatLng> waypoints;
    private String color;

    public RouteViewModel(VehicleRepresentation vehicle, List<LatLng> waypoints, String color) {
        this.vehicle = vehicle;
        this.waypoints = waypoints;
        this.color = color;
    }

    public VehicleRepresentation getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleRepresentation vehicle) {
        this.vehicle = vehicle;
    }

    public List<LatLng> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<LatLng> waypoints) {
        this.waypoints = waypoints;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
