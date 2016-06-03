package at.ac.tuwien.swtm.admin.view;

import at.ac.tuwien.swtm.analytics.rest.api.WastebinsResource;
import at.ac.tuwien.swtm.analytics.rest.api.model.WastebinRepresentation;
import org.apache.deltaspike.core.api.scope.ViewAccessScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created
 * by Moritz Becker (moritz.becker@gmx.at)
 * on 03.06.2016.
 */
@Named
@ViewAccessScoped
public class FixedLocationDialogView implements Serializable {

    private WastebinRepresentation wastebin;

    @Inject
    private WastebinsResource wastebinsResource;

    public void load(Long wastebinId) {
        wastebin = wastebinsResource.getWastebin(wastebinId);
    }

    public WastebinRepresentation getWastebin() {
        return wastebin;
    }

    public void setWastebin(WastebinRepresentation wastebin) {
        this.wastebin = wastebin;
    }

    public void saveWastebin() {
        Response response = wastebinsResource.updateWastebin(wastebin);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Changes saved"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Saving failed", null));
        }
    }

}
