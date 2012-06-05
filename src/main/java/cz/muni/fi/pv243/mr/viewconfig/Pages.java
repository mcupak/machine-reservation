package cz.muni.fi.pv243.mr.viewconfig;

import cz.muni.fi.pv243.mr.security.CanReserve;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@ViewConfig
public interface Pages {

    static enum Pages1 {
        @ViewPattern("/index.xhtml")
        WELCOME,

        @ViewPattern("/label.xhtml")
        @UrlMapping(pattern="/label/#{id}/")
        LABEL,

        @ViewPattern("/machine.xhtml")
        @UrlMapping(pattern="/machine/#{id}/")
        MACHINE
    }

}
