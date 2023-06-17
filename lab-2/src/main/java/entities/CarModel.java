package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

/**
 * Car model entity
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "CAR_MODEL", schema = "cardb")
@NamedQueries({
        @NamedQuery(name="CarModel.findById",
                query="select distinct s from CarModel s " +
                        "where s.id = :id"),
        @NamedQuery(name="CarModel.findAllByCarMarkId",
                query="select distinct s from CarModel s " +
                        "where s.carMark.id = :car_mark_id")})
public class CarModel {

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_MARK_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CarMark carMark;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
    @Column(name = "LENGTH", nullable = false)
    private Integer length;
    @Column(name = "WIDTH", nullable = false)
    private Integer width;
    @Enumerated(EnumType.STRING)
    @Column(name = "BODY_TYPE", nullable = false)
    private Body_Type bodyType;
}
