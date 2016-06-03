package at.ac.tuwien.swtm.notification.model;

import at.ac.tuwien.swtm.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Entity
public class Notification extends BaseEntity<Long> {

    private NotificationType notificationType;
    private Long wastebinId;
    private String content;
    private LocalDateTime timestamp;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    @NotNull
    @Column(nullable = false)
    public Long getWastebinId() {
        return wastebinId;
    }

    public void setWastebinId(Long wastebinId) {
        this.wastebinId = wastebinId;
    }

    @NotNull
    @Column(nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull
    @Column(nullable = false)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
