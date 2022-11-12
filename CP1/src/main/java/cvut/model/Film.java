package cvut.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "film")
@Getter
@Setter
@NoArgsConstructor
public class Film {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "film_and_main_roles",
            joinColumns =
                    @JoinColumn(name = "film_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "main_role_id")
    )
    private List<MainRole> mainRoleList;

    @OneToMany(mappedBy = "film")
    private List<Critique> critiques;

    @Column(name = "film_name", nullable = false)
    private String name;

    @Basic
    @Column(name = "date_of_release", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfRelease;

    @Column(name="film_description", columnDefinition = "TEXT", nullable = false)
    private String description;


}