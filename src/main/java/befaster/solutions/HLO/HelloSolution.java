package befaster.solutions.HLO;

public class HelloSolution {
    public String hello(String friendName) {
        return sayHello(friendName);
    }
    
    private String sayHello(String name) {
        if (name == null ) {
            throw new IllegalArgumentException("The value null is not a valid name");
        }
        return "Hello, " + name + "!";
    }
}
