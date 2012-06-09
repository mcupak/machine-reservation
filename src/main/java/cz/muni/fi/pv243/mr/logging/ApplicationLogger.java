package cz.muni.fi.pv243.mr.logging;

import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.model.User;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.jboss.seam.security.events.LoggedInEvent;
import org.jboss.solder.logging.Logger;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class ApplicationLogger {

    @Inject
    private Logger logger;
    @Inject
    private UsersManager usersManager;

    public void userLoggedOut(@Observes LoggedInEvent event) {
        User user = usersManager.getUser(Long.parseLong(event.getUser().getId()));
        logger.infof("User %s [%s][%s] authenticated.", user.getEmail(), user.getId().toString(), user.getUserRole().toString());
    }

}
