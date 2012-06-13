package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.*;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
public class ReservationsManager {

    @Inject
    private EntityManager em;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

    public Reservation getReservation(Long id) {
        return em.find(Reservation.class, id);
    }

    /**
     * Adds reservation to system, if there is free slot for all the machines required for the reservation
     * @param reservation
     * @return 
     */
    public boolean addReservation(Reservation reservation) {
        // checking if it is possible to reserve the whole set of machines
        for (Machine m : reservation.getMachines()) {
            if (getReservations(m, reservation.getStart(), reservation.getEnd()) != null) {
                return false;
            }
        }
        em.persist(reservation);
        return true;
    }

    public void removeReservation(Reservation reservation) {
        Reservation resToRemove = getReservation(reservation.getId());
        em.remove(resToRemove);
    }

    public boolean cancelReservation(User user, Reservation reservation) {
        Reservation resToRemove = getReservation(reservation.getId());
        if (resToRemove != null && resToRemove.getUser().equals(user)) {
            em.remove(resToRemove);
            return true;
        } else {
            return false;
        }
    }

    public List<Reservation> getReservations(Machine machine, Date from, Date to) {
        TypedQuery<Reservation> q = em.createQuery(
                "SELECT r FROM Reservation r "
                + "INNER JOIN r.machines m WHERE m = :machine "
                + "AND r.start >= :from AND r.end <= :to",
                Reservation.class);
        q.setParameter("machine", machine);
        q.setParameter("from", from);
        q.setParameter("to", to);
        return q.getResultList();
    }

    public List<Reservation> getCurrentReservations(User user) {
        TypedQuery<Reservation> q = em.createQuery(
                "SELECT r FROM Reservation r "
                + "WHERE r.user = :user "
                + "AND (r.start >= now() OR r.end >= now())",
                Reservation.class);
        q.setParameter("user", user);
        return q.getResultList();
    }

    public void editReservation(Reservation reservation) {
        if (getReservation(reservation.getId()) != null) {
            em.merge(reservation);
        }
    }

    public TimeZone getTimeZone() {
        return TIME_ZONE;
    }
}
