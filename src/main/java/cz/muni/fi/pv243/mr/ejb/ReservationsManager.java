package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
public class ReservationsManager {

    @PersistenceContext
    private EntityManager em;
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

    // TODO: permissions
    public boolean deleteReservation(Long id) {
        Reservation reservation = getReservation(id);
        if (reservation == null) {
            return false;
        } else {
            em.remove(reservation);
            return true;
        }
    }

    public Reservation getReservation(Long id) {
        return em.find(Reservation.class, id);
    }

    public void reserve(User user, Machine machine, Date from, Date to) {
        // TODO
    }

    public void addReservation(Reservation reservation) {
        em.persist(reservation);
    }

    public void removeReservation(Reservation reservation) {
        Reservation resToRemove = getReservation(reservation.getId());
        em.remove(resToRemove);
    }

    public void cancelReservation(User user, Reservation reservation) {
        Reservation resToRemove = getReservation(reservation.getId());
        if (resToRemove.getUser().equals(user)) {
            em.remove(resToRemove);
        }
    }

    public List<Reservation> getReservations(Machine machine, Date from, Date to) {
        TypedQuery<Reservation> q = em.createQuery(
                "SELECT r FROM reservations r "
                + "WHERE r.machine = :machine "
                + "AND (r.start > :from OR r.start = :from) AND (r.end < :to OR r.end = :to)",
                Reservation.class);
        q.setParameter("machine", machine);
        q.setParameter("from", from);
        q.setParameter("to", to);
        return q.getResultList();
    }

    public List<Reservation> getCurrentReservations(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Reservation.class);
        Root<Reservation> reservationRoot = cq.from(Reservation.class);
        cq = cq.where(cb.equal(reservationRoot.get("user"), user));
        TypedQuery<Reservation> q = em.createQuery(cq);
        return q.getResultList();
//        List<Reservation> reservations = new ArrayList<Reservation>();
//        for (List<Reservation> rs: DummyModel.getReservations().values()) {
//            for (Reservation reservation: rs) {
//                if (reservation.getUser().getId().equals(user.getId())) {
//                    reservations.add(reservation);
//                }
//            }
//        }
//        return reservations;
    }

    public TimeZone getTimeZone() {
        return TIME_ZONE;
    }
}
