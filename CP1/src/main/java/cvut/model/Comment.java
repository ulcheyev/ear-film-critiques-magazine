package cvut.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment extends AbstractEntity{

    @Column(name = "comment_text", nullable = false)
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

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", dateOfPublic=" + dateOfPublic +
                ", user=" + commentOwner +
                '}';
    }
}
