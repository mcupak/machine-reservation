package cz.muni.fi.pv243.mr.security;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.api.User;
import org.picketlink.idm.impl.api.PasswordCredential;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class UserAuthenticator extends BaseAuthenticator {

    @Inject
    private Credentials credentials;
    @PersistenceContext
    private EntityManager entityManager;

    public void authenticate() {
        // TODO
        if (credentials.getUsername().equals("admin") && ((PasswordCredential) credentials.getCredential()).getValue().equals("admin")) {
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(new User() {

                public String getId() {
                    return "admin";
                }

                public String getKey() {
                    return "admin";
                }
            });
            return;
        }
        if (credentials.getUsername().equals("guest") && ((PasswordCredential) credentials.getCredential()).getValue().equals("guest")) {
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(new User() {

                public String getId() {
                    return "guest";
                }

                public String getKey() {
                    return "guest";
                }
            });
            return;
        }
        setStatus(AuthenticationStatus.FAILURE);
    }
}
