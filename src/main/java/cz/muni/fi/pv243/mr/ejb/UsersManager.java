package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
public class UsersManager {

    @PersistenceContext
    private EntityManager entityManager;

    public User getUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("In order to find the user, his ID is required");
        }
        // TODO
//        return entityManager.find(User.class, id);
        return DummyModel.getUsers().get((int) id.longValue());
    }

    public User getUser(String email, String password) {
        // TODO
        if ("admin@admin".equals(email) && "admin".equals(password)) {
            return DummyModel.getUsers().get(0);
        } else if ("guest@guest".equals(email) && "guest".equals(password)) {
            return DummyModel.getUsers().get(1);
        } else {
            return null;
        }
    }

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public boolean deleteUser(Long id) {
        User user = getUser(id);
        if (user == null) {
            return false;
        } else {
            entityManager.remove(user);
            return true;
        }
    }

    public void editUser(User user) {
        entityManager.merge(user);
    }

}
