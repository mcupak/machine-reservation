package cz.muni.fi.pv243.mr.ejb;

import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Machine;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Stateless
public class MachinesManager {

    @PersistenceContext
    private EntityManager entityManager;

    public Machine getMachine(long id) {
        return DummyModel.getMachines().get((int)id);
    }

    public List<Machine> getMachines() {
        return DummyModel.getMachines();
    }

}
