package at.ac.tuwien.swtm.planner.webapp.task;

import com.google.maps.model.DistanceMatrixElement;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
         *  3. we want to minimize the total route length
         *
         * Other assumptions:
         *  - One wastebin is either emptied completely or it is not touched at all.
         */
        List<Vehicle> vehicles = transportationPlan.getVehicles();
        List<Wastebin> wastebins = transportationPlan.getWastebins();
        List<Wastebin> assignedWastebins = wastebins.stream()
                .filter(wastebin -> wastebin.getAssignedVehicle() != null)
                .collect(Collectors.toList());
        Set<Wastebin> unassignedWastebins = new HashSet<>(wastebins);
        unassignedWastebins.removeAll(assignedWastebins);

        BigDecimal maximumFillingDegree = unassignedWastebins.stream()
                .map(Wastebin::getFillingDegree)
                .max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        BigDecimal payloadSum = assignedWastebins.stream()
                .map(Wastebin::getPayload)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Vehicle, List<Wastebin>> routes = assignedWastebins.stream()
                .collect(Collectors.groupingBy(Wastebin::getAssignedVehicle));

        long routeLengthSum = 0;
        for (Map.Entry<Vehicle, List<Wastebin>> route : routes.entrySet()) {
            long routeDuration = aggregateRoute(transportationPlan.getObjectDistances(), route, row -> row.durationInTraffic.inSeconds, (a, b) -> a + b);
            long routeLength = aggregateRoute(transportationPlan.getObjectDistances(), route, row -> row.distance.inMeters, (a, b) -> a + b);
            routeLengthSum += routeDuration;

            BigDecimal routePayload = route.getValue().stream()
                    .map(Wastebin::getPayload)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (routeLength > route.getKey().getRange().multiply(BigDecimal.valueOf(1000)).longValue()) {
                // route is out of range for assigned vehicle
                hardScore -= 1;
            }

            if (routePayload.compareTo(route.getKey().getCapacity()) > 0) {
                // payload on route is too high for vehicle
                hardScore -= 1;
            }
        }

        return BendableScore.valueOf(new int[]{ hardScore }, new int[]{
                maximumFillingDegree.multiply(BigDecimal.valueOf(-100)).intValue(),
                payloadSum.intValue(),
                (int) routeLengthSum
        });
    }

    private <T> T aggregateRoute(Map<Object, Map<Object, DistanceMatrixElement>> objectDistances, Map.Entry<Vehicle, List<Wastebin>> route, Function<DistanceMatrixElement, T> matrixElementSelector, BinaryOperator<T> combinator) {
        List<Wastebin> wastebins = route.getValue();
        T aggregation = matrixElementSelector.apply(objectDistances.get(route.getKey()).get(wastebins.get(0)));

        for (int i = 0; i < wastebins.size() - 1; i++) {
            aggregation = combinator.apply(aggregation, matrixElementSelector.apply(objectDistances.get(wastebins.get(i)).get(wastebins.get(i + 1))));
        }
        return combinator.apply(aggregation, matrixElementSelector.apply(objectDistances.get(wastebins.get(wastebins.size() - 1)).get(route.getKey())));
    }

}
