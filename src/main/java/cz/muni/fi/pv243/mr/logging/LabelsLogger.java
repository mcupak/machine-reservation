package cz.muni.fi.pv243.mr.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@MessageLogger
public interface LabelsLogger {

    @Log @Message("Label '%s' created.")
    void created(String label);

    @Log @Message("Label '%s' deleted.")
    void deleted(String label);
}
