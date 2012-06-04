package cz.muni.fi.pv243.mr.bean;

import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ManagedBean
@SessionScoped
public class TestBean {

    private EntityManager entityManager;

    public List<String> getTables() {
        return Arrays.asList("machines", "reservations", "labels", "users");
    }

}
