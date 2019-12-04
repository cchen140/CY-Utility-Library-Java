package cy.utility;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UmathTest {

    @Test
    void getDivisors() {
        ArrayList<Long> divisors;

        // 3000's divisors:
        // 1, 2, 3, 4, 5, 6, 8, 10, 12, 15, 20, 24, 25, 30, 40, 50, 60, 75, 100,
        // 120, 125, 150, 200, 250, 300, 375, 500, 600, 750, 1000, 1500, 3000
        divisors = Umath.getDivisors(3000);
        //System.out.println(divisors);
        assertEquals(32, divisors.size());
        assertTrue(divisors.contains((long)375));

        // 1's divisor
        divisors = Umath.getDivisors(1);
        assertEquals(1, divisors.size());
        assertTrue(divisors.contains((long)1));

        // 2's divisor
        divisors = Umath.getDivisors(2);
        assertEquals(2, divisors.size());
        assertTrue(divisors.contains((long)1));
        assertTrue(divisors.contains((long)2));

        // 3's divisor
        divisors = Umath.getDivisors(3);
        assertEquals(2, divisors.size());
        assertTrue(divisors.contains((long)1));
        assertTrue(!divisors.contains((long)2));
        assertTrue(divisors.contains((long)3));
    }

    @Test
    void getPoisson() {
        for (int i=0; i<1000; i++) {
            double value = Umath.getPoisson(30030);
            System.out.println(value);
        }
    }
}