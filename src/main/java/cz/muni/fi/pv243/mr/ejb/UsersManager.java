package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.logging.UsersLogger;
import cz.muni.fi.pv243.mr.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jboss.solder.logging.Logger;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
@Named
public class UsersManager {

    @Inject
    private EntityManager em;
    @Inject
    private Logger log;
    @Inject
    private UsersLogger usersLogger;

    public User getUser(Long id) {
        return em.find(User.class, id);
    }

    @Produces
    @Model
    public List<User> getUsers() {
        log.debug("Retrieving all users in the system");
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return em.createQuery(cq).getResultList();
    }

    public User getUser(String email, String password) {
        log.debug("Retrieving user based on username and password");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq = cq.where(cb.equal(userRoot.get("email"), email));
        TypedQuery<User> q = em.createQuery(cq);
        User user = q.getSingleResult();
        if ((user != null) && (user.getPassword().equals(password))) {
            return user;
        } else {
            return null;
        }
    }

    public void addUser(User user) {
        if (user != null) {
            em.persist(user);
            usersLogger.created(user.getEmail(), user.getUserRole().toString());
        }
    }

    public boolean deleteUser(Long id) {
        User user = getUser(id);
        if (user == null) {
            return false;
        } else {
            em.remove(user);
            usersLogger.deleted(user.getEmail());
            return true;
        }
    }

    public void editUser(User user) {
        if (getUser(user.getId()) != null) {
            em.merge(user);
            usersLogger.updated(user.getId());
        }
    }
}
