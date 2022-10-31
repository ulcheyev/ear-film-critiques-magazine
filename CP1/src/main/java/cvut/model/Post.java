package cvut.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Post extends AbstractEntity{

    @OneToOne
    @JoinColumn(name = "critique_id", nullable = false)
    private Critique critique;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

    @Column(name = "post_title", nullable = false)
    private String title;

    @Basic
    @Column(name = "date_of_public", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPublic;

    public Post() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateOfPublic() {
        return dateOfPublic;
    }

    public void setDateOfPublic(Date dateOfPublic) {
        this.dateOfPublic = dateOfPublic;
    }

    public Critique getCritique() {
        return critique;
    }

    public void setCritique(Critique critique) {
        this.critique = critique;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Post{" +
                "critique=" + critique +
                ", commentList=" + commentList +
                ", title='" + title + '\'' +
                ", dateOfPublic=" + dateOfPublic +
                '}';
    }
}
