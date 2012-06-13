
package cz.muni.fi.pv243.mr.logging;

import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.solder.logging.Logger;


/**
 * Warning this class consists of generated code.
 * 
 */
@Generated(value = "org.jboss.logging.generator.model.MessageLoggerImplementor", date = "2012-06-13T17:06:23+0200")
public class LabelsLogger_$logger
    implements Serializable, LabelsLogger
{

    private final static long serialVersionUID = 1L;
    private final static String FQCN = LabelsLogger_$logger.class.getName();
    protected final Logger log;
    private final static String created = "Label '%s' created.";
    private final static String deleted = "Label '%s' deleted.";

    public LabelsLogger_$logger(final Logger log) {
        this.log = log;
    }

    @Override
    public final void created(final String label) {
        log.logf(FQCN, (Logger.Level.INFO), null, created$str(), label);
    }

    protected String created$str() {
        return created;
    }

    @Override
    public final void deleted(final String label) {
        log.logf(FQCN, (Logger.Level.INFO), null, deleted$str(), label);
    }

    protected String deleted$str() {
        return deleted;
    }

}
