package at.ac.tuwien.swtm.admin.view;

import at.ac.tuwien.swtm.analytics.rest.api.WastebinMomentResource;
import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinMomentRepresentation;
import at.ac.tuwien.swtm.planner.rest.api.PlansResource;
import at.ac.tuwien.swtm.planner.rest.api.model.PlanRepresentation;
import at.ac.tuwien.swtm.resources.rest.api.VehiclesResource;
import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;
import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.primefaces.model.map.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 31.05.2016.
 */
@Named
@ViewAccessScoped
public class PlanViewModel implements Serializable {

    private Double centerLatitude = 48.415469;
    private Double centerLongitude = 15.605784;
    private MapModel mapModel;

    @Inject
    private PlansResource plansResource;
    @Inject
    private WastebinMomentResource wastebinMomentResource;
    @Inject
    private VehiclesResource vehiclesResource;

    @PostConstruct
    public void init() {
        reloadPlan();
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public void reloadPlan() {
        mapModel = new DefaultMapModel();

        Map<Long, VehicleRepresentation> vehicles = vehiclesResource.getVehicles().stream()
                .collect(Collectors.toMap(VehicleRepresentation::getId, Function.identity()));
        List<WastebinMomentRepresentation> wastebins = wastebinMomentResource.getLatestMoments();
        Map<Long, WastebinMomentRepresentation> wastbinsById = wastebins.stream()
                .collect(Collectors.toMap(WastebinMomentRepresentation::getId, Function.identity()));

        if (!vehicles.isEmpty()) {
            int numPositions = 0;
            double centerLatitude = 0;
            double centerLongitude = 0;

            PlanRepresentation planRepresentation = plansResource.createNewPlan();

            for (WastebinMomentRepresentation wastebin : wastebins) {
                mapModel.addOverlay(new Marker(
                        new LatLng(wastebin.getLatitude(), wastebin.getLongitude()),
                        wastebin.getName()));
            }

            for (Map.Entry<Long, List<Long>> route : planRepresentation.getVehicleAssignment().entrySet()) {
                VehicleRepresentation vehicle = vehicles.get(route.getKey());
                List<LatLng> tour = new ArrayList<>();

                centerLatitude += vehicle.getLatitude();
                centerLongitude += vehicle.getLongitude();
                numPositions++;

                tour.add(new LatLng(vehicle.getLatitude(), vehicle.getLongitude()));
                for (Long wastbinId : route.getValue()) {
                    WastebinMomentRepresentation wastebin = wastbinsById.get(wastbinId);
                    tour.add(new LatLng(wastebin.getLatitude(), wastebin.getLongitude()));

                    centerLatitude += wastebin.getLatitude();
                    centerLongitude += wastebin.getLongitude();
                    numPositions++;
                }
                tour.add(new LatLng(vehicle.getLatitude(), vehicle.getLongitude()));

                Polyline polyline = new Polyline(tour);
                polyline.setStrokeWeight(10);
                polyline.setStrokeColor("#FF9900");
                polyline.setStrokeOpacity(0.7);

                mapModel.addOverlay(polyline);
            }

            this.centerLatitude = centerLatitude / numPositions;
            this.centerLongitude = centerLongitude / numPositions;
        }
    }

}
