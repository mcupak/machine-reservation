package cz.muni.fi.pv243.mr.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@MessageLogger
public interface UsersLogger {

    @Log @Message("User '%s' with '%s' role created.")
    void created(String email, String role);

    @Log @Message("User '%s' deleted.")
    void deleted(String email);
    
    @Log @Message("User '%s' information updated.")
    void updated(Long id);
    
}
