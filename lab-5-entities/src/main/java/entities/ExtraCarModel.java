package entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Car model entity
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "CAR_MODEL", schema = "cardb")
public class ExtraCarModel {

    public enum Body_Type {
        SEDAN,
        HATCHBACK,
        STATION_WAGON,
        COUPE,
        WAGON,
        ROADSTER
    }
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_MARK_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CarMark carMark;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
    @Column(name = "LENGTH", nullable = false)
    private Integer length;
    @Column(name = "WIDTH", nullable = false)
    private Integer width;
    @Column(name = "HEIGHT", nullable = false)
    private Integer height;
    @Enumerated(EnumType.STRING)
    @Column(name = "BODY_TYPE", nullable = false)
    private Body_Type bodyType;
}

