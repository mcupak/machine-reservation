package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.LabelsManager;
import cz.muni.fi.pv243.mr.ejb.MachinesManager;
import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.model.Label;
import cz.muni.fi.pv243.mr.model.Machine;
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
public class ReserveBean implements Serializable {

    @Inject
    private LabelsManager labelsManager;
    @Inject
    private MachinesManager machinesManager;
    @Inject
    private ReservationsManager reservationsManager;
    private List<Label> selectedLabels;
    private List<Machine> selectedMachines;
    private List<Machine> machines;
    private List<Label> labels;
    private Date from = new Date();
    private Date to = new Date(from.getTime() + 1000l * 60l * 60l * 24l);;

    @AssertTrue
    public boolean checkWhetherFromIsBeforeTo() {
        return from.before(to);
    }

    @PostConstruct
    public void load() {
        filter();
        labels = labelsManager.getLabels();
    }

    public void filter() {
        machines = machinesManager.getMachines(selectedLabels, from, to);
        System.out.println("Filter done: " + machines);
    }

    public void reserve() {
        // TODO
    }

    public List<Label> getLabels() {
        return labels;
    }

    public Date getFrom() {
        return from;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<Label> getSelectedLabels() {
        return selectedLabels;
    }

    public List<Machine> getSelectedMachines() {
        return selectedMachines;
    }

    public TimeZone getTimeZone() {
        return reservationsManager.getTimeZone();
    }

    public Date getTo() {
        return to;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setSelectedLabels(List<Label> selectedLabels) {
        this.selectedLabels = selectedLabels;
    }

    public void setSelectedMachines(List<Machine> selectedMachines) {
        this.selectedMachines = selectedMachines;
    }

    public void setTo(Date to) {
        this.to = to;
    }

}
