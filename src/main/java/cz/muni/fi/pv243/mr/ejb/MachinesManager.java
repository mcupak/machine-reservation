package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
public class MachinesManager {

    @PersistenceContext
    private EntityManager em;

    public Machine getMachine(long id) {
        return em.find(Machine.class, id);
    }

    /**
     *
     * @return List of machine in the system
     */
    public List<Machine> getMachines() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Machine.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Machine> getMachines(Collection<Label> labels, Date from, Date to) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Machine.class);
        Root<Machine> machineRoot = cq.from(Machine.class);
        TypedQuery<Machine> q = em.createQuery(cq);

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
        if (em.find(Machine.class, machine.getId()) != null) {
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
