package cvut.model;
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
        @NamedQuery(name = "AppUser.findUsersWithSpecifiedCommentQuantity", query = "SELECT user FROM AppUser user where size(user.commentList)>?1")
})
@SqlResultSetMapping(
        name = "AppUserMapping",
        classes = @ConstructorResult(
                targetClass = AppUser.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "firstname", type = String.class),
                        @ColumnResult(name = "lastname", type = String.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "password", type = String.class),
                        @ColumnResult(name = "email", type = String.class)
                }
        )
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findUsersByLastnameNNQ",
                query = "SELECT id, firstname, lastname, username, password, email FROM app_user WHERE lastname = :lastname",
                resultSetMapping = "AppUserMapping"
        ),
        @NamedNativeQuery(
                name = "AppUser.findUsersWithSpecifiedCommentQuantityNNQ",
                query = "SELECT id, firstname, lastname, username, password, email FROM app_user WHERE (SELECT COUNT(*) FROM comment WHERE comment.comment_owner = app_user.id) > ?1",
                resultSetMapping = "AppUserMapping"
        )
})


public class AppUser {

    public static final String DISCRIMINATOR_VALUE = "ROLE_USER";

    public AppUser(Long id, String firstname, String lastname, String username, String password, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

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
    @OrderBy("dateOfPublic DESC")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "voteOwner")
    @ToString.Exclude
    @OrderBy("date DESC")
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

    public AppUser(Long id) {
        this.id = id;
    }

    @Transient
    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

}
