import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        System.out.println(list.toString());
        String result = String.join(",", list);
        System.out.println(result); // 输出: a,b,c
    }
}
