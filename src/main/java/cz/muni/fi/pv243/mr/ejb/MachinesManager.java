package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.*;
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

    /**
     * does not like the JOIN, I haven't figure out solution => doesn't work
     * @param from
     * @param to
     * @return 
     */
    public List<Machine> getFreeMachines(Date from, Date to) {
        TypedQuery<Machine> query = em.createQuery("SELECT m FROM Machine m "
                + "WHERE m NOT IN "
                + "(SELECT m FROM Reservation r "
                + "INNER JOIN Reservation.machines INNER JOIN Machine "
                + "WHERE r.start >= :from AND r.end <= :to"
                + ")",
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
        // TODO: tahle metoda je OPRAVDU!!! potreba dodelat
        if (labels == null) {
            if (from == null || to == null) {
                return getMachines();
            } else {
//                return getFreeMachines(from, to);
                return getMachines();
            }
        }
//        // FIXME: it should be done all in one query
//        Set<Machine> byLabels = null;
//        for (Label label: labels) {
//            if (byLabels == null) {
//                byLabels = new HashSet<Machine>(label.getMachines());
//                continue;
//            }
//            byLabels.retainAll(label.getMachines());
//        }
//        if (byLabels == null || byLabels.isEmpty()) {
//            return new ArrayList<Machine>();
//        }
        TypedQuery<Machine> query = em.createQuery("SELECT m FROM Machine m "
                + "RIGHT JOIN Reservation.machines "
                + "RIGHT JOIN Reservation "
                + "WHERE m IN :machines",
                Machine.class);
        query.setParameter("machines", getMachines(labels));
        return query.getResultList();
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
