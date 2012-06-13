package cz.muni.fi.pv243.mr.ejb;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.jboss.solder.core.ExtensionManaged;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class EntityManagerProducer {

    @ExtensionManaged
    @Produces
    @PersistenceUnit
    @ConversationScoped
    EntityManagerFactory entityManagerFactory;
}
