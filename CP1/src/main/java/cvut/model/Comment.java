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

    @ManyToOne
    private AppUser commentOwner;

    @ManyToOne(optional = false)
    private Post post;

    public Comment() {}

    public Comment(String text, Date dateOfPublic, AppUser appUser) {
        this.text = text;
        this.dateOfPublic = dateOfPublic;
        this.commentOwner = appUser;
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

    public AppUser getCommentOwner() {
        return commentOwner;
    }

    public void setCommentOwner(AppUser appUser) {
        this.commentOwner = appUser;
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
