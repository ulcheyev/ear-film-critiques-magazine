package cvut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comment{

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "comment_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Basic
    @Column(name = "date_of_public", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfPublic;

    @ManyToOne
    @JoinColumn(name = "comment_owner", referencedColumnName = "id", nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "critique", referencedColumnName = "id", nullable = false)
    private Critique critique;


    public Comment(String text, Date dateOfPublic, AppUser appUser, Critique critique) {
        this.text = text;
        this.dateOfPublic = dateOfPublic;
        this.appUser = appUser;
        this.critique = critique;
    }

//    public Comment(String text, Date date, AppUser appUser, Critique critique) {
//    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

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
        return appUser;
    }

    public void setCommentOwner(AppUser appUser) {
        this.appUser = appUser;
    }

    public AppUser getAppUser() {return appUser;}

    public void setAppUser(AppUser appUser) {this.appUser = appUser;}



    public Critique getCritique() {
        return critique;
    }

    public void setCritique(Critique critique) {
        this.critique = critique;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", dateOfPublic=" + dateOfPublic +
                ", user=" + appUser +
                '}';
    }
}
