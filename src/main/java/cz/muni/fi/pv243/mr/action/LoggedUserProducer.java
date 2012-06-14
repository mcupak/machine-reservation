package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.model.User;
import java.io.Serializable;
import javax.inject.Inject;
import org.jboss.seam.security.Identity;
import org.jboss.solder.unwraps.Unwraps;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class LoggedUserProducer implements Serializable {

	private static final long serialVersionUID = 4603063114429231508L;
	@Inject
    private Identity identity;
    @Inject
    private UsersManager usersManager;

    @Unwraps
    @Logged
    public User produceLoggedUser() {
        if (!identity.isLoggedIn()) {
            return null;
        }
        return usersManager.getUser(Long.parseLong(identity.getUser().getId()));
    }
}
