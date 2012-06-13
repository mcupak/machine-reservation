
package cz.muni.fi.pv243.mr.logging;

import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.solder.logging.Logger;


/**
 * Warning this class consists of generated code.
 * 
 */
@Generated(value = "org.jboss.logging.generator.model.MessageLoggerImplementor", date = "2012-06-13T17:06:23+0200")
public class ReservationsLogger_$logger
    implements Serializable, ReservationsLogger
{

    private final static long serialVersionUID = 1L;
    private final static String FQCN = ReservationsLogger_$logger.class.getName();
    protected final Logger log;
    private final static String deleted = "Reservation of '%s' machine by user '%s' has been deleted.";
    private final static String created = "Machine '%s' has been reserved by user '%s'.";

    public ReservationsLogger_$logger(final Logger log) {
        this.log = log;
    }

    @Override
    public final void deleted(final String machine, final String email) {
        log.logf(FQCN, (Logger.Level.INFO), null, deleted$str(), machine, email);
    }

    protected String deleted$str() {
        return deleted;
    }

    @Override
    public final void created(final String machine, final String email) {
        log.logf(FQCN, (Logger.Level.INFO), null, created$str(), machine, email);
    }

    protected String created$str() {
        return created;
    }

}
