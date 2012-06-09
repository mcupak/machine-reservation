package cz.muni.fi.pv243.mr.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class DummyModel {

    private static boolean initialized = false;
    private static List<Label> labels;
    private static List<Machine> machines;
    private static Map<Machine, List<Reservation>> reservations;
    private static List<User> users;

    public static List<Machine> getMachines() {
        init();
        return Collections.unmodifiableList(machines);
    }

    public static List<Label> getLabels() {
        init();
        return Collections.unmodifiableList(labels);
    }

    public static Map<Machine, List<Reservation>> getReservations() {
        init();
        return reservations;
    }

    public static List<User> getUsers() {
        init();
        return users;
    }

    private static void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        labels = Arrays.asList(
                new Label("Lorem ipsum", 0l, new ArrayList<Machine>(), "linux"),
                new Label("Lorem ipsum", 1l, new ArrayList<Machine>(), "windows"),
                new Label("Lorem ipsum", 2l, new ArrayList<Machine>(), "x86"));
        machines = Arrays.asList(
                new Machine("Lorem ipsum", 0l, Arrays.asList(labels.get(0), labels.get(2)), "anna"),
                new Machine("Lorem ipsum", 1l, Arrays.asList(labels.get(1), labels.get(2)), "aisa"));
        labels.get(0).getMachines().add(machines.get(0));
        labels.get(1).getMachines().add(machines.get(1));
        labels.get(2).getMachines().add(machines.get(0));
        labels.get(2).getMachines().add(machines.get(1));
        users = new ArrayList<User>();
        users.add(new User("admin@admin", 0l, UserRole.ADMIN));
        users.add(new User("guest@guest", 1l, UserRole.COMMON));
        users.add(new User("franta@frantov.cz", 2l, UserRole.COMMON));
        long reservationId = 0l;
        reservations = new HashMap<Machine, List<Reservation>>();
        for (Machine machine: machines) {
            Date now = new Date();
            List<Reservation> machineReservations = new ArrayList<Reservation>();
            for (int i=0; i<10; i++) {
                final long hour = 1000 * 60 * 60;
                final long day = hour * 24;
                Date start = new Date(now.getTime() + reservationId * hour + i * 5 * hour);
                Reservation reservation = new Reservation(new Date(start.getTime() + day), reservationId, Arrays.asList(machine), start, users.get(1));
                machineReservations.add(reservation);
                reservationId++;
            }
            reservations.put(machine, machineReservations);
        }
    }
}
