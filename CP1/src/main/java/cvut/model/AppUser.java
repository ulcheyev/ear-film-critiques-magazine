package cvut.model;
import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.*;

//TODO email attribute

@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
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
    private List<Comment> commentList;

    @OneToMany(mappedBy = "voteOwner")
    private List<RatingVote> ratingVotes;

    public AppUser() {}

    public AppUser(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public List<RatingVote> getRatingVotes() {return ratingVotes;}

    public void setRatingVotes(List<RatingVote> ratingVotes) {this.ratingVotes = ratingVotes;}

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}
