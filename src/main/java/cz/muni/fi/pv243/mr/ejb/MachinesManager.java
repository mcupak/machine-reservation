package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Label;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Stateless
public class MachinesManager {

    @PersistenceContext
    private EntityManager entityManager;

    public Machine getMachine(long id) {
        // TODO
        if (id < 0 || id >= DummyModel.getLabels().size()) {
            return null;
        }
        return DummyModel.getMachines().get((int)id);
    }

    public List<Machine> getMachines() {
        // TODO
        return DummyModel.getMachines();
    }

    public List<Machine> getMachines(Collection<Label> labels, Date from, Date to) {
        // TODO
        if (labels == null) {
            return getMachines();
        }
        List<Machine> result = findMachinesWithLabels(getMachines(), labels);
        for (Machine machine: getMachines()) {
            if (isAvailable(machine, from, to)) {
                result.add(machine);
            }
        }
        return result;
    }

    private List<Machine> findMachinesWithLabels(List<Machine> machines, Collection<Label> labels) {
        List<Machine> result = new ArrayList<Machine>();
        for (Machine machine: machines) {
            boolean found = true;
            Set<Label> machineLabels = new HashSet<Label>(machine.getLabels());
            for (Label label: labels) {
                if (!machineLabels.contains(label)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                result.add(machine);
            }
        }
        return result;
    }

    private boolean isAvailable(Machine machine, Date from, Date to) {
        for (Reservation reservation: DummyModel.getReservations().get(machine)) {
            if (reservation.getEnd().equals(to) || reservation.getStart().equals(from) || (reservation.getStart().before(from) && reservation.getEnd().after(from)) || (reservation.getStart().before(to) && reservation.getEnd().after(to))) {
                return false;
            }
        }
        return true;
    }

}
