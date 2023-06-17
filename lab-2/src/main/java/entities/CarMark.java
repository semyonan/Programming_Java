package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;

/**
 * Car mark entity
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "CAR_MARK", schema = "cardb")
@NamedQueries({
        @NamedQuery(name="CarMark.findById",
                query="select distinct s from CarMark s " +
                        "where s.id = :id")})
public class CarMark {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false, length = 60)
    private String name;
    @Column(name = "RELEASE_DATE", nullable = true)
    private Date releaseDate;
}
