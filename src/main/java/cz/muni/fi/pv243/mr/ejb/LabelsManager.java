package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Label;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Stateless
public class LabelsManager {

    @PersistenceContext
    private EntityManager em;

    public Label getLabel(long id) {
        return em.find(Label.class, id);
        //return DummyModel.getLabels().get((int) id);
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
