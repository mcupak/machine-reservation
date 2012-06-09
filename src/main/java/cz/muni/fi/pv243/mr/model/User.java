package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
@Table(name="users")
public class User implements Serializable {
    @Column
    String email;
    @Id
    private Long id;
    @Column
    private UserRole userRole;

    public User() {
    }

    public User(String email, Long id, UserRole userRole) {
        this.email = email;
        this.id = id;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

}
