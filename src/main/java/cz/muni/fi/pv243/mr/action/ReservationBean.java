package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.LabelsManager;
import cz.muni.fi.pv243.mr.ejb.MachinesManager;
import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.model.Label;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import cz.muni.fi.pv243.mr.model.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ViewScoped
@Named
public class ReservationBean implements Serializable {

    @Inject
    @Logged
    private User user;
    @Inject
    private LabelsManager labelsManager;
    @Inject
    private MachinesManager machinesManager;
    @Inject
    private ReservationsManager reservationsManager;
    private Reservation reservation;
    private List<Label> selectedLabels;
    private List<Machine> machines;
    private List<Label> labels;
    private String title = "New Reservation";

    @AssertTrue
    public boolean checkWhetherFromIsBeforeTo() {
        return reservation.getStart().before(reservation.getEnd());
    }

    @PostConstruct
    public void load() {
        reservation = new Reservation();
        reservation.setStart(new Date());
        reservation.setEnd(new Date(reservation.getStart().getTime() + 1000l * 60l * 60l * 24l));
        filter();
        labels = labelsManager.getLabels();
    }

    public void loadReservation(String id) {
        if (id == null || id.isEmpty()) {
            return;
        }
        reservation = reservationsManager.getReservation(Long.parseLong(id));
        title = "Edit Reservation";
    }

    public void filter() {
        machines = machinesManager.getMachines(selectedLabels, reservation.getStart(), reservation.getEnd());
    }

    public void persist() {
        if (reservation.getId() == null) {
            reservationsManager.addReservation(reservation);
        } else {
            reservationsManager.editReservation(reservation);
        }
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

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setSelectedLabels(List<Label> selectedLabels) {
        this.selectedLabels = selectedLabels;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
