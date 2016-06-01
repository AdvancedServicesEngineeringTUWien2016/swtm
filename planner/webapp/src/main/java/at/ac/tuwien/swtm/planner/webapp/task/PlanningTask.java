package at.ac.tuwien.swtm.planner.webapp.task;

import at.ac.tuwien.swtm.analytics.rest.api.WastebinMomentResource;
import at.ac.tuwien.swtm.planner.webapp.config.PlannerConfiguration;
import at.ac.tuwien.swtm.resources.rest.api.VehiclesResource;
import com.google.appengine.repackaged.org.joda.time.ReadableInstant;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import org.joda.time.Instant;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
public class PlanningTask implements Runnable {

    private static final String googleMapsApiKey = "";
    private static final SolverFactory<TransportationPlan> solverFactory;
    static {
        solverFactory = SolverFactory.createFromXmlResource(
                "META-INF/transportationSolverConfig.xml");
    }

    @Inject
    private PlannerConfiguration plannerConfiguration;

    @Inject
    private VehiclesResource vehiclesResource;

    @Inject
    private WastebinMomentResource wastebinMomentResource;

    private TransportationPlan solution;

    @Override
    public void run() {
        Solver<TransportationPlan> solver = solverFactory.buildSolver();

        solution = solver.solve(getProblem());
    }

    public TransportationPlan getSolution() {
        return solution;
    }

    private TransportationPlan getProblem() {
        List<Vehicle> vehicles = vehiclesResource.getVehicles().stream()
                .map(Vehicle::fromVehicleRepresentation)
                .collect(Collectors.toList());

        List<Wastebin> wastebins = wastebinMomentResource.getLatestMoments().stream()
                .map(Wastebin::fromWastebinRepresentation)
                .collect(Collectors.toList());

        // TODO: only fetch matrix when wastebin locations change or vehicle locations change or if either is added
        DistanceMatrix distanceMatrix = getDistanceMatrix(vehicles, wastebins);

        Map<Object, Map<Object, DistanceMatrixElement>> objectDistances = new HashMap<>();
        for (int i = 0; i < vehicles.size(); i++) {
            objectDistances.put(vehicles.get(i), toMap(vehicles, wastebins, distanceMatrix.rows[i]));
        }
        for (int i = 0; i < wastebins.size(); i++) {
            objectDistances.put(wastebins.get(i), toMap(vehicles, wastebins, distanceMatrix.rows[vehicles.size() + i]));
        }

        return new TransportationPlan(wastebins, vehicles, objectDistances);
    }

    private Map<Object, DistanceMatrixElement> toMap(List<Vehicle> vehicles, List<Wastebin> wastebins, DistanceMatrixRow distanceMatrixRow) {
        Map<Object, DistanceMatrixElement> result = new HashMap<>(vehicles.size() + wastebins.size());
        for (int i = 0; i < vehicles.size(); i++) {
            result.put(vehicles.get(i), distanceMatrixRow.elements[i]);
        }
        for (int i = 0; i < wastebins.size(); i++) {
            result.put(wastebins.get(i), distanceMatrixRow.elements[vehicles.size() + i]);
        }
        return result;
    }

    private DistanceMatrix getDistanceMatrix(List<Vehicle> vehicles, List<Wastebin> wastebins) {
        List<LatLng> locations = vehicles.stream()
                .map(vehicle -> new LatLng(vehicle.getLatitude(), vehicle.getLongitude()))
                .collect(Collectors.toList());

        locations.addAll(wastebins.stream()
                .map(wastebin -> new LatLng(wastebin.getLatitude(), wastebin.getLongitude()))
                .collect(Collectors.toList()));

        LatLng[] locationsArray = locations.toArray(new LatLng[0]);

        GeoApiContext geoContext = new GeoApiContext().setApiKey(plannerConfiguration.getGoogleMapsApiKey());
        DistanceMatrixApiRequest distanceMatrixApiRequest = DistanceMatrixApi.newRequest(geoContext);
        distanceMatrixApiRequest.origins(locationsArray);
        distanceMatrixApiRequest.destinations(locationsArray);
        distanceMatrixApiRequest.mode(TravelMode.DRIVING);
        distanceMatrixApiRequest.trafficModel(TrafficModel.BEST_GUESS);
        distanceMatrixApiRequest.units(Unit.METRIC);
        distanceMatrixApiRequest.departureTime(Instant.now());

        try {
            return distanceMatrixApiRequest.await();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
