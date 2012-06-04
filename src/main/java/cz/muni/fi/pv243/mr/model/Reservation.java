package cz.muni.fi.pv243.mr.model;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date end;
    @Id
    private Long id;
    @ManyToMany
    private Collection<Machine> machines;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start;

    public Date getEnd() {
        return end;
    }

    public Long getId() {
        return id;
    }

    public Collection<Machine> getMachines() {
        return machines;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStart(Date start) {
        this.start = start;
    }

}
