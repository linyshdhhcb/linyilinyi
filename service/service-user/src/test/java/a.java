import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/24
 * @ClassName: a
 */
public class a {
    @Test
    public void test(){

        String a="$2a$10$dkDKgyJUJtsqI6Fpo0NXHOdO6ZB.2WMUUuCN8gG5IUQ75M1AXAFKe";
        char[] charArray = a.toCharArray();
        int i = a.indexOf("s");
        char c = a.charAt(2);
        int[] ints = new int[128];
        int anInt = ints[c];
        System.out.println(ints[0]);
    }
}
