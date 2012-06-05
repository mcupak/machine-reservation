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
@Table(name="machines")
public class Machine implements Serializable {

    @Column
    private String description;
    @Id
    private Long id;
    @ManyToMany
    private Collection<Label> labels;
    @Column(nullable=false, length=100, unique=true)
    private String name;

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
}
