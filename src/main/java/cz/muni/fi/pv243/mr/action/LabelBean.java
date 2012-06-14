package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.LabelsManager;
import cz.muni.fi.pv243.mr.model.Label;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ViewScoped
@Named
public class LabelBean implements Serializable {

	private static final long serialVersionUID = 6490857639238136899L;
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
