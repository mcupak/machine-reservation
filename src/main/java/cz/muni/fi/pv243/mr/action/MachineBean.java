package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.MachinesManager;
import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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

    @AssertTrue
    public boolean checkWhetherFromIsBeforeTo() {
        return from.before(to);
    }

    public Machine getMachine() {
        return machine;
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
    }

    public void loadReservations(Machine machine, Date from, Date to) {
        reservations = reservationsManager.getReservations(machine, from, to);
    }

}
