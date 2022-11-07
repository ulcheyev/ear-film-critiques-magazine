package cvut.model;
import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Comment{

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Column(name = "comment_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Basic
    @Column(name = "date_of_public", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPublic;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comment_owner", referencedColumnName = "id", nullable = false)
    private User commentOwner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post", referencedColumnName = "id", nullable = false)
    private Post post;

    public Comment() {}

    public Comment(String text, Date dateOfPublic, User user) {
        this.text = text;
        this.dateOfPublic = dateOfPublic;
        this.commentOwner = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateOfPublic() {
        return dateOfPublic;
    }

    public void setDateOfPublic(Date dateOfPublic) {
        this.dateOfPublic = dateOfPublic;
    }

    public User getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(User user) {
        this.commentOwner = user;
    }

    public Post getPost() {return post;}

    public void setPost(Post post) {this.post = post;}

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", dateOfPublic=" + dateOfPublic +
                ", user=" + commentOwner +
                '}';
    }
}