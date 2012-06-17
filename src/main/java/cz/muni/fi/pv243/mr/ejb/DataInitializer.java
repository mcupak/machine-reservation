/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.Label;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import cz.muni.fi.pv243.mr.model.User;
import cz.muni.fi.pv243.mr.model.UserRole;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rhatlapa
 */
@Singleton
@Startup
public class DataInitializer {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void initialize() {
        // initializing labels
        List<Label> labels = Arrays.asList(
                new Label("Lorem ipsum", null, "linux"),
                new Label("Lorem ipsum", null, "windows"),
                new Label("Lorem ipsum", null, "x86"));
        for (Label l : labels) {
            em.persist(l);
        }

        // initializing machines
        List<Machine> machines = Arrays.asList(
                new Machine("Lorem ipsum", null, Arrays.asList(labels.get(0), labels.get(2)), "anna"),
                new Machine("Lorem ipsum", null, Arrays.asList(labels.get(1), labels.get(2)), "aisa"));
        for (Machine m : machines) {
            em.persist(m);
        }

        // initializing users
        List<User> users = Arrays.asList(
                new User("admin@admin.cz", null, UserRole.ADMIN, "admin"),
                new User("guest@guest.cz", null, UserRole.COMMON, "guest"),
                new User("franta@frantov.cz", null, UserRole.COMMON, "franta")
                );

        for (User u : users) {
            em.persist(u);
        }

        long reservationId = 0l;
        List<Reservation> reservations = new ArrayList<Reservation>();
        final long hour = 1000 * 60 * 60;
        final long day = hour * 24;
        final Date now = new Date();
        long counter = 0;
        for (Machine machine : machines) {
            for (User user: users) {
                reservations.add(new Reservation(
                        new Date(now.getTime() + (counter + 1l) * day), null, Arrays.asList(machine), new Date(now.getTime() + counter * day), user));
                counter++;
            }
        }
        for (Reservation r : reservations) {
            em.persist(r);
        }
    }




}
