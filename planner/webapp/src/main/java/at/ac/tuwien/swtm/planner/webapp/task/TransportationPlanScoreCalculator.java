package at.ac.tuwien.swtm.planner.webapp.task;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.impl.PointImpl;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.buildin.bendablelong.BendableLongScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.math.BigDecimal;
import java.util.*;
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

        BigDecimal routeLengthSum = BigDecimal.ZERO;
        for (Map.Entry<Vehicle, List<Wastebin>> route : routes.entrySet()) {
            List<Point> points = route.getValue().stream()
                    .map(wastebin -> new PointImpl(wastebin.getLatitude(), wastebin.getLongitude(), SpatialContext.GEO))
                    .collect(Collectors.toList());
            points.add(0, new PointImpl(route.getKey().getLatitude(), route.getKey().getLongitude(), SpatialContext.GEO));

            BigDecimal routeLength = calculateRoundTripLength(points);
            routeLengthSum = routeLengthSum.add(routeLength);

            BigDecimal routePayload = route.getValue().stream()
                    .map(Wastebin::getPayload)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (routeLength.compareTo(route.getKey().getRange()) > 0) {
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
                routeLengthSum.intValue()
        });
    }

    private BigDecimal calculateRoundTripLength(List<Point> points) {
        if (points.size() <= 1) {
            return BigDecimal.ZERO;
        }
        BigDecimal length = BigDecimal.ZERO;
        for (int i = 0; i < points.size() - 1; i++) {
            length = length.add(BigDecimal.valueOf(SpatialContext.GEO.calcDistance(points.get(i), points.get(i + 1))));
        }
        return length.add(BigDecimal.valueOf(SpatialContext.GEO.calcDistance(points.get(points.size() - 1), points.get(0))));
    }

}
