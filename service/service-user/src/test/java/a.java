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
        String s = String.valueOf(System.currentTimeMillis());
        System.out.println("s = " + s);
        String a="$2a$10$dkDKgyJUJtsqI6Fpo0NXHOdO6ZB.2WMUUuCN8gG5IUQ75M1AXAFKe";
        System.out.println(a.length());
    }
}
