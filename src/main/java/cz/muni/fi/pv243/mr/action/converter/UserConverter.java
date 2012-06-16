package cz.muni.fi.pv243.mr.action.converter;

import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.model.User;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@FacesConverter(value="userConverter")
public class UserConverter implements Converter {

    @Inject
    private UsersManager usersManager;

    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null) return null;
        long id = Long.parseLong(string);
        return usersManager.getUser(id);
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) return null;
        User user = (User) o;
        return Long.toString(user.getId());
    }

}
