package at.ac.tuwien.swtm.common.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 25.05.2016.
 */
@MappedSuperclass
public class BaseEntity<I extends Serializable> {

    private I id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
