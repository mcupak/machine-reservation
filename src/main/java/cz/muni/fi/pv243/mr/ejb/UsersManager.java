/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rhatlapa
 */
@Stateless
public class UsersManager {

    @PersistenceContext
    private EntityManager entityManager;

    public User getUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("In order to find the user, his ID is required");
        }
        return entityManager.find(User.class, id);
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
