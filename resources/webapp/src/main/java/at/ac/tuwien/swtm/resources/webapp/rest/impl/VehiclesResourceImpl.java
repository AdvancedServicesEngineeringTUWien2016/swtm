package at.ac.tuwien.swtm.resources.webapp.rest.impl;

import at.ac.tuwien.swtm.resources.model.Vehicle;
import at.ac.tuwien.swtm.resources.rest.api.VehiclesResource;
import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;
import at.ac.tuwien.swtm.resources.webapp.data.VehicleDataAccess;
import at.ac.tuwien.swtm.resources.webapp.service.VehicleService;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 29.05.2016.
 */
public class VehiclesResourceImpl implements VehiclesResource {

    @Inject
    private VehicleService vehicleService;

    @Inject
    private VehicleDataAccess vehicleDataAccess;

    @Override
    public List<VehicleRepresentation> getVehicles() {
        return vehicleDataAccess.getAll().stream()
                .map(vehicle -> {
                    VehicleRepresentation vehicleRepresentation = new VehicleRepresentation();
                    vehicleRepresentation.setId(vehicle.getId());
                    vehicleRepresentation.setDescription(vehicle.getDescription());
                    vehicleRepresentation.setCapacity(vehicle.getCapacity());
                    vehicleRepresentation.setLatitude(vehicle.getLocation().getLatitude());
                    vehicleRepresentation.setLongitude(vehicle.getLocation().getLongitude());
                    return vehicleRepresentation;
                }).collect(Collectors.toList());
    }

    @Override
    public Response putVehicle(VehicleRepresentation vehicleRepresentation) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleRepresentation.getId());
        vehicle.setCapacity(vehicleRepresentation.getCapacity());
        vehicle.setDescription(vehicleRepresentation.getDescription());
        vehicle.getLocation().setLatitude(vehicleRepresentation.getLatitude());
        vehicle.getLocation().setLongitude(vehicleRepresentation.getLongitude());
        vehicleService.saveVehicle(vehicle);

        return Response.ok().build();
    }
}
