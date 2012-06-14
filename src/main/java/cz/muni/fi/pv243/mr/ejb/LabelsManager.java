package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.Label;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
@Named
public class LabelsManager {

    @Inject
    private EntityManager em;

    public Label getLabel(long id) {
        return em.find(Label.class, id);
    }

    public Label getLabel(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Label> cq = cb.createQuery(Label.class);
        Root<Label> labelRoot = cq.from(Label.class);
        cq = cq.where(cb.equal(labelRoot.get("name"), name));
        TypedQuery<Label> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    public List<Label> getLabels(Reservation reservation) {
        List<Label> labels = new ArrayList<Label>();
        Iterator<Machine> it = reservation.getMachines().iterator();
        if (it.hasNext()) {            
            labels.addAll(it.next().getLabels());
            while (it.hasNext()) {
                Machine m = it.next();
                labels.retainAll(m.getLabels());                
            }
        }        
        return labels;
    }

    @Produces
    @Model
    public List<Label> getLabels() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Label.class));
        return em.createQuery(cq).getResultList();
    }

    public void addLabel(Label label) {
        em.persist(label);
    }

    public void editLabel(Label label) {
        if (getLabel(label.getId()) != null) {
            em.merge(label);
        }
    }

    public void removeLabel(Label label) {
        em.remove(getLabel(label.getId()));
    }
}
