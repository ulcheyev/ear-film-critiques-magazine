package cvut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.*;

//TODO email attribute

@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppUser {

    @GeneratedValue(strategy = AUTO)
    @Id
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy="appUser")
    @ToString.Exclude
    private List<Comment> commentList;

    @OneToMany(mappedBy = "voteOwner")
    @ToString.Exclude
    private List<RatingVote> ratingVotes;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    public AppUser(String firstname, String lastname, String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
