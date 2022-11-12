package cvut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "critique")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Critique {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private CritiqueState critiqueState = CritiqueState.IN_PROCESSED;

    @OneToMany(mappedBy = "critique")
    private List<RatingVote> critiqueRatingVote;

    @Column(name = "title",columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(name = "critique_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "rating")
    private double rating = 0.0;

    @Basic
    @Column(name = "date_of_acceptance")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfAcceptance;

    @OneToMany(mappedBy = "critique")
    private List<Remarks> critiqueRemarks;

    //TODO ubrat cascade type
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "admin", referencedColumnName = "id")
    private Admin  admin;

    //TODO ubrat cascade type
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "critique_owner", referencedColumnName = "id", nullable = false)
    private Critic critiqueOwner;

    @OneToMany(mappedBy = "admin")
    private List<Remarks> remarksList;

    @OneToMany(mappedBy = "critique")
    private List<Comment> comments;

    //TODO ubrat cascade type
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "film", referencedColumnName = "id", nullable = false)
    private Film film;


}

