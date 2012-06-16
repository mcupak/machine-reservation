package cz.muni.fi.pv243.mr.action;

import cz.muni.fi.pv243.mr.action.ReservationsComponentBean;
import cz.muni.fi.pv243.mr.ejb.ReservationsManager;
import cz.muni.fi.pv243.mr.model.Machine;
import cz.muni.fi.pv243.mr.model.Reservation;
import cz.muni.fi.pv243.mr.model.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ViewScoped
@Named
public class ReservationsBean implements Serializable {
    private static final long serialVersionUID = 324509230820348234L;

    @Inject
    private ReservationsComponentBean reservationsComponentBean;

    @Inject
    private ReservationsManager reservationsManager;

    private Map<Date, List<Reservation>> organizedReservations;

    private Date from;

    private Date to;

    private User user = null;

    private Machine machine = null;

    @PostConstruct
    private void initializeFields() {
        from = reservationsComponentBean.getToday();
        to = reservationsComponentBean.getNextWeek();
    }

    public void loadReservations() {
        List<Reservation> ret = reservationsManager.getReservations(from, to, user, machine);
        organizedReservations = reservationsComponentBean.getOrganizedReservations(getDays(), ret);
    }

    public List<Date> getDays() {
        return reservationsComponentBean.getDays(from, to);
    }

    public Map<Date, List<Reservation>> getOrganizedReservations() {
        return organizedReservations;
    }

    public void setOrganizedReservations(Map<Date, List<Reservation>> organizedReservations) {
        this.organizedReservations = organizedReservations;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
