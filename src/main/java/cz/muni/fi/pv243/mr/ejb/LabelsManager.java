package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Label;
import java.util.List;
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
        if (id < 0 || id >= DummyModel.getLabels().size()) {
            return null;
        }
        return DummyModel.getLabels().get((int) id);
    }

    public List<Label> getLabels() {
        return DummyModel.getLabels();
    }

}
