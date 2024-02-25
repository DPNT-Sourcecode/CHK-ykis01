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
        assertEquals(-1, checkout.checkout("0"));
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

    @Test
    public void testCheckout_withoutFiveA_returns200() {
        assertEquals(200, checkout.checkout("AAAAA"));
    }

    @Test
    public void testCheckout_withoutEightA_returns330() {
        assertEquals(330, checkout.checkout("AAAAAAAA"));
    }

    @Test
    public void testCheckout_withoutNineA_returns380() {
        assertEquals(380, checkout.checkout("AAAAAAAAA"));
    }
    
    @Test
    public void testCheckout_2Eget1Bfree_returns80() {
        assertEquals(80, checkout.checkout("EEB"));
    }
    
    @Test
    public void testCheckout_2Eget1Bfree_with2B_returns110() {
        assertEquals(110, checkout.checkout("EEBB"));
    }
    
    @Test
    public void testCheckout_2E_withoutB_returns80() {
        assertEquals(80, checkout.checkout("EE"));
    }
    
    @Test
    public void CHK_R2_012() {
        assertEquals(155, checkout.checkout("ABCDE"));
    }
    
    @Test
    public void CHK_R2_026() {
        assertEquals(160, checkout.checkout("EEEEBB"));
    }
    
    @Test
    public void CHK_R2_027() {
        assertEquals(160, checkout.checkout("BEBEEE"));
    }
    
    @Test
    public void test_buy2Fget1F__with1F_returns10() {
        assertEquals(10, checkout.checkout("F"));
    }
    
    
    @Test
    public void test_buy2Fget1F_with3F_returns20() {
        assertEquals(20, checkout.checkout("FFF"));
    }

    @Test
    public void CHK_R3_039() {
        assertEquals(20, checkout.checkout("FF"));
    }
    
    @Test
    public void CHK_R3_041() {
        assertEquals(30, checkout.checkout("FFFF"));
    }
    
    @Test
    public void CHK_R3_042() {
        assertEquals(40, checkout.checkout("FFFFFF"));
    }
    
    @Test
    public void CHK_R3_043() {
        assertEquals(40, checkout.checkout("FFFFFF"));
    }
    
    @Test
    public void CHK_R5_013() {
        assertEquals(70, checkout.checkout("K"));
    }
    
    @Test
    public void CHK_R5_021() {
        assertEquals(20, checkout.checkout("S"));
    }
    
    @Test
    public void CHK_R5_026() {
        assertEquals(17, checkout.checkout("X"));
    }
    
}

