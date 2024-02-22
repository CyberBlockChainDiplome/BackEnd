package diplome.blockchain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Diploma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String diplomaName;

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "diploma", fetch = FetchType.EAGER)
    private List<Stage> stages;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Transmitter transmitter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiplomaName() {
        return diplomaName;
    }

    public void setDiplomaName(String diplomaName) {
        this.diplomaName = diplomaName;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}
