package at.ac.tuwien.swtm.planner.webapp.task;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 02.06.2016.
 */
public class WastebinDifficultyComparator implements Comparator<Wastebin> {
    @Override
    public int compare(Wastebin o1, Wastebin o2) {
        return new CompareToBuilder()
                .append(o1.getPayload(), o2.getPayload())
                .toComparison();
    }
}
