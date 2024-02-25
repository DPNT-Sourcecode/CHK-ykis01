package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        Map<Boolean, List<Integer>> groups = List.of(1, 2, 3, 4, 5, 6, 7, 8).stream().collect(Collectors.partitioningBy(s -> s == 8));
        List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
        System.out.println(subSets);
    }

}

