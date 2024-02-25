package befaster.solutions.HLO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloSolutionTest {
    private HelloSolution hello;

    @BeforeEach
    public void setUp() {
        hello = new HelloSolution();
    }

    @Test
    public void testHello_whenNameIsCraftsman_returnsString() {
        final String name = "Craftsman";
        assertEquals("Hello, " + name + "!", hello.hello("Craftsman"));
    }

    @Test
    public void testHello_whenNameIsNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> hello.hello(null));
    }

}


