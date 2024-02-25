package befaster.solutions.CHK;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckoutSolutionTest {
    private CheckoutSolution checkout;

    @BeforeEach
    public void setUp() {
        checkout = new CheckoutSolution();
    }

    /**
     *     +------+-------+----------------+
    | Item | Price | Special offers |
    +------+-------+----------------+
    | A    | 50    | 3A for 130     |
    | B    | 30    | 2B for 45      |
    | C    | 20    |                |
    | D    | 15    |                |
    +------+-------+----------------+
     */
    
    @Test
    public void testCheckout_withoutProducts_returnsZero() {
        assertEquals(0, checkout.checkout(""));
    }
    
    @Test
    public void testCheckout_withNull_returnsMinusOne() {
        assertEquals(-1, checkout.checkout(null));
    }
    
    @Test
    public void testCheckout_withImpossibleProduct_returnsMinusOne() {
        assertEquals(-1, checkout.checkout("X"));
    }

    @Test
    public void testCheckout_withoutOneA_returns50() {
        assertEquals(50, checkout.checkout("A"));
    }
    
    @Test
    public void testCheckout_withoutThreeA_returns130() {
        assertEquals(130, checkout.checkout("AAA"));
    }
    
    @Test
    public void testCheckout_withoutFourA_returns180() {
        assertEquals(180, checkout.checkout("AAAA"));
    }

}

