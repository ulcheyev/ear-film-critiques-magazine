package cvut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "main_role")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MainRole{

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private FilmRole filmRole;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @ManyToMany(mappedBy = "mainRoleList")
    private List<Film> filmList;

}
