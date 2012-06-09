package cz.muni.fi.pv243.mr.action.converter;


import cz.muni.fi.pv243.mr.ejb.MachinesManager;
import cz.muni.fi.pv243.mr.model.Machine;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@FacesConverter(value="machineConverter")
public class MachineConverter implements Converter {

    @Inject
    private MachinesManager machinesManager;

    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        long id = Long.parseLong(string);
        return machinesManager.getMachine(id);
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Machine machine = (Machine) o;
        return Long.toString(machine.getId());
    }

}
