package diplome.blockchain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Stage {

    @Id
    @GeneratedValue
    private Long id;
    private double value;

    @ManyToOne
    private Diploma diploma;
    @ManyToOne
    private Receiver receiver;

    public Diploma getDiploma() {
        return diploma;
    }

    public void setDiploma(Diploma diploma) {
        this.diploma = diploma;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
