package cvut.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "remarks")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Remarks {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Admin admin;

    @Column(name = "remarks_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "critique", referencedColumnName = "id", nullable = false)
    @OrderBy("admin ASC")
    @JsonBackReference(value = "remarks-critique")
    private Critique critique;

    @Basic
    @Column(name = "remarks_make_day", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date remarksMakeDay;

    public Remarks(Admin admin, String text, Critique critique, Date remarksMakeDay) {
        this.admin = admin;
        this.text = text;
        this.critique = critique;
        this.remarksMakeDay = remarksMakeDay;
    }
}
