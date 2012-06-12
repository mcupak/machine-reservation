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
                new Label("Lorem ipsum", 0l, new ArrayList<Machine>(), "linux"),
                new Label("Lorem ipsum", 1l, new ArrayList<Machine>(), "windows"),
                new Label("Lorem ipsum", 2l, new ArrayList<Machine>(), "x86"));
        for (Label l : labels) {
            em.persist(labels);
        }
        
        // initializing machines
        List<Machine> machines = Arrays.asList(
                new Machine("Lorem ipsum", 0l, Arrays.asList(labels.get(0), labels.get(2)), "anna"),
                new Machine("Lorem ipsum", 1l, Arrays.asList(labels.get(1), labels.get(2)), "aisa"));
        for (Machine m : machines) {
            em.persist(m);
        }
        
        // initializing users
        List<User> users = Arrays.asList(
                new User("admin@admin", 0l, UserRole.ADMIN, "admin"),
                new User("guest@guest", 1l, UserRole.COMMON, "guest"),
                new User("franta@frantov.cz", 2l, UserRole.COMMON, "franta")
                );
        
        for (User u : users) {
            em.persist(u);
        }
        
        long reservationId = 0l;
        List<Reservation> reservations = new ArrayList<Reservation>();
        for (Machine machine : machines) {
            Date now = new Date();
            for (int i = 0; i < 10; i++) {
                final long hour = 1000 * 60 * 60;
                final long day = hour * 24;
                Date start = new Date(now.getTime() + reservationId * hour + i * 5 * hour);
                Reservation reservation = new Reservation(new Date(start.getTime() + day), reservationId, Arrays.
                        asList(machine).get(0), start, users.get(1));
                reservations.add(reservation);
                reservationId++;
            }
        }
//        for (Reservation r : reservations) {
//            em.persist(r);
//        }
    }
    



}
