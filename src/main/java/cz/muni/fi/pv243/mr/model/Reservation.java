package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
//@Table(name = "reservations")
public class Reservation implements Serializable {

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message="The end date has to be filled.")
    private Date end;
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message="The list of machines assigned to the reservation can't be empty.")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Machine> machines;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message="The start date has to be filled.")
    private Date start;
    @ManyToOne
    @NotNull(message="The user has to be filled.")
    private User user;
    @Transient
    @AssertTrue(message="Start date has to be before end date.")
    private boolean datesChronological = true;

    public Reservation() {
    }

    public Reservation(Date end, Long id, List<Machine> machines, Date start, User user) {
        this.end = end;
        this.id = id;
        this.machines = machines;
        this.start = start;
        this.user = user;
        datesChronological = start.before(end);
    }

    public Date getEnd() {
        return end;
    }

    public Long getId() {
        return id;
    }

    public List<Machine> getMachines() {
        if (machines == null) {
            machines = new ArrayList<Machine>();
        }
        return machines;
    }

    public Date getStart() {
        return start;
    }

    public User getUser() {
        return user;
    }

    public void setEnd(Date end) {
        if (end == null) {
            datesChronological = true;
        } else if (start != null) {
            datesChronological = start.before(end);
        }
        this.end = end;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public void setStart(Date start) {
        if (start == null) {
            datesChronological = true;
        } else if (end != null) {
            datesChronological = start.before(end);
        }
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

    @AssertTrue
    public boolean checkWhetherFromIsBeforeTo() {
        return getStart().before(getEnd());
    }
}
