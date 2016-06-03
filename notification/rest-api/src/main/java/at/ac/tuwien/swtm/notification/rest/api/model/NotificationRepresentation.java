package at.ac.tuwien.swtm.notification.rest.api.model;

import at.ac.tuwien.swtm.common.rest.LocalDataTimeAdapter;
import at.ac.tuwien.swtm.notification.model.NotificationType;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
public class NotificationRepresentation {

    private Long id;
    private Long wastebinId;
    private NotificationType notificationType;
    private String content;
    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWastebinId() {
        return wastebinId;
    }

    public void setWastebinId(Long wastebinId) {
        this.wastebinId = wastebinId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlJavaTypeAdapter(LocalDataTimeAdapter.class)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
