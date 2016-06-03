package at.ac.tuwien.swtm.admin.view;

import at.ac.tuwien.swtm.admin.view.model.RouteViewModel;
import at.ac.tuwien.swtm.admin.view.model.VehicleViewModel;
import at.ac.tuwien.swtm.analytics.rest.api.WastebinMomentsResource;
import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinMomentRepresentation;
import at.ac.tuwien.swtm.notification.rest.api.NotificationsResource;
import at.ac.tuwien.swtm.notification.rest.api.model.NotificationRepresentation;
import at.ac.tuwien.swtm.planner.rest.api.PlansResource;
import at.ac.tuwien.swtm.planner.rest.api.model.PlanRepresentation;
import at.ac.tuwien.swtm.resources.rest.api.VehiclesResource;
import at.ac.tuwien.swtm.resources.rest.api.model.VehicleRepresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
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
public class PlanView implements Serializable {

    private Double centerLatitude = 48.415469;
    private Double centerLongitude = 15.605784;
    private MapModel mapModel;
    private List<RouteViewModel> routes;
    private List<VehicleViewModel> vehicles;
    private List<VehicleViewModel> selectedVehicles;
    private List<NotificationRepresentation> notifications;
    private Marker selectedMarker;

    @Inject
    private PlansResource plansResource;
    @Inject
    private WastebinMomentsResource wastebinMomentsResource;
    @Inject
    private VehiclesResource vehiclesResource;
    @Inject
    private NotificationsResource notificationsResource;

    @PostConstruct
    public void init() {
        reloadPlan();
        reloadNotifications();
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

    public void reloadNotifications() {
        notifications = notificationsResource.getNotifications();
    }

    public void reloadPlan() {
        mapModel = new DefaultMapModel();

        List<VehicleRepresentation> vehicles = vehiclesResource.getVehicles();
        this.vehicles = vehicles.stream()
                .map(VehicleViewModel::fromRepresentation)
                .collect(Collectors.toList());
        Map<Long, VehicleViewModel> vehicleViewModelsById = this.vehicles.stream()
                .collect(Collectors.toMap(VehicleViewModel::getId, Function.identity()));
        Map<Long, VehicleRepresentation> vehiclesById = vehicles.stream()
                .collect(Collectors.toMap(VehicleRepresentation::getId, Function.identity()));
        List<WastebinMomentRepresentation> wastebins = wastebinMomentsResource.getLatestMoments();
        Map<Long, WastebinMomentRepresentation> wastbinsById = wastebins.stream()
                .collect(Collectors.toMap(WastebinMomentRepresentation::getId, Function.identity()));

        if (!vehiclesById.isEmpty()) {
            int numPositions = 0;
            double centerLatitude = 0;
            double centerLongitude = 0;

            PlanRepresentation planRepresentation = plansResource.createNewPlan();

            if (planRepresentation == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Keine Lösung gefunden"));
            } else {

                for (WastebinMomentRepresentation wastebin : wastebins) {
                    mapModel.addOverlay(new Marker(
                            new LatLng(wastebin.getLatitude(), wastebin.getLongitude()),
                            wastebin.getName(), wastebin));
                }

                routes = new ArrayList<>(planRepresentation.getVehicleAssignment().size());
                int routeIdx = 0;
                for (Map.Entry<Long, List<Long>> route : planRepresentation.getVehicleAssignment().entrySet()) {
                    VehicleRepresentation vehicle = vehiclesById.get(route.getKey());
                    VehicleViewModel vehicleViewModel = vehicleViewModelsById.get(route.getKey());
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

                    RouteViewModel routeViewModel = new RouteViewModel(vehicle, tour, incrementColor("#FF0000", routeIdx * 150));
                    vehicleViewModel.setRouteColor(routeViewModel.getColor());
                    routes.add(routeViewModel);
                    routeIdx++;
                }

                this.selectedVehicles = new ArrayList<>(this.vehicles);
                this.centerLatitude = centerLatitude / numPositions;
                this.centerLongitude = centerLongitude / numPositions;
            }
        }
    }

    public String getRoutesAsJson() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(routes);
    }

    public List<RouteViewModel> getRoutes() {
        return routes;
    }

    public Converter getVehicleConverter() {
        return new Converter() {
            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                return vehicles.stream()
                        .filter(vehicleViewModel -> vehicleViewModel.getId().toString().equals(value))
                        .findAny().orElse(null);
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                if (value == null) {
                    return null;
                } else {
                    return ((VehicleViewModel) value).getId().toString();
                }
            }
        };
    }

    public Marker getSelectedMarker() {
        return selectedMarker;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        this.selectedMarker = (Marker) event.getOverlay();
    }

    public List<VehicleViewModel> getSelectedVehicles() {
        return selectedVehicles;
    }

    public void setSelectedVehicles(List<VehicleViewModel> selectedVehicles) {
        this.selectedVehicles = selectedVehicles;
    }

    public List<VehicleViewModel> getVehicles() {
        return vehicles;
    }

    public List<NotificationRepresentation> getNotifications() {
        return notifications;
    }

    private String incrementColor(String baseColor, int step) {
        int colorToInt = Integer.parseInt(baseColor.substring(1), 16);  // Convert HEX color to integer
        colorToInt = (colorToInt + step) % 16777216; // mod 16^6;                                             // Increment integer with step
        StringBuilder colorSb = new StringBuilder("#");
        String colorValStr = Integer.toString(colorToInt, 16);
        for (int i = 0; i < 6 - colorValStr.length(); i++) {
            colorSb.append('0');
        }
        return colorSb.append(colorValStr).toString();
    };

}
