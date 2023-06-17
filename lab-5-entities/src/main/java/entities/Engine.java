package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@NoArgsConstructor
@Data
@Table(name = "ENGINE", schema = "cardb")
public class Engine {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_MODEL_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ExtraCarModel carModel;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
    @Column(name = "NUMBER_OF_CYLINDERS", nullable = false)
    private Integer numberOfCylinders;
    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;
    @Column(name = "HEIGHT", nullable = false)
    private Integer height;
}
