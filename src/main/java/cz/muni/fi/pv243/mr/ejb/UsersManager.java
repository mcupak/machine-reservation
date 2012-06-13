package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
public class UsersManager {

    @PersistenceContext
    private EntityManager em;

    public User getUser(Long id) {
        return em.find(User.class, id);
    }

    public User getUser(String email, String password) {
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
        em.persist(user);
    }

    public boolean deleteUser(Long id) {
        User user = getUser(id);
        if (user == null) {
            return false;
        } else {
            em.remove(user);
            return true;
        }
    }

    public void editUser(User user) {
        em.merge(user);
    }

}
