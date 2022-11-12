package cvut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comment{

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "comment_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Basic
    @Column(name = "date_of_public", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPublic;

    @ManyToOne
    @JoinColumn(name = "comment_owner", referencedColumnName = "id", nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "critique", referencedColumnName = "id", nullable = false)
    private Critique critique;

    public Comment(String text, Date dateOfPublic, AppUser appUser, Critique critique) {
        this.text = text;
        this.dateOfPublic = dateOfPublic;
        this.appUser = appUser;
        this.critique = critique;
    }



}
