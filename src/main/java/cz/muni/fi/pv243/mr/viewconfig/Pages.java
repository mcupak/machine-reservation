package cz.muni.fi.pv243.mr.viewconfig;

import cz.muni.fi.pv243.mr.security.Admin;
import cz.muni.fi.pv243.mr.security.CanReserve;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
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

        @ViewPattern("/editLabel.xhtml")
        @UrlMapping(pattern="/label/#{id}/")
        LABEL,

        @ViewPattern("/admin/*")
        @Admin
        @LoginView("/denied.xhtml")
        @AccessDeniedView("/denied.xhtml")
        ADMIN_ACTIONS,

        @ViewPattern("/user/*")
        @CanReserve
        @LoginView("/denied.xhtml")
        @AccessDeniedView("/denied.xhtml")
        USER_ACTIONS,

        @ViewPattern("/editMachine.xhtml")
        @UrlMapping(pattern="/machine/#{id}/")
        MACHINE,

        @ViewPattern("/user/reservation.xhtml")
        @UrlMapping(pattern="/reservation/#{id}")
        RESERVATION

    }

}