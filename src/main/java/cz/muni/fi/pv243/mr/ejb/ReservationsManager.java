package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
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
    private EntityManager entityManager;

    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");

    public List<Reservation> getReservations(Machine machine, Date from, Date to) {
        // TODO
        return DummyModel.getReservations().get(machine);
    }

    public TimeZone getTimeZone() {
        return TIME_ZONE;
    }

}
