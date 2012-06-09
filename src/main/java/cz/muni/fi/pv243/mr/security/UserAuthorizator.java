package cz.muni.fi.pv243.mr.security;

import cz.muni.fi.pv243.mr.model.UserRole;
import java.io.Serializable;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class UserAuthorizator implements Serializable {

    @Secures
    @Admin
    public boolean isAdmin(Identity identity) {
        if (!identity.isLoggedIn()) {
            return false;
        }
        return identity.getUser().getKey().equals(UserRole.ADMIN.toString());
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
