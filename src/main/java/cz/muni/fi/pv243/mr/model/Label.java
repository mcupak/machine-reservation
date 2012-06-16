package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
//@Table(name="labels")
public class Label implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;
    @Column
    private String description;
    @ManyToMany
    private Collection<Machine> machines;

    public Label() {
    }

    // FIXME: remove
    public Label(String description, Long id, String name) {
        this.description = description;
        this.id = id;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Machine> getMachines() {
        if (machines == null) {
            machines = new ArrayList<Machine>();
        }
        return machines;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMachines(Collection<Machine> machines) {
        this.machines = machines;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Label other = (Label) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Label{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }
    
    
}
