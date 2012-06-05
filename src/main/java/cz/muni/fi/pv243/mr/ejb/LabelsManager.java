package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Label;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Stateless
public class LabelsManager {

    @PersistenceContext
    private EntityManager entityManager;

    public Label getLabel(long id) {
        return DummyModel.getLabels().get((int) id);
    }

}
