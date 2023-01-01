package cvut.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "rolex", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(AppUser.DISCRIMINATOR_VALUE)
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedQueries({
        @NamedQuery(name = "AppUser.findUsersWithSpecifiedCommentQuantity", query = "SELECT user FROM AppUser user where size(user.commentList)>?1"),
})
public class AppUser {

    public static final String DISCRIMINATOR_VALUE = "ROLE_USER";

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
    @JsonManagedReference
    private List<Comment> commentList;

    @OneToMany(mappedBy = "voteOwner")
    @ToString.Exclude
    @JsonManagedReference
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

    @Transient
    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

}
