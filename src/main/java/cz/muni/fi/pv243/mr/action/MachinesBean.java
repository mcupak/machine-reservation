package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.MachinesManager;
import cz.muni.fi.pv243.mr.model.DummyModel;
import cz.muni.fi.pv243.mr.model.Machine;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@SessionScoped
@Named
public class MachinesBean implements Serializable {

    @EJB
    private MachinesManager machineManager;

    public List<Machine> getMachines() {
//        DummyModel.fillWithInitTestData();
        return machineManager.getMachines();
    }

}
