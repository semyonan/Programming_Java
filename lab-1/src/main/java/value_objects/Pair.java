package value_objects;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class represents a set of two elements
 * @param <T>
 * @param <S>
 */
@Data
@AllArgsConstructor
public class Pair <T,S> {
    private T elem1;
    private S elem2;
}
