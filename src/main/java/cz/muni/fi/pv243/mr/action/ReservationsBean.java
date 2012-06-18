package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.action.ReservationsComponentBean;
import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.logging.ReservationsLogger;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import cz.muni.fi.pv243.mr.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ViewScoped
@Named
public class ReservationsBean implements Serializable {
	private static final long serialVersionUID = 324509230820348234L;

	@Inject
	private ReservationsComponentBean reservationsComponentBean;
	
	@Inject
	private UsersManager usersManager;

	@Inject
	private ReservationsManager reservationsManager;
	
    @Inject
    private ReservationsLogger reservationsLogger;
    
    @Inject
    @Logged
    private User adminUser;

	private Map<Date, List<Reservation>> organizedReservations;

	private Date from;

	private Date to;

	private User user = null;

	private Machine machine = null;

	@PostConstruct
	private void initializeFields() {
		from = reservationsComponentBean.getToday();
		to = reservationsComponentBean.getNextWeek();
	}

	public void loadReservations() {
		List<Reservation> ret = reservationsManager.getReservations(from, to,
				user, machine);
		organizedReservations = reservationsComponentBean
				.getOrganizedReservations(getDays(), ret);
	}

	public List<Date> getDays() {
		return reservationsComponentBean.getDays(from, to);
	}

	public Map<Date, List<Reservation>> getOrganizedReservations() {
		return organizedReservations;
	}

	public void setOrganizedReservations(
			Map<Date, List<Reservation>> organizedReservations) {
		this.organizedReservations = organizedReservations;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

    public void delete(long reservationId) {
        // FIXME: workaround
    	Reservation reservation = reservationsManager.getReservation(reservationId);
        boolean success = reservationsManager.cancelReservation(usersManager.getUser(adminUser.getId()), reservation);
        if (success) {
            for (Machine m: reservation.getMachines()) {
                reservationsLogger.deleted(m.getName(), reservation.getUser().getEmail());
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservation has been deleted.", "reservation has been deleted"));
            loadReservations();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The reservaton can't be deleted.", "the reservaton can't be deleted"));
        }
    }
}
