package diplome.blockchain.model;

import jakarta.persistence.*;

@Entity
public class Stage {

    @Id
    @GeneratedValue
    private Long id;
    private double value;

    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Receiver receiver;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
