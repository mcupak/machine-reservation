package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
//@Table(name="machines")
public class Machine implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    @Column
    private String description;
    @ManyToMany(mappedBy = "machines")//r(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Label> labels;

    public Machine() {
    }

    // FIXME: remove
    public Machine(String description, Long id, Collection<Label> labels, String name) {
        this.description = description;
        this.id = id;
        this.labels = labels;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Label> getLabels() {
        if (labels == null) {
            labels = new ArrayList<Label>();
        }
        return labels;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
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
        final Machine other = (Machine) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Machine{" + "id=" + id + ", name=" + name + ", description=" + description + ", labels=" + labels + '}';
    }
    
    
}
