package cz.muni.fi.pv243.mr.security;

import cz.muni.fi.pv243.mr.ejb.UsersManager;
import cz.muni.fi.pv243.mr.model.User;
import javax.inject.Inject;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class UserAuthenticator extends BaseAuthenticator {

    @Inject
    private Credentials credentials;
    @Inject
    private UsersManager usersManager;

    public void authenticate() {
        // TODO
        final User user = usersManager.getUser(credentials.getUsername(), ((PasswordCredential) credentials.getCredential()).getValue());
        if (user == null) {
            setStatus(AuthenticationStatus.FAILURE);
        }
        setUser(new org.picketlink.idm.api.User() {
            public String getId() {
                return Long.toString(user.getId());
            }
            public String getKey() {
                return user.getUserRole().toString();
            }
        });
        setStatus(AuthenticationStatus.SUCCESS);
    }
}
