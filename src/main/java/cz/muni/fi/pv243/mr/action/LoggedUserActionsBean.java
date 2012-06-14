package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.security.UserAuthorizator;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.seam.security.Identity;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@SessionScoped
@Named
public class LoggedUserActionsBean implements Serializable {

	private static final long serialVersionUID = 4559334109992948286L;
	@Inject
    private Identity identity;
    @Inject
    private UserAuthorizator authorizator;

    public boolean isAdmin() {
        return authorizator.isAdmin(identity);
    }

    public boolean canReserve() {
        return authorizator.canReserve(identity);
    }
}
