package cvut.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Remarks extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "admin", referencedColumnName = "id", nullable = false)
    private User admin;

    @Column(name = "remarks_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Basic
    @Column(name = "remarks_make_day", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date remarksMakeDay;

    public Remarks() {}

    public User getAdmin() {return admin;}
    public void setAdmin(User admin) {this.admin = admin;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Remarks{" +
                "admin=" + admin +
                '}';
    }
}
