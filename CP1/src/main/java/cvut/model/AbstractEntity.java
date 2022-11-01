package cvut.model;
import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @SequenceGenerator(
            name="sequence_id_generator",
            sequenceName = "sequence_id_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "sequence_id_generator"
    )
    @Column(
            name = "id",
            updatable = false
    )

    private Long id;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Override
    public String toString() {
        return "id = " + id + " ";
    }
}
