package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
@Named
public class MachinesManager {

    @Inject
    private EntityManager em;

    @Inject
    private LabelsManager labelsManager;

    @Inject
    private ReservationsManager reservationsManager;

    public Machine getMachine(long id) {
        return em.find(Machine.class, id);
    }

    /**
     * @return List of machine in the system
     */
    @Produces
    @Model
    public List<Machine> getMachines() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Machine.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Machine> getAvailableMachines(Date from, Date to) {
        if (from == null || to == null || from.after(to)) {
            return new ArrayList<Machine>();
        }
        TypedQuery<Machine> query = em.createQuery("SELECT ma FROM Machine ma "
                + "WHERE ma.id NOT IN "
                + "(SELECT m.id FROM Reservation r "
                + "INNER JOIN r.machines m "
                + "WHERE (r.start >= :from AND r.start <= :to) OR (r.end >= :from AND r.end <= :to))",
                Machine.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        return query.getResultList();
    }

    public List<Machine> getMachines(Collection<Label> labels) {
        if (labels == null || labels.isEmpty()) {
            return getMachines();
        }
        TypedQuery<Machine> query = em.createQuery("SELECT DISTINCT m FROM Machine m INNER JOIN Machine.labels l "
                + "WHERE l IN :labels",
                Machine.class);
        query.setParameter("labels", labels);

        return query.getResultList();
    }

    public List<Machine> getMachines(Collection<Label> labels, Date from, Date to) {
        if (from == null || to == null || from.after(to)) {
            return new ArrayList<Machine>();
        }
        if (labels == null) {
            if (from == null || to == null) {
                return getMachines();
            } else {
                return getAvailableMachines(from, to);
            }
        }
        if (labels.isEmpty()) {
            return getAvailableMachines(from, to);
        }
        // FIXME: it should be done in a query
        List<Machine> machines = getAvailableMachines(from, to);
        List<Machine> result = new ArrayList<Machine>();
        for (Machine machine: machines) {
            if (machine.getLabels().containsAll(labels)) {
                result.add(machine);
            }
        }
        return result;
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
        for (Label label : machineToRemove.getLabels()) {
            label.getMachines().remove(machine);
            labelsManager.editLabel(label);
        }
        for (Reservation reservation : reservationsManager.getReservations(machine)) {
            reservationsManager.removeReservation(reservation);
        }
        em.remove(machineToRemove);
    }
}
