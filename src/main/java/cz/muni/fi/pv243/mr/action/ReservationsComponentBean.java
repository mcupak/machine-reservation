package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.model.Reservation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ApplicationScoped
@Named
public class ReservationsComponentBean implements Serializable {

	private static final long serialVersionUID = 3481341972471882514L;
	@Inject
    private ReservationsManager reservationsManager;

    public Map<Date, List<Reservation>> getOrganizedReservations(List<Date> days, List<Reservation> reservations) {
        Map<Date, List<Reservation>> organized = new HashMap<Date, List<Reservation>>();
        for (Date day: days) {
            List<Reservation> dayReservations = new ArrayList<Reservation>();
            for (Reservation reservation: reservations) {
                if (inDay(day, reservation)) {
                    dayReservations.add(reservation);
                }
                if (!dayReservations.isEmpty()) {
                    organized.put(day, dayReservations);
                }
            }
        }
        return organized;
    }

    public List<Date> getDays(Date from, Date to) {
        if (!from.before(to)) {
            throw new IllegalArgumentException("The [from] date has to be before [to] date, but it doesn't ("+from.getTime()+", "+to.getTime()+").");
        }
        List<Date> days = new ArrayList<Date>();
        final long day = 1000 * 60 * 60 * 24;
        for (long i=from.getTime(); i<to.getTime(); i+=day) {
            days.add(new Date(i));
        }
        if (!isTheSameDay(days.get(days.size() - 1), to)) {
            days.add(to);
        }
        return days;
    }

    public TimeZone getTimeZone() {
        return reservationsManager.getTimeZone();
    }

    private boolean isTheSameDay(Date first, Date second) {
        Calendar f = Calendar.getInstance(reservationsManager.getTimeZone());
        f.setTime(first);
        Calendar s = Calendar.getInstance(reservationsManager.getTimeZone());
        s.setTime(second);
        return f.get(Calendar.DAY_OF_YEAR) == s.get(Calendar.DAY_OF_YEAR) && f.get(Calendar.YEAR) == s.get(Calendar.YEAR);
    }

    private boolean inDay(Date day, Reservation reservation) {
        return (reservation.getStart().before(day) && reservation.getEnd().after(day)) || isTheSameDay(day, reservation.getStart()) || isTheSameDay(day, reservation.getEnd());
    }

}
