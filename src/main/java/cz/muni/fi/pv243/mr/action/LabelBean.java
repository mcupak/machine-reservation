package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.LabelsManager;
import cz.muni.fi.pv243.mr.logging.ReservationsLogger;
import cz.muni.fi.pv243.mr.model.Label;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ViewScoped
@Named
public class LabelBean implements Serializable {

    private Label label;
    @EJB
    private LabelsManager labelsManager;

    public Label getLabel() {
        return label;
    }

    public void loadLabel(String labelId) {
        label = labelsManager.getLabel(Long.parseLong(labelId));
    }

}
