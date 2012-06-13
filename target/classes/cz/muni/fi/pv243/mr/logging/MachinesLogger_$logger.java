
package cz.muni.fi.pv243.mr.logging;

import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.solder.logging.Logger;


/**
 * Warning this class consists of generated code.
 * 
 */
@Generated(value = "org.jboss.logging.generator.model.MessageLoggerImplementor", date = "2012-06-13T17:06:23+0200")
public class MachinesLogger_$logger
    implements Serializable, MachinesLogger
{

    private final static long serialVersionUID = 1L;
    private final static String FQCN = MachinesLogger_$logger.class.getName();
    protected final Logger log;
    private final static String deleted = "Machine '%s' deleted.";
    private final static String created = "Machine '%s' created.";

    public MachinesLogger_$logger(final Logger log) {
        this.log = log;
    }

    @Override
    public final void deleted(final String machine) {
        log.logf(FQCN, (Logger.Level.INFO), null, deleted$str(), machine);
    }

    protected String deleted$str() {
        return deleted;
    }

    @Override
    public final void created(final String machine) {
        log.logf(FQCN, (Logger.Level.INFO), null, created$str(), machine);
    }

    protected String created$str() {
        return created;
    }

}
