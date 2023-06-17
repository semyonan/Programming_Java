package entities;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Car mark entity
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "CAR_MARK", schema = "cardb")
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
