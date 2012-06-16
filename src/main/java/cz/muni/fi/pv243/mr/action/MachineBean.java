package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.LabelsManager;
import cz.muni.fi.pv243.mr.ejb.MachinesManager;
import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.model.Label;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ViewScoped
@Named
public class MachineBean implements Serializable {

	private static final long serialVersionUID = 4214653875228526068L;
	private Date from = new Date();
    private Date to = new Date(from.getTime() + 1000l * 60l * 60l * 24l * 30l);
    private Machine machine;
    @Inject
    private MachinesManager machinesManager;
    @Inject
    private ReservationsManager reservationsManager;
    private List<Reservation> reservations;

    @Inject
    LabelsManager labelsManager;

    private Map<Label, Boolean> labels;

    @AssertTrue
    public boolean checkWhetherFromIsBeforeTo() {
        return from.before(to);
    }

    public Machine getMachine() {
        return machine;
    }

    public Map<Label, Boolean> getLabels() {
        return labels;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    @NotNull
    public Date getFrom() {
        return from;
    }

    @NotNull
    public Date getTo() {
        return to;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public TimeZone getTimeZone() {
        return reservationsManager.getTimeZone();
    }

    public void loadMachine(String machineId) {
        machine = machinesManager.getMachine(Long.parseLong(machineId));
        labels = new HashMap<Label, Boolean>();
        for (Label label : labelsManager.getLabels()) {
            labels.put(label, machine.getLabels().contains(label));
        }
    }

    public void loadReservations(Machine machine, Date from, Date to) {
        reservations = reservationsManager.getReservations(machine, from, to);
    }

    public String saveMachine() {
        machine.getLabels().clear();
        for (Map.Entry<Label, Boolean> entry : labels.entrySet()) {
            if (entry.getValue()) {
                machine.getLabels().add(entry.getKey());
            }
        }
        machinesManager.editMachine(machine);
//        machine = null;
//        labels = null;
        return "/admin/machines";
    }
}
