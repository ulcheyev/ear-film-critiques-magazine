package cvut.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Rating {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Critique critique;

    @Column
    private double stars;

    @Basic
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Rating() {}

    public Rating(Critique critique, double stars, Date date) {
        this.critique = critique;
        this.stars = stars;
        this.date = date;
    }

    public Critique getCritique() {
        return critique;
    }

    public void setCritique(Critique critique) {
        this.critique = critique;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", critique=" + critique +
                ", stars=" + stars +
                ", date=" + date +
                '}';
    }
}
