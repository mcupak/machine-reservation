
package cz.muni.fi.pv243.mr.logging;

import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.solder.logging.Logger;


/**
 * Warning this class consists of generated code.
 * 
 */
@Generated(value = "org.jboss.logging.generator.model.MessageLoggerImplementor", date = "2012-06-13T17:06:23+0200")
public class UsersLogger_$logger
    implements Serializable, UsersLogger
{

    private final static long serialVersionUID = 1L;
    private final static String FQCN = UsersLogger_$logger.class.getName();
    protected final Logger log;
    private final static String created = "User '%s' with '%s' role created.";
    private final static String deleted = "User '%s' with '%s' role deleted.";

    public UsersLogger_$logger(final Logger log) {
        this.log = log;
    }

    @Override
    public final void created(final String email, final String role) {
        log.logf(FQCN, (Logger.Level.INFO), null, created$str(), email, role);
    }

    protected String created$str() {
        return created;
    }

    @Override
    public final void deleted(final String email, final String role) {
        log.logf(FQCN, (Logger.Level.INFO), null, deleted$str(), email, role);
    }

    protected String deleted$str() {
        return deleted;
    }

}
