package at.ac.tuwien.swtm.planner.webapp.task;

import com.google.maps.model.DistanceMatrixElement;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 30.05.2016.
 */
public class TransportationPlanScoreCalculator implements EasyScoreCalculator<TransportationPlan> {

    private static final BigDecimal maximumFillingDegreeWeight = BigDecimal.valueOf(10);
    private static final BigDecimal payloadSumWeight = BigDecimal.valueOf(5);
    private static final BigDecimal routeLengthSumWeight = BigDecimal.valueOf(5);

    @Override
    public Score calculateScore(TransportationPlan transportationPlan) {
        int hardScore = 0;

        /**
         * Hard constraints:
         *  - each route must start and end at the vehicle's position (we handle this implicitly)
         *  - one route must not be longer than the vehicle's reach
         *  - one vehicle cannot load more waste than its maximum payload
         *
         * Soft constraints (with priorities):
         *  1. we want to minimize the maximum filling degree of all bins
         *  2. we want to maximize the amount of waste transported by all vehicles
         *  3. we want to minimize the total route duration
         *  4. we want to use the smallest vehicles (= lowest capacities) if possible
         *
         * Other assumptions:
         *  - One wastebin is either emptied completely or it is not touched at all.
         */
        List<Wastebin> wastebins = transportationPlan.getWastebins();
        List<Wastebin> assignedWastebins = wastebins.stream()
                .filter(wastebin -> wastebin.getVehicle() != null)
                .collect(Collectors.toList());
        Set<Wastebin> unassignedWastebins = new HashSet<>(wastebins);
        unassignedWastebins.removeAll(assignedWastebins);

        List<Vehicle> assignedVehicles = transportationPlan.getVehicles().stream()
                .filter(vehicle -> vehicle.getNextWastebin() != null)
                .collect(Collectors.toList());

        BigDecimal maximumFillingDegree = unassignedWastebins.stream()
                .map(Wastebin::getFillingDegree)
                .max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        BigDecimal wastePayloadSum = assignedWastebins.stream()
                .map(Wastebin::getPayload)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal spareCapacity = BigDecimal.ZERO;
        long routeLengthSum = 0;
        for (Vehicle vehicle : assignedVehicles){
            List<Wastebin> route = getRoute(vehicle);
            long routeDuration = aggregateRoute(transportationPlan.getObjectDistances(), vehicle, route, row -> row.durationInTraffic.inSeconds, (a, b) -> a + b);
            long routeLength = aggregateRoute(transportationPlan.getObjectDistances(), vehicle, route, row -> row.distance.inMeters, (a, b) -> a + b);
            routeLengthSum += routeDuration;

            BigDecimal routePayload = route.stream()
                    .map(Wastebin::getPayload)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (routeLength > vehicle.getRange().multiply(BigDecimal.valueOf(1000)).longValue()) {
                // route is out of range for assigned vehicle
                hardScore -= 1;
            }

            if (routePayload.compareTo(vehicle.getCapacity()) > 0) {
                // payload on route is too high for vehicle
                hardScore -= 1;
            }
            spareCapacity = spareCapacity.add(vehicle.getCapacity().subtract(routePayload));
        }

        return BendableScore.valueOf(new int[]{ hardScore }, new int[]{
                maximumFillingDegree.multiply(BigDecimal.valueOf(-100)).intValue(),
                wastePayloadSum.intValue(),
                (int) -routeLengthSum,
                spareCapacity.multiply(BigDecimal.valueOf(-1)).intValue()
        });
    }

    public static List<Wastebin> getRoute(Vehicle vehicle) {
        List<Wastebin> route = new ArrayList<>();
        Wastebin current = vehicle.getNextWastebin();

        while (current != null) {
            route.add(current);
            current = current.getNextWastebin();
        }

        return route;
    }
    private <T> T aggregateRoute(Map<Object, Map<Object, DistanceMatrixElement>> objectDistances, Vehicle vehicle, List<Wastebin> wastebins, Function<DistanceMatrixElement, T> matrixElementSelector, BinaryOperator<T> combinator) {
        T aggregation = matrixElementSelector.apply(objectDistances.get(vehicle).get(wastebins.get(0)));

        for (int i = 0; i < wastebins.size() - 1; i++) {
            aggregation = combinator.apply(aggregation, matrixElementSelector.apply(objectDistances.get(wastebins.get(i)).get(wastebins.get(i + 1))));
        }
        return combinator.apply(aggregation, matrixElementSelector.apply(objectDistances.get(wastebins.get(wastebins.size() - 1)).get(vehicle)));
    }

}
