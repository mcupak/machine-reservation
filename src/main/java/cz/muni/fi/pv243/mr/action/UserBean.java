package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.model.User;
import cz.muni.fi.pv243.mr.model.UserRole;

import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

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

    public void newUser() {
        user = new User();
    }

    public void loadUser(String id) {
        user = usersManager.getUser(Long.parseLong(id));
    }

    public String saveUser() {
        usersManager.editUser(user);
        return "/admin/users.xml?faces-redirect=true";
    }

    public String createUser() {
        user.setPassword(UUID.randomUUID().toString());
        usersManager.addUser(user);
        return "/admin/users.xml?faces-redirect=true";
    }

    public SelectItem[] getUserRoles() {
        SelectItem[] items = new SelectItem[UserRole.values().length];
        int i = 0;
        for(UserRole role: UserRole.values()) {
            items[i++] = new SelectItem(role, role.toString());
        }
        return items;
    }
}
