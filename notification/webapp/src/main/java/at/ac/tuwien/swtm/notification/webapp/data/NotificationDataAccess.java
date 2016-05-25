package at.ac.tuwien.swtm.notification.webapp.data;

import at.ac.tuwien.swtm.notification.model.Notification;
import com.blazebit.persistence.CriteriaBuilderFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Stateless
public class NotificationDataAccess {

    @Inject
    private EntityManager em;

    @Inject
    private CriteriaBuilderFactory cbf;

    public List<Notification> getNotifications() {
        return cbf.create(em, Notification.class).getResultList();
    }
}
