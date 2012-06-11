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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@SessionScoped
@Named
public class UserReservationsBean implements Serializable {

    @Inject
    @Logged
    private User user;
    @Inject
    private ReservationsManager reservationsManager;
    @Inject
    private UsersManager usersManager;
    private List<Reservation> reservations;

    @PostConstruct
    public void load() {
        reservations = reservationsManager.getCurrentReservations(user);
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

    public void delete(long reservationId) {
        boolean success = reservationsManager.deleteReservation(reservationId);
        if (success) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservation has been deeleted.", "reservation has been deeleted"));
            load();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The reservaton can't be deleted.", "the reservaton can't be deleted"));
        }
    }

}