package befaster.solutions.HLO;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloSolutionTest {
    private HelloSolution hello;

    @BeforeEach
    public void setUp() {
        hello = new HelloSolution();
    }

    @Test
    public void testHello_whenNameIsWorld_returnsString() {
        final String name = "World";
        String result = hello.hello(name);
        assertTrue(result.contains(name));
    }
    
    @Test
    public void testHello_whenNameIsNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> hello.hello(null));
    }

}
