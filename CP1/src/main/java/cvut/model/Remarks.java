package cvut.model;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "remarks")
public class Remarks {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @ManyToOne
    @JoinColumn(name = "admin", referencedColumnName = "id", nullable = false)
    private Admin admin;

    @Column(name = "remarks_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "critique",referencedColumnName = "id", nullable = false)
    private Critique critique;

    @Basic
    @Column(name = "remarks_make_day", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date remarksMakeDay;

    public Remarks() {}

    public Admin getAdmin() {return admin;}
    public void setAdmin(Admin admin) {this.admin = admin;}

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
