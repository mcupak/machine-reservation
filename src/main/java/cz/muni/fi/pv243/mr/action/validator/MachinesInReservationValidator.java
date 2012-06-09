package cz.muni.fi.pv243.mr.action.validator;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@FacesValidator(value="machinesInReserveationValidator")
public class MachinesInReservationValidator implements Validator {

    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The list of machines assigned to the reservation can't be empty.", "the list of machines assigned to the reservation can't be empty");
        if (o == null) {
            throw new ValidatorException(msg);
        }
        List list = (List) o;
        if (list.isEmpty()) {
            throw new ValidatorException(msg);
        }
    }

}
