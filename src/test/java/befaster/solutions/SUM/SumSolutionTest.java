package befaster.solutions.SUM;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SumSolutionTest {
    private SumSolution sum;

    @BeforeEach
    public void setUp() {
        sum = new SumSolution();
    }

    @Test
    public void testSum_whenXAndYAreInBounds_returnsSum() {
        assertThat(sum.compute(1, 1), equalTo(2));
    }

    @Test
    public void testSum_whenXIsOutOfLowerBounds_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sum.compute(-1, 1));
    }

    @Test
    public void testSum_whenXIsOutOfUpperBounds_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sum.compute(101, 1));
    }

    @Test
    public void testSum_whenYIsOutOfLowerBounds_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sum.compute(1, -1));
    }

    @Test
    public void testSum_whenYIsOutOfUpperBounds_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> sum.compute(1, 101));
    }

}

