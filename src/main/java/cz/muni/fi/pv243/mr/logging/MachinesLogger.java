package cz.muni.fi.pv243.mr.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@MessageLogger
public interface MachinesLogger {

    @Log @Message("Machine '%s' created.")
    void created(String machine);

    @Log @Message("Machine '%s' deleted.")
    void deleted(String machine);
}
