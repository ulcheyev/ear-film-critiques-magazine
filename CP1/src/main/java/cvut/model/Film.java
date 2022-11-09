package cvut.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "film")
public class Film {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "film_and_main_roles",
            joinColumns =
                    @JoinColumn(name = "film_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "main_role_id")
    )
    private List<MainRole> mainRoleList;

    @OneToMany(mappedBy = "film")
    private List<Critique> critiques;

    @Column(name = "film_name", nullable = false)
    private String name;

    @Basic
    @Column(name = "date_of_release", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfRelease;

    @Column(name="film_description", columnDefinition = "TEXT", nullable = false)
    private String description;

    public Film() {}

    public List<MainRole> getMainRoleList() {
        return mainRoleList;
    }

    public void setMainRoleList(List<MainRole> mainRoleList) {
        this.mainRoleList = mainRoleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(Date dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Override
    public String toString() {
        return "Film{" +
                "mainRoleList=" + mainRoleList +
                ", name='" + name + '\'' +
                ", dateOfRelease=" + dateOfRelease +
                ", description='" + description + '\'' +
                '}';
    }
}