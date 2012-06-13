package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.Label;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @author <a href="mailto:rhatlapa@redhat.com">Radim Hatlapatka</a>
 */
@Stateless
public class LabelsManager {

    @PersistenceContext(type= PersistenceContextType.EXTENDED)
    private EntityManager em;

    public Label getLabel(long id) {
        return em.find(Label.class, id);
    }

    public Label getLabel(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Label.class);
        Root<Label> labelRoot = cq.from(Label.class);
        cq = cq.where(cb.equal(labelRoot.get("name"), name));
        TypedQuery<Label> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    public List<Label> getLabels() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Label.class));
        return em.createQuery(cq).getResultList();
    }

    public void addLabel(Label label) {
        em.persist(label);
    }

    public void editLabel(Label label) {
        em.merge(label);
    }

    public void removeLabel(Label label) {
        em.remove(getLabel(label.getId()));
    }
}
