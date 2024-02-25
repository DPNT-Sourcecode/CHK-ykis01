package befaster.solutions.HLO;

import java.util.Objects;
import befaster.runner.SolutionNotImplementedException;

public class HelloSolution {
    public String hello(String friendName) {
        throw new SolutionNotImplementedException();
    }
    
    private String sayHello(String name) {
        Objects.requireNonNull(name, "The value null is not a valid name");
        return "Hello, " + name + "!";
    }
}

