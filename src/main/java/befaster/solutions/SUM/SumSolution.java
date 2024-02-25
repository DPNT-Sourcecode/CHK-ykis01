package befaster.solutions.SUM;

import befaster.runner.SolutionNotImplementedException;

@SuppressWarnings("unused")
public class SumSolution {

    public int compute(int x, int y) {
        return sum(x, y);
    }
    
    // sum(Integer, Integer) -> Integer
    
//    - param[0] = a positive integer between 0-100
//    - param[1] = a positive integer between 0-100
//    - @return = an Integer representing the sum of the two numbers

    private static int sum(int x, int y) {
        if (x < 0 || x > 100) {
            throw new IllegalArgumentException("x must be a positive integer between 0-100");
        }
        if (y < 0 || y > 100) {
            throw new IllegalArgumentException("y must be a positive integer between 0-100");
        }
        return x + y;
    }

}
