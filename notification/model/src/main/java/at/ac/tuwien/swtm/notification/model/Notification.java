package at.ac.tuwien.swtm.notification.model;

import at.ac.tuwien.swtm.common.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@Entity
public class Notification extends BaseEntity<Long> {

    private String content;

    @NotNull
    @Column(nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
