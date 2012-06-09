package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

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
    @ManyToOne
    private User user;

    public Reservation() {
    }

    // FIXME: remove
    public Reservation(Date end, Long id, Collection<Machine> machines, Date start, User user) {
        this.end = end;
        this.id = id;
        this.machines = machines;
        this.start = start;
        this.user = user;
    }

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

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }

}
