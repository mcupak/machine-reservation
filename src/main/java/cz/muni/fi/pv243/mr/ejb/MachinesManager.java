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
import javax.persistence.criteria.CriteriaQuery;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Stateless
public class MachinesManager {

    @PersistenceContext
    private EntityManager em;

    public Machine getMachine(long id) {
        return em.find(Machine.class, id);
    }

    public List<Machine> getMachines() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Machine.class));
        return em.createQuery(cq).getResultList();
        // TODO
        //return DummyModel.getMachines();
    }

    public List<Machine> getMachines(Collection<Label> labels, Date from, Date to) {
        // TODO

        List<Machine> result = findMachinesWithLabels(getMachines(), labels);
        for (Machine machine : getMachines()) {
            if (isAvailable(machine, from, to)) {
                result.add(machine);
            }
        }
        return result;
    }

    private List<Machine> findMachinesWithLabels(List<Machine> machines, Collection<Label> labels) {
        if (labels == null) {
            return machines;
        }
        List<Machine> result = new ArrayList<Machine>();
        for (Machine machine : machines) {
            boolean found = true;
            Set<Label> machineLabels = new HashSet<Label>(machine.getLabels());
            for (Label label : labels) {
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
        for (Reservation reservation : DummyModel.getReservations().get(machine)) {
            if (reservation.getEnd().equals(to) || reservation.getStart().equals(from) || (reservation.
                    getStart().before(from) && reservation.getEnd().after(from)) || (reservation.
                    getStart().before(to) && reservation.getEnd().after(to))) {
                return false;
            }
        }
        return true;
    }

    public void addMachine(Machine machine) {
        if (machine == null) {
            throw new IllegalArgumentException("No machine given for adding");
        }
        em.persist(machine);

    }

    public void editMachine(Machine machine) {
        if (machine == null) {
            throw new IllegalArgumentException("No machine given for editing");
        }
        if (em.find(Machine.class, machine.getId())!=null) {
            em.merge(machine);
        }
    }
    
    public void removeMachine(Machine machine) {
        if (machine == null) {
            throw new IllegalArgumentException("No machine given for removing");
        }
        Machine machineToRemove = em.find(Machine.class, machine.getId());
        em.remove(machineToRemove);
    }
}
