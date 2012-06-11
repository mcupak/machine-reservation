package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import cz.muni.fi.pv243.mr.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
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

    public List<Reservation> getReservations(Machine machine, Date from, Date to) {
        // TODO
        return DummyModel.getReservations().get(machine);
    }

    public List<Reservation> getCurrentReservations(User user) {
        List<Reservation> reservations = new ArrayList<Reservation>();
        for (List<Reservation> rs: DummyModel.getReservations().values()) {
            for (Reservation reservation: rs) {
                if (reservation.getUser().getId().equals(user.getId())) {
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public TimeZone getTimeZone() {
        return TIME_ZONE;
    }

}
