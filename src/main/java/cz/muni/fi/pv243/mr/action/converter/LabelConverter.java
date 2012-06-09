package cz.muni.fi.pv243.mr.action.converter;

import cz.muni.fi.pv243.mr.ejb.LabelsManager;
import cz.muni.fi.pv243.mr.model.Label;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@FacesConverter(value="labelConverter")
public class LabelConverter implements Converter {

    @Inject
    private LabelsManager labelsManager;

    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        long id = Long.parseLong(string);
        return labelsManager.getLabel(id);
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Label label = (Label) o;
        return Long.toString(label.getId());
    }

}
