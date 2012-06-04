package cz.muni.fi.pv243.mr.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
@Entity
@Table(name="labels")
public class Label implements Serializable {

    @Column
    private String description;
    @Id
    private Long id;
    @ManyToMany
    private Collection<Machine> machines;
    @Column(nullable=false, length=100, unique=true)
    private String name;

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }
//
//    public Collection<Machine> getMachines() {
//        return machines;
//    }

    public String getName() {
        return name;
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

}
