package cz.muni.fi.pv243.mr.security;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class UserAuthorizator {

    @Secures
    @Admin
    public boolean isAdmin(Identity identity) {
        if (!identity.isLoggedIn()) {
            return false;
        }
        // TODO
        return identity.getUser().getId().equals("admin");
    }

    @Secures
    @CanReserve
    public boolean canReserve(Identity identity) {
        if (!identity.isLoggedIn()) {
            return false;
        }
        return true;
    }
}
