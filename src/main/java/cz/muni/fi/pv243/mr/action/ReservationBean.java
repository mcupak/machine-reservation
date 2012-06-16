package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.LabelsManager;
import cz.muni.fi.pv243.mr.ejb.MachinesManager;
import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.logging.ReservationsLogger;
import cz.muni.fi.pv243.mr.model.Label;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import cz.muni.fi.pv243.mr.model.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ViewScoped
@Named
public class ReservationBean implements Serializable {

	private static final long serialVersionUID = -6210138663980366244L;
	private static final String CURRENT_PAGE = "reservation.xhtml";
	private static final String NEXT_PAGE = "reservations.xhtml";
	@Inject
    @Logged
    private User user;
    @Inject
    private LabelsManager labelsManager;
    @Inject
    private MachinesManager machinesManager;
    @Inject
    private ReservationsManager reservationsManager;
    @Inject
    private UsersManager usersManager;
    @Inject
    private ReservationsLogger reservationsLogger;
    private Reservation reservation;
    private List<Label> selectedLabels;
    private List<Machine> machines;
    private List<Label> labels;
    private String title = "New Reservation";
    private Date from;
    private Date to;

    @PostConstruct
    public void load() {
        reservation = new Reservation();
        reservation.setUser(usersManager.getUser(user.getId()));
        from = new Date();
        to = new Date(from.getTime() + 1000l * 60l * 60l * 24l);
        filter();
        labels = labelsManager.getLabels();
    }

    public void loadReservation(String id) {
        if (id == null || id.isEmpty()) {
            return;
        }
        Reservation r = reservationsManager.getReservation(Long.parseLong(id));
        if (r == null) {
            return;
        }
        reservation = r;
        title = "Edit Reservation";
        selectedLabels = labelsManager.getLabels(reservation);
        machines.clear();
        machines.addAll(reservation.getMachines());
    }

    public void filter() {
        machines = machinesManager.getMachines(selectedLabels, from, to, reservation);
        reservation.getMachines().clear();
    }

    public String persist() {
        boolean success = false;
        if (reservation.getId() == null) {
            success = reservationsManager.addReservation(reservation);
            if (success) {
                for (Machine m: reservation.getMachines()) {
                    reservationsLogger.created(m.getName(), reservation.getUser().getEmail());
                }
            }
        } else {
            success = reservationsManager.editReservation(reservation);
        }
        if (success) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservation has been saved.", "reservation has been saved"));
            return NEXT_PAGE;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Reservation can't be saved.", "reservation can't be saved"));
            return CURRENT_PAGE;
        }
    }

    public Date getFrom() {
        return from;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public List<Label> getSelectedLabels() {
        return selectedLabels;
    }

    public TimeZone getTimeZone() {
        return reservationsManager.getTimeZone();
    }

    public String getTitle() {
        return title;
    }

    public Date getTo() {
        return to;
    }

    public void setFrom(Date from) {
        this.reservation.setStart(from);
        this.from = from;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setSelectedLabels(List<Label> selectedLabels) {
        this.selectedLabels = selectedLabels;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setTo(Date to) {
        this.to = to;
        this.reservation.setEnd(to);
    }

}