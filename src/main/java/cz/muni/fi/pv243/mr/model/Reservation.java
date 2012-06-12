package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

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
    @ManyToOne
    private Machine machine;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start;
    @ManyToOne
    private User user;

    public Reservation() {
    }

    public Reservation(Date end, Long id, Machine machine, Date start, User user) {
        this.end = end;
        this.id = id;
        this.machine = machine;
        this.start = start;
        this.user = user;
    }

    public Date getEnd() {
        return end;
    }

    public Long getId() {
        return id;
    }

    public Machine getMachine() {
        return machine;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    

}
