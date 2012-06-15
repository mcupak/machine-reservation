package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
//@Table(name="users")
public class User implements Serializable {

    private static final long serialVersionUID = -1839803668123102392L;
    
    @Column(unique=true) // in the system cannot be more users with the same email, which is used as username
    @Email
    @NotEmpty
    private String email;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    @NotNull
    private UserRole userRole;
    
    @Column
    @NotEmpty
    @Size(min = 5, message="Password needs to be at least 5 characters long")
    private String password;

    public User() {
    }

    public User(String email, Long id, UserRole userRole, String password) {
        this.email = email;
        this.id = id;
        this.userRole = userRole;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
