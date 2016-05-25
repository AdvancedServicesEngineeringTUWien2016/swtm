package at.ac.tuwien.swtm.common.config.cdi;

import javax.enterprise.inject.Default;
import javax.enterprise.util.AnnotationLiteral;

/**
 * Literal for {@link Default}
 *
 * @author Christian Beikov
 */
public class DefaultLiteral extends AnnotationLiteral<Default> implements Default {

    private static final long serialVersionUID = 3240069236025230401L;
}
