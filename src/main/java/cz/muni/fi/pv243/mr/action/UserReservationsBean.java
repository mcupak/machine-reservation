package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.model.Reservation;
import cz.muni.fi.pv243.mr.model.User;
import java.io.Serializable;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@SessionScoped
@Named
public class UserReservationsBean implements Serializable {

//    @Inject
//    @Logged
    // TODO
    private User user;
    @Inject
    private ReservationsManager reservationsManager;
    @Inject
    private UsersManager usersManager;
    private List<Reservation> reservations;

    @PostConstruct
    public void load() {
        reservations = reservationsManager.getCurrentReservations(usersManager.getUser(1l));
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public TimeZone getTimeZone() {
        return reservationsManager.getTimeZone();
    }

    public User getUser() {
        return user;
    }

}
