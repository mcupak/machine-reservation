package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.model.User;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class UserBean implements Serializable {
    private static final long serialVersionUID = 938023984223423L;

    private User user;

    @Inject
    private UsersManager usersManager;

    public User getUser() {
        return user;
    }

    public void loadUser(String id) {
        user = usersManager.getUser(Long.parseLong(id));
    }
}
