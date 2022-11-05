package cvut.model;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Remarks {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

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
