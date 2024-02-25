package befaster.solutions.HLO;

import java.util.Objects;

public class HelloSolution {
    public String hello(String friendName) {
        return sayHello(friendName);
    }
    
    private String sayHello(String name) {
        Objects.requireNonNull(name, "The value null is not a valid name");
        return "Hello, " + name + "!";
    }
}


