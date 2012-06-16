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
    
     public List<Machine> getAvailableMachinesIgnoringSpecifiedReservation(Reservation reservation, Date from, Date to) {
        if (from == null || to == null || from.after(to)) {
            return new ArrayList<Machine>();
        }
        if (reservation == null || reservation.getId() == null) {
            return getAvailableMachines(from, to);
        }
        TypedQuery<Machine> query = em.createQuery("SELECT ma FROM Machine ma "
                + "WHERE ma.id NOT IN "
                + "(SELECT m.id FROM Reservation r "
                + "INNER JOIN r.machines m "
                + "WHERE r.id != :reservationId AND (r.start >= :from AND r.start <= :to) OR (r.end >= :from AND r.end <= :to))",
                Machine.class);
        query.setParameter("reservationId", reservation.getId());
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
            if (label.getId() != null) {
                em.merge(label);
            }
        }
        TypedQuery<Reservation> q = em.createQuery(
                "SELECT r FROM Reservation r INNER JOIN r.machines m WHERE m = :machine",
                Reservation.class);
        q.setParameter("machine", machine);
        for (Reservation reservation : q.getResultList()) {
            em.remove(reservation);
        }
        em.remove(machineToRemove);
    }
}
