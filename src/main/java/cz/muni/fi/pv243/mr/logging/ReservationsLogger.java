package cz.muni.fi.pv243.mr.logging;

import cz.muni.fi.pv243.mr.model.Reservation;
import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@MessageLogger
public interface ReservationsLogger {

    @Log @Message("Machines '%s' has been reserved by user '%s'.")
    void created(String machine, String email);

    @Log @Message("Reservation of '%s' machines by user '%s' has been deleted.")
    void deleted(String machine, String email);
    
    @Log @Message("Reservation of '%s' machines canceled by user '%s'")
    void canceled(String machine, String email);
    
    @Log @Message("Reservation updated: %s => %s")
    void edited(Reservation originalReservation, Reservation updatedReservation);
}
